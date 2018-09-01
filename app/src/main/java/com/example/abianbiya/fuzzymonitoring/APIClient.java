package com.example.abianbiya.fuzzymonitoring;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class APIClient {
    private static String baseUrl = "http://devkimedia.cokilabs.com/" ;
    private static OkHttpClient.Builder httpClient = new OkHttpClient.Builder();

    public static APIInterface getClient() {

//        final String refer = "CROCODICHEBAT";
//        final String ua = "Dalvik/1.6.0 (Linux; U; Android 4.0.2; sdk Build/ICS_MR0";

        httpClient.addInterceptor(new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Request original = chain.request();

                Request.Builder requestBuilder = original.newBuilder()
//                        .header("Referer", refer)
                        .method(original.method(), original.body());

                //.removeHeader("User-Agent")
                // .addHeader("User-Agent", ua)

                Request request = requestBuilder.build();
                return chain.proceed(request);
            }
        });

        OkHttpClient client = httpClient
                .readTimeout(30, TimeUnit.SECONDS)
                .connectTimeout(30, TimeUnit.SECONDS)
                .build();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build();
        //.addConverterFactory(GsonConverterFactory.create())

        return retrofit.create(APIInterface.class);
    }
}