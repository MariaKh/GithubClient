package com.githubclient.di;

import com.githubclient.network.GithubApi;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.concurrent.TimeUnit;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by 1 on 3/27/2018.
 */

@Singleton
@Module
public class NetworkModule {

//    @Provides
//    @Singleton
//    public Interceptor provideAuthInterceptor() {
//        return (chain) -> {
//            Request original = chain.request();
//            Request.Builder builder = original.newBuilder();
//            Request request = builder.method(original.method(), original.body())
//                    .build();
//            return chain.proceed(request);
//        };
//    }

    @Provides
    @Singleton
    public OkHttpClient provideOkHttpClient() {
        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();

        httpClient
                .connectTimeout(60, TimeUnit.SECONDS)
                .readTimeout(60, TimeUnit.SECONDS)
                .writeTimeout(60, TimeUnit.SECONDS);
        httpClient.retryOnConnectionFailure(false);
        HttpLoggingInterceptor logging=new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);
        httpClient.addInterceptor(logging);
        return httpClient.build();
    }

    @Provides
    @Singleton
    public Retrofit provideRetrofit(OkHttpClient httpClient) {
        return new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create(gson))
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .baseUrl("https://api.github.com/")
                .client(httpClient)
                .build();
    }

    @Singleton
    @Provides
    GithubApi provideGithubApi(Retrofit retrofit) {
        return retrofit.create(GithubApi.class);
    }

    public static final Gson gson = new GsonBuilder()
           // .setLenient()
            .create();

    @Provides
    @Singleton
    public Gson provideGson() {
        return gson;
    }
}
