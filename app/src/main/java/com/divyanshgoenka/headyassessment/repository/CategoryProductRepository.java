package com.divyanshgoenka.headyassessment.repository;

import com.divyanshgoenka.headyassessment.Constants;
import com.divyanshgoenka.headyassessment.api.HeadyService;
import com.divyanshgoenka.headyassessment.pojo.Category;
import com.divyanshgoenka.headyassessment.pojo.CategoryRankingData;
import com.divyanshgoenka.headyassessment.pojo.Product;
import com.divyanshgoenka.headyassessment.pojo.Ranking;
import com.divyanshgoenka.headyassessment.util.Validations;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.Callable;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import okhttp3.OkHttpClient;
import okhttp3.internal.cache.DiskLruCache;
import okhttp3.internal.io.FileSystem;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by divyanshgoenka on 05/02/18.
 */

public class CategoryProductRepository {
    private HeadyService categoryProductApi;
    private CategoryRankingData lastFetchedDataInMemory;
    private final List<Ranking> rankedProducts = new ArrayList<>();
    private final HashMap<Long,Product> productHashMap = new HashMap<>();
    @Inject
    public CategoryProductRepository(){


        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constants.API_END_POINT)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();

        categoryProductApi = retrofit.create(HeadyService.class);
    }



    public Observable<List<Ranking>> getProductsByRank(){

        if(rankedProducts==null|| lastFetchedDataInMemory == null || dataIsTooOld(lastFetchedDataInMemory.getFetchedAt())){
            return createObservableToProcessCategoryProductInfo(createCategoryRankingDataObservable());
        }

        return Observable.just(rankedProducts);
    }

    private Observable<CategoryRankingData> createCategoryRankingDataObservable(){
        return categoryProductApi.getData().doOnNext(new Consumer<CategoryRankingData>() {
            @Override
            public void accept(CategoryRankingData categoryRankingData) throws Exception {
                    categoryRankingData.setFetchedAt(System.currentTimeMillis());
            }
        });
    }

    private Observable<List<Ranking>> createObservableToProcessCategoryProductInfo(Observable<CategoryRankingData> categoryRankingDataObservable) {
        return categoryRankingDataObservable.flatMap(new Function<CategoryRankingData, ObservableSource<List<Ranking>>>() {
            @Override
            public ObservableSource<List<Ranking>> apply(CategoryRankingData categoryRankingData) throws Exception {
                return Observable.fromCallable(new Callable<List<Ranking>>() {
                    @Override
                    public List<Ranking> call() throws Exception {
                        return processAndReturnCategoryRankData(categoryRankingData);
                    }
                });
            }
        });
    }

    private List<Ranking> processAndReturnCategoryRankData(CategoryRankingData categoryRankingData) {
        rankedProducts.clear();
        productHashMap.clear();
        if(!Validations.isEmptyOrNull(categoryRankingData.getRankings())){
            for(Category category:categoryRankingData.getCategories()){
                for(Product product : category.getProducts()){
                    productHashMap.put(product.getId(),product);
                }
            }
            for(Ranking ranking:categoryRankingData.getRankings())
            {
                List<Product> products = ranking.getProducts();
                Collections.sort(products, new Comparator<Product>() {
                    @Override
                    public int compare(Product p1, Product p2) {
                        Product p1_full = productHashMap.get(p1.getId());
                        Product p2_full = productHashMap.get(p2.getId());
                        p1_full.setCountsSafely(p1);
                        p2_full.setCountsSafely(p2);

                        return (int) (p2.getCountForRanking(ranking.getRanking())-p1.getCountForRanking(ranking.getRanking()));
                    }
                });
            }
        }
        return rankedProducts;
    }

    private boolean dataIsTooOld(Long lastFatchTime) {
        return lastFatchTime!=null&&(System.currentTimeMillis()-lastFatchTime>= Constants.DATA_REFRESH_THRESHOLD);
    }

    public Long getListVersionAt() {
        return lastFetchedDataInMemory.getFetchedAt();
    }
}
