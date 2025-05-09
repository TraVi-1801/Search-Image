package com.vic_project.search_image.di.core

import com.google.gson.GsonBuilder
import com.vic_project.search_image.data.EnvironmentManager
import com.vic_project.search_image.data.remote.network.InternetAvailabilityRepository
import com.vic_project.search_image.data.remote.network.NetworkMonitor
import com.vic_project.search_image.data.remote.service.ApiService
import com.vic_project.search_image.data.remote.service.NoInternetInterceptor
import com.vic_project.search_image.utils.NetworkConstant.IOException
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create
import java.io.IOException
import java.util.concurrent.TimeUnit
import javax.inject.Qualifier
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Qualifier
    @Retention(AnnotationRetention.BINARY)
    annotation class MainInterceptor

    @Qualifier
    @Retention(AnnotationRetention.BINARY)
    annotation class OtherInterceptor


    @MainInterceptor
    @Provides
    @Singleton
    fun provideOkHttpClient(
        internetRepo: InternetAvailabilityRepository
    ): OkHttpClient {
        val logging = HttpLoggingInterceptor()
        logging.level = HttpLoggingInterceptor.Level.BODY
        return OkHttpClient.Builder()
            .addInterceptor(NoInternetInterceptor(internetRepo))
            .addInterceptor(logging)
            .connectTimeout(30, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)
            .writeTimeout(10, TimeUnit.SECONDS)
            .build()
    }

    @OtherInterceptor
    @Provides
    @Singleton
    fun provideOkHttpClientFile(
        internetRepo: InternetAvailabilityRepository
    ): OkHttpClient {
        val logging = HttpLoggingInterceptor()
        logging.level = HttpLoggingInterceptor.Level.BASIC
        return OkHttpClient.Builder()
            .addInterceptor(NoInternetInterceptor(internetRepo))
            .addInterceptor(logging)
            .connectTimeout(30, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)
            .writeTimeout(10, TimeUnit.SECONDS)
            .build()
    }

    @MainInterceptor
    @Provides
    @Singleton
    fun provideRetrofit(@MainInterceptor okHttpClient: OkHttpClient): Retrofit {
        val gson = GsonBuilder()
            .setLenient()
            .create()
        return Retrofit.Builder()
            .client(okHttpClient)
            .baseUrl(EnvironmentManager.baseUrl.url_v1)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
    }

    @OtherInterceptor
    @Provides
    @Singleton
    fun provideRetrofitFile(@OtherInterceptor okHttpClient: OkHttpClient): Retrofit {
        val gson = GsonBuilder()
            .setLenient()
            .create()
        return Retrofit.Builder()
            .client(okHttpClient)
            .baseUrl(EnvironmentManager.baseUrl.url_v1)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
    }

    @MainInterceptor
    @Provides
    @Singleton
    fun provideApiService(@MainInterceptor retrofit: Retrofit): ApiService = retrofit.create()

    @OtherInterceptor
    @Provides
    @Singleton
    fun provideApiServiceFile(@OtherInterceptor retrofit: Retrofit): ApiService = retrofit.create()
}