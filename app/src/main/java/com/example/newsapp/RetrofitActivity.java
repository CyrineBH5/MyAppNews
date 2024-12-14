package com.example.newsapp;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitActivity {
    private static final String BASE_URL = "https://newsapi.org/v2/";
    private static Retrofit retrofit;

    public static Retrofit getRetrofitInstance() {
        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(new OkHttpClient.Builder()
                            .addInterceptor(chain -> {
                                Request newRequest = chain.request().newBuilder()
                                        .addHeader("User-Agent", "AndroidApp")
                                        .build();
                                return chain.proceed(newRequest);
                            })
                            .build())
                    .build();
        }
        return retrofit;
    }
}
