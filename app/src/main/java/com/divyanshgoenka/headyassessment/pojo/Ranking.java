
package com.divyanshgoenka.headyassessment.pojo;

import java.io.Serializable;
import java.util.List;
import com.google.gson.annotations.SerializedName;


public class Ranking  implements Serializable {

    @SerializedName("products")
    private List<Product> mProducts;

    @Override
    public String toString() {
        return mRanking;
    }

    @SerializedName("ranking")
    private String mRanking;

    public List<Product> getProducts() {
        return mProducts;
    }

    public String getRanking() {
        return mRanking;
    }

    public static class Builder {

        private List<Product> mProducts;
        private String mRanking;

        public Ranking.Builder withProducts(List<Product> products) {
            mProducts = products;
            return this;
        }

        public Ranking.Builder withRanking(String ranking) {
            mRanking = ranking;
            return this;
        }

        public Ranking build() {
            Ranking Ranking = new Ranking();
            Ranking.mProducts = mProducts;
            Ranking.mRanking = mRanking;
            return Ranking;
        }

    }

}
