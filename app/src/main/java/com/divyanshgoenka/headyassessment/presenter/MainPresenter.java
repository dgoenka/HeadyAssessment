package com.divyanshgoenka.headyassessment.presenter;

import com.divyanshgoenka.headyassessment.android.fragment.ProductFragment;
import com.divyanshgoenka.headyassessment.android.fragment.RankingFragment;
import com.divyanshgoenka.headyassessment.log.Logger;
import com.divyanshgoenka.headyassessment.pojo.Category;
import com.divyanshgoenka.headyassessment.pojo.Listable;
import com.divyanshgoenka.headyassessment.pojo.Product;
import com.divyanshgoenka.headyassessment.pojo.Ranking;
import com.divyanshgoenka.headyassessment.repository.CategoryProductRepository;
import com.divyanshgoenka.headyassessment.rx.SchedulersFacade;
import com.divyanshgoenka.headyassessment.view.MainView;
import com.divyanshgoenka.headyassessment.view.ProductView;
import com.divyanshgoenka.headyassessment.view.RankingView;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observer;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;


/**
 * Created by divyanshgoenka on 04/02/18.
 */

public class MainPresenter implements BasePresenter<MainView> {

    MainView mainView;
    RankingView rankingView;
    SchedulersFacade schedulersFacade;
    CategoryProductRepository categoryProductRepository;
    CompositeDisposable productsCompositeDisposable = new CompositeDisposable();
    private ProductView productView;

    @Inject
    public MainPresenter(MainView mainView, CategoryProductRepository categoryProductRepository, SchedulersFacade schedulersFacade) {
        this.mainView = mainView;
        this.categoryProductRepository = categoryProductRepository;
        this.schedulersFacade = schedulersFacade;
    }

    public void switchToProducts() {
        if (mainView != null) {
            mainView.switchToProducts();
        }
    }

    public void switchToRankings() {
        if (mainView != null) {
            mainView.switchToRankings();
        }
    }


    public void setRankingView(RankingFragment rankingView) {
        this.rankingView = rankingView;
    }

    public void loadRankings() {
        categoryProductRepository.getProductsByRank().subscribeOn(schedulersFacade.io()).observeOn(schedulersFacade.ui()).subscribe(new Observer<List<Ranking>>() {
            @Override
            public void onSubscribe(Disposable d) {
                if (rankingView != null) {
                    rankingView.showLoading();
                }
            }

            @Override
            public void onNext(List<Ranking> rankings) {
                if (rankingView != null) {
                    rankingView.showProducts(rankings, categoryProductRepository.getListVersionAt());
                }
            }

            @Override
            public void onError(Throwable e) {
                if (rankingView != null) {
                    rankingView.onLoadRankingException(e);
                }
            }

            @Override
            public void onComplete() {
                if (rankingView != null) {
                    rankingView.hideLoading();
                }
            }


        });
    }

    public void onProductClicked(Product product) {
        if (mainView != null) {
            mainView.showProduct(product);
        }
    }

    @Override
    public void set(MainView mainView) {
        this.mainView = mainView;
    }

    public void setProductView(ProductFragment productView) {
        this.productView = productView;
    }

    public void loadCategoriesAndProducts(int position, Category mCategory) {
        Logger.d("inside loadCategoriesAndProducts, mCategory is" + mCategory);
        categoryProductRepository.getChildrenOf(mCategory).subscribeOn(schedulersFacade.io()).observeOn(schedulersFacade.ui()).subscribe(new Observer<ArrayList<Listable>>() {
            @Override
            public void onSubscribe(Disposable d) {
                if (productView != null) {
                    productView.showLoading();
                }
            }

            @Override
            public void onNext(ArrayList<Listable> listables) {
                Logger.d("In onNext, ");
                if (productView != null) {
                    productView.showList(position, listables, mCategory);
                }
            }

            @Override
            public void onError(Throwable e) {
                if (productView != null) {
                    productView.showError();
                }
            }

            @Override
            public void onComplete() {
                if (productView != null) {
                    productView.hideLoading();
                }
            }
        });
    }

    public void onCategoryClicked(Category category) {
        Logger.d("in onCategoryClicked");
        if (productView != null) {
            productView.onCategoryClicked(category);
        }
    }
}
