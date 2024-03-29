package com.example.livecrickettvscores.Activities.Retrofit;

import android.content.Context;

import androidx.annotation.NonNull;

import com.example.livecrickettvscores.BuildConfig;
import com.grapesnberries.curllogger.CurlLoggerInterceptor;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class RetroFit_APIClient {


    public static RetroFit_APIClient apiClient;
    private Retrofit retrofit = null;

    public static RetroFit_APIClient getInstance() {
        if (apiClient == null) {
            apiClient = new RetroFit_APIClient();
        }
        return apiClient;
    }

    public Retrofit getClient(final Context context, final String BASE_URL, final String x_rapidapiikey, final String x_rapidapi_host) {
        OkHttpClient.Builder client = new OkHttpClient.Builder();

        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();

        if (BuildConfig.DEBUG) {
            interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);   // development build
        } else {
            interceptor.setLevel(HttpLoggingInterceptor.Level.NONE);    // production build
//            interceptor.setLevel(HttpLoggingInterceptor.Level.HEADERS);    // production build
        }
        client.addInterceptor(new Interceptor() {
            @Override
            public okhttp3.Response intercept(Chain chain) throws IOException {
                Request request = chain.request().newBuilder().addHeader("X-RapidAPI-Key", x_rapidapiikey).addHeader("X-RapidAPI-Host", x_rapidapi_host).build();
                return chain.proceed(request);
            }
        });
        client.addInterceptor(interceptor);

        client.readTimeout(60, TimeUnit.SECONDS);
        client.writeTimeout(60, TimeUnit.SECONDS);
        client.connectTimeout(60, TimeUnit.SECONDS);
        client.addInterceptor(new CurlLoggerInterceptor());
        retrofit = new Retrofit.Builder().baseUrl(BASE_URL).client(client.build()).addConverterFactory(ScalarsConverterFactory.create()).addConverterFactory(GsonConverterFactory.create()).build();


        return retrofit;
    }
}

