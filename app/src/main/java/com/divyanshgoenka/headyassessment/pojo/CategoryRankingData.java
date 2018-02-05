
package com.divyanshgoenka.headyassessment.pojo;

import java.io.Serializable;
import java.util.List;
import com.google.gson.annotations.SerializedName;

public class CategoryRankingData  implements Serializable {

    @SerializedName("categories")
    private List<Category> mCategories;
    @SerializedName("rankings")
    private List<Ranking> mRankings;

    private Long fetchedAt;

    public Long getFetchedAt() {
        return fetchedAt;
    }

    public void setFetchedAt(Long fetchedAt) {
        if(this.fetchedAt==null)
        this.fetchedAt = fetchedAt;
    }

    public List<Category> getCategories() {
        return mCategories;
    }

    public List<Ranking> getRankings() {
        return mRankings;
    }

    public static class Builder {

        private List<Category> mCategories;
        private List<Ranking> mRankings;

        public CategoryRankingData.Builder withCategories(List<Category> categories) {
            mCategories = categories;
            return this;
        }

        public CategoryRankingData.Builder withRankings(List<Ranking> rankings) {
            mRankings = rankings;
            return this;
        }

        public CategoryRankingData build() {
            CategoryRankingData CategoryRankingData = new CategoryRankingData();
            CategoryRankingData.mCategories = mCategories;
            CategoryRankingData.mRankings = mRankings;
            return CategoryRankingData;
        }

    }

}
