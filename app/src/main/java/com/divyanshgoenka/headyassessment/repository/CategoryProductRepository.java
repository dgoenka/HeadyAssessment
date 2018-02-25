package com.divyanshgoenka.headyassessment.repository;

import com.divyanshgoenka.headyassessment.Constants;
import com.divyanshgoenka.headyassessment.api.HeadyService;
import com.divyanshgoenka.headyassessment.log.Logger;
import com.divyanshgoenka.headyassessment.pojo.Category;
import com.divyanshgoenka.headyassessment.pojo.CategoryList;
import com.divyanshgoenka.headyassessment.pojo.CategoryRankingData;
import com.divyanshgoenka.headyassessment.pojo.Listable;
import com.divyanshgoenka.headyassessment.pojo.Product;
import com.divyanshgoenka.headyassessment.pojo.Ranking;
import com.divyanshgoenka.headyassessment.util.Validations;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;

import io.reactivex.Observable;


/**
 * Created by divyanshgoenka on 05/02/18.
 */

public class CategoryProductRepository {


    private final HashMap<Long, Product> productHashMap = new HashMap<>();
    HeadyService headyService;
    private CategoryRankingData lastFetchedDataInMemory;
    private HashMap<Integer, Category> categoryMap;

    public CategoryProductRepository(HeadyService headyService) {
        this.headyService = headyService;
    }


    public Observable<ArrayList<Listable>> getChildrenOf(Category category) {
        Logger.d("inside getChildrenOf, shouldRefetchData is" + shouldRefetchData());
        if (shouldRefetchData()) {
            return createObservableToProcessCategoryProductTransversalInfo(createCategoryRankingDataObservable(), category);
        }
        return createObservableToProcessCategoryProductTransversalInfo(Observable.just(lastFetchedDataInMemory), category);
    }

    private Observable<ArrayList<Listable>> createObservableToProcessCategoryProductTransversalInfo(Observable<CategoryRankingData> categoryRankingDataObservable, Category category) {
        Logger.d("inside createObservableToProcessCategoryProductTransversalInfo, category is" + category);
        return categoryRankingDataObservable.flatMap(categoryRankingData -> Observable.fromCallable(() -> {
            Logger.d("inside the observable of createObservableToProcessCategoryProductTransversalInfo, categoryRankingData is" + categoryRankingData + " category is " + category);
            return processAndReturnTransversalData(categoryRankingData, category);
        }));
    }


    private ArrayList<Listable> processAndReturnTransversalData(CategoryRankingData categoryRankingData, Category category) {
        Logger.d("in processAndReturnTransversalData category is" + category);
        if (category == null) {
            Logger.d("in processAndReturnTransversalData going to findAndAddCategoriesAndProductsAtRoot");

            return findAndAddCategoriesAndProductsAtRoot(categoryRankingData);
        }

        return findAndAddCategoriesAndProductsIn(categoryRankingData, category);
    }

    private ArrayList<Listable> findAndAddCategoriesAndProductsAtRoot(CategoryRankingData categoryRankingData) {
        ArrayList<Listable> items = new ArrayList<>();
        CategoryList categoryList = new CategoryList();
        for (Category categoryFromList : categoryRankingData.getCategories()) {
            Logger.d("Checking for category, " + categoryFromList.getId() + ". " + categoryFromList.getName());
            boolean isRootCategory = true;
            for (Category categoryToCheckIn : categoryRankingData.getCategories()) {
                Logger.d("in category, " + categoryToCheckIn.getId() + ". " + categoryToCheckIn.getName());

                if (categoryFromList.getId() != categoryToCheckIn.getId()) {
                    List<Integer> childCategories = categoryToCheckIn.getChildCategories();
                    for (Integer childCategory : childCategories) {
                        if (childCategory.longValue() == categoryFromList.getId()) {
                            isRootCategory = false;
                            Logger.d(categoryFromList.getName() + " is a child of " + categoryToCheckIn.getName());
                            break;
                        }
                    }
                    if (!isRootCategory) {
                        break;
                    }
                }
            }

            Logger.d("Checking for category, " + categoryFromList.getName() + " isRootCategory? " + isRootCategory);


            if (isRootCategory) {
                categoryList.add(categoryFromList);
            }
        }
        if (!Validations.isEmptyOrNull(categoryList)) {
            items.add(categoryList);
        }
        return items;
    }

    private ArrayList<Listable> findAndAddCategoriesAndProductsIn(CategoryRankingData categoryRankingData, Category category) {
        ArrayList<Listable> items = new ArrayList<>();
        CategoryList categoryList = new CategoryList();
        List<Integer> childCategories = category.getChildCategories();
        for (Integer childCategory : childCategories) {
            for (Category categoryFromList : categoryRankingData.getCategories()) {
                if (childCategory.longValue() == categoryFromList.getId()) {
                    categoryList.add(categoryFromList);
                }
            }
        }
        if (!Validations.isEmptyOrNull(categoryList)) {
            items.add(categoryList);
        }
        
        for (Product product : category.getProducts()) {
            items.add(product);
        }

        return items;
    }


    public Observable<List<Ranking>> getProductsByRank(){

        if (shouldRefetchData()) {
            return createObservableToProcessCategoryProductInfo(createCategoryRankingDataObservable());
        }

        return Observable.just(lastFetchedDataInMemory.getRankings());
    }

    public boolean shouldRefetchData() {
        return lastFetchedDataInMemory == null || dataIsTooOld(lastFetchedDataInMemory.getFetchedAt());
    }


    private Observable<CategoryRankingData> createCategoryRankingDataObservable(){
        return headyService.getData();
    }

    private Observable<List<Ranking>> createObservableToProcessCategoryProductInfo(Observable<CategoryRankingData> categoryRankingDataObservable) {
        return categoryRankingDataObservable.flatMap(categoryRankingData -> {
            categoryRankingData.setFetchedAt(System.currentTimeMillis());
            lastFetchedDataInMemory = categoryRankingData;
            return Observable.fromCallable(() -> processAndReturnCategoryRankData(categoryRankingData));
        });
    }

    private List<Ranking> processAndReturnCategoryRankData(CategoryRankingData categoryRankingData) {
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
                        p1.setName(p1_full.getName());
                        p2.setName(p2_full.getName());
                        //TODO - more fields / effective deep copy mechanism

                        return (int) (p2.getCountForRanking(ranking.getRanking())-p1.getCountForRanking(ranking.getRanking()));
                    }
                });
            }
        }
        return categoryRankingData.getRankings();
    }

    private boolean dataIsTooOld(Long lastFatchTime) {
        return lastFatchTime!=null&&(System.currentTimeMillis()-lastFatchTime>= Constants.DATA_REFRESH_THRESHOLD);
    }

    public Long getListVersionAt() {
        return lastFetchedDataInMemory.getFetchedAt();
    }
}
