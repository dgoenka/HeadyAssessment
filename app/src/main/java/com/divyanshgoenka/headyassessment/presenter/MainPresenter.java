package com.divyanshgoenka.headyassessment.presenter;

import com.divyanshgoenka.headyassessment.android.fragment.RankingFragment;
import com.divyanshgoenka.headyassessment.android.rx.AndroidSchedulersFacade;
import com.divyanshgoenka.headyassessment.pojo.Product;
import com.divyanshgoenka.headyassessment.pojo.Ranking;
import com.divyanshgoenka.headyassessment.repository.CategoryProductRepository;
import com.divyanshgoenka.headyassessment.rx.SchedulersFacade;
import com.divyanshgoenka.headyassessment.view.MainView;
import com.divyanshgoenka.headyassessment.view.RankingView;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

/**
 * Created by divyanshgoenka on 04/02/18.
 */

public class MainPresenter {



    MainView mainView;
    RankingView rankingView;
    SchedulersFacade schedulersFacade;
    CategoryProductRepository categoryProductRepository;


    @Inject
    public MainPresenter(MainView mainView,CategoryProductRepository categoryProductRepository,SchedulersFacade schedulersFacade){
        this.mainView = mainView;
        this.categoryProductRepository = categoryProductRepository;
        this.schedulersFacade = schedulersFacade;
    }

    public void switchToProducts() {
        mainView.switchToProducts();
    }

    public void switchToRankings() {
        mainView.switchToRankings();
    }


    public void setMainView(MainView mainView) {
        this.mainView = mainView;
    }

    public void setRankingView(RankingFragment rankingView) {
        this.rankingView = rankingView;
    }

    public void loadRankings() {
        categoryProductRepository.getProductsByRank().subscribeOn(schedulersFacade.io()).observeOn(schedulersFacade.ui()).subscribe(new Observer<List<Ranking>>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(List<Ranking> rankings) {
                if(rankingView!=null)
                    rankingView.showProducts(rankings, categoryProductRepository.getListVersionAt());
            }

            @Override
            public void onError(Throwable e) {
                if(rankingView!=null)
                    rankingView.onLoadRankingException(e);
            }

            @Override
            public void onComplete() {

            }


        });
    }

    public void onProductClicked(Product product) {
        //TODO Detail View of Product
    }
}
