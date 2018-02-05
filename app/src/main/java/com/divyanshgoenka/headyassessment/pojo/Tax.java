
package com.divyanshgoenka.headyassessment.pojo;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;


public class Tax  implements Serializable {

    @SerializedName("name")
    private String mName;
    @SerializedName("value")
    private Double mValue;

    public String getName() {
        return mName;
    }

    public Double getValue() {
        return mValue;
    }

    public static class Builder {

        private String mName;
        private Double mValue;

        public Tax.Builder withName(String name) {
            mName = name;
            return this;
        }

        public Tax.Builder withValue(Double value) {
            mValue = value;
            return this;
        }

        public Tax build() {
            Tax Tax = new Tax();
            Tax.mName = mName;
            Tax.mValue = mValue;
            return Tax;
        }

    }

}
