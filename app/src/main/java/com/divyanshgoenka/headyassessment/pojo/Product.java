
package com.divyanshgoenka.headyassessment.pojo;

import java.io.Serializable;
import java.util.List;
import com.google.gson.annotations.SerializedName;


public class Product implements Listable,Serializable {

    @SerializedName("date_added")
    private String mDateAdded;
    @SerializedName("id")
    private Long mId;
    @SerializedName("name")
    private String mName;
    @SerializedName("tax")
    private Tax mTax;
    @SerializedName("variants")
    private List<Variant> mVariants;
    @SerializedName("view_count")
    private Long mViewCount;
    @SerializedName("order_count")
    private Long mOrderCount;

    public Long getOrderCount() {
        return mOrderCount;
    }

    public void setOrderCount(Long mOrderCount) {
        this.mOrderCount = mOrderCount;
    }

    public Long getShares() {
        return mShares;
    }

    public void setShares(Long mShares) {
        this.mShares = mShares;
    }

    @SerializedName("shares")

    private Long mShares;
    public void setDateAdded(String mDateAdded) {
        this.mDateAdded = mDateAdded;
    }

    public void setId(Long mId) {
        this.mId = mId;
    }

    public void setName(String mName) {
        this.mName = mName;
    }

    public void setTax(Tax mTax) {
        this.mTax = mTax;
    }

    public void setVariants(List<Variant> mVariants) {
        this.mVariants = mVariants;
    }

    public void setViewCount(Long mViewCount) {
        this.mViewCount = mViewCount;
    }

    public String getDateAdded() {
        return mDateAdded;
    }

    public Long getId() {
        return mId;
    }

    public String getName() {
        return mName;
    }

    public Tax getTax() {
        return mTax;
    }

    public List<Variant> getVariants() {
        return mVariants;
    }

    public Long getViewCount() {
        return mViewCount;
    }

    public void setCountsSafely(Product p2) {
        if(p2!=null){

            if(getViewCount()==null && p2.getViewCount()!=null)
                setViewCount(p2.getViewCount());
            if(getOrderCount()==null && p2.getOrderCount()!=null)
                setOrderCount(p2.getOrderCount());
            if(getShares()==null && p2.getShares() !=null)
                setShares(p2.getShares());

        }
    }

    public Long getCountForRanking(String ranking) {
        ranking = ranking.toLowerCase();
        if(ranking.contains("share")) return getShares();
        if(ranking.contains("order")) return getOrderCount();
        return getViewCount();
    }


    public static class Builder {

        private String mDateAdded;
        private Long mId;
        private String mName;
        private Tax mTax;
        private List<Variant> mVariants;
        private Long mViewCount;

        public Product.Builder withDateAdded(String dateAdded) {
            mDateAdded = dateAdded;
            return this;
        }

        public Product.Builder withId(Long id) {
            mId = id;
            return this;
        }

        public Product.Builder withName(String name) {
            mName = name;
            return this;
        }

        public Product.Builder withTax(Tax tax) {
            mTax = tax;
            return this;
        }

        public Product.Builder withVariants(List<Variant> variants) {
            mVariants = variants;
            return this;
        }

        public Product.Builder withViewCount(Long viewCount) {
            mViewCount = viewCount;
            return this;
        }

        public Product build() {
            Product Product = new Product();
            Product.mDateAdded = mDateAdded;
            Product.mId = mId;
            Product.mName = mName;
            Product.mTax = mTax;
            Product.mVariants = mVariants;
            Product.mViewCount = mViewCount;
            return Product;
        }

    }

}
