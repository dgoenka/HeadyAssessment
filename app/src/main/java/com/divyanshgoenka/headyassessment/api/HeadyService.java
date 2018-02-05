package com.divyanshgoenka.headyassessment.api;

import com.divyanshgoenka.headyassessment.pojo.CategoryRankingData;


import io.reactivex.Observable;
import retrofit2.http.GET;

/**
 * Created by divyanshgoenka on 04/02/18.
 */

public interface HeadyService {
    @GET("/json")
    Observable<CategoryRankingData> getData();
}
