package com.divyanshgoenka.headyassessment.view;

import com.divyanshgoenka.headyassessment.pojo.Product;
import com.divyanshgoenka.headyassessment.pojo.Ranking;

import java.util.List;

/**
 * Created by divyanshgoenka on 04/02/18.
 */

public interface RankingView {
    public void showProducts(List<Ranking> productIterable, Long listVersionAt);
    public void setLoading(boolean loading);
}
