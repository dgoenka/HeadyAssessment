
package com.divyanshgoenka.headyassessment.pojo;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;


public class Variant implements Serializable {

    @SerializedName("color")
    private String mColor;
    @SerializedName("id")
    private Long mId;
    @SerializedName("price")
    private Long mPrice;
    @SerializedName("size")
    private Long mSize;

    public String getColor() {
        return mColor;
    }

    public Long getId() {
        return mId;
    }

    public Long getPrice() {
        return mPrice;
    }

    public Long getSize() {
        return mSize;
    }

    public static class Builder {

        private String mColor;
        private Long mId;
        private Long mPrice;
        private Long mSize;

        public Variant.Builder withColor(String color) {
            mColor = color;
            return this;
        }

        public Variant.Builder withId(Long id) {
            mId = id;
            return this;
        }

        public Variant.Builder withPrice(Long price) {
            mPrice = price;
            return this;
        }

        public Variant.Builder withSize(Long size) {
            mSize = size;
            return this;
        }

        public Variant build() {
            Variant Variant = new Variant();
            Variant.mColor = mColor;
            Variant.mId = mId;
            Variant.mPrice = mPrice;
            Variant.mSize = mSize;
            return Variant;
        }

    }

}
