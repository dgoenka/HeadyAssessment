package com.divyanshgoenka.headyassessment.api;

import com.divyanshgoenka.headyassessment.Constants;
import com.divyanshgoenka.headyassessment.di.Implementor;

import java.io.IOException;

import okhttp3.Cache;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by divyanshgoenka on 23/02/18.
 */


public class RetrofitClientProvider {
    public static HeadyService buildRetrofitClient() {

        OkHttpClient client = new OkHttpClient
                .Builder()
                .cache(new Cache(Implementor.getCurrentImplementation().getCacheDir(), 10 * 1024 * 1024)) // 10 MB
                .addInterceptor(new Interceptor() {
                    @Override
                    public Response intercept(Chain chain) throws IOException {
                        Request request = chain.request();
                        if (Implementor.getCurrentImplementation().isNetworkAvailable()) {
                            request = request.newBuilder().header("Cache-Control", "public, max-age=" + 60).build();
                        } else {
                            request = request.newBuilder().header("Cache-Control", "public, only-if-cached, max-stale=" + 60 * 60 * 24 * 7).build();
                        }
                        return chain.proceed(request);
                    }
                })
                .build();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constants.API_END_POINT)
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();

        return retrofit.create(HeadyService.class);
    }
}
