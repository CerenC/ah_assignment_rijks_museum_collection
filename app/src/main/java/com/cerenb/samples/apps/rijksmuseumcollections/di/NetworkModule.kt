package com.cerenb.samples.apps.rijksmuseumcollections.di

import com.cerenb.samples.apps.rijksmuseumcollections.data.datasources.remote.interceptor.AuthenticationInterceptor
import com.cerenb.samples.apps.rijksmuseumcollections.data.datasources.remote.service.ArtCollectionService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class NetworkModule {

    @Singleton
    @Provides
    fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(MoshiConverterFactory.create())
            .client(okHttpClient)
            .build()
    }

    @Singleton
    @Provides
    fun provideLoggingInterceptor(): HttpLoggingInterceptor =
        HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)

    @Singleton
    @Provides
    fun provideNetworkInterceptor(): AuthenticationInterceptor = AuthenticationInterceptor()

    @Singleton
    @Provides
    fun provideOkHttpClient(
        httpLoggingInterceptor: HttpLoggingInterceptor,
        authenticationInterceptor: AuthenticationInterceptor
    ): OkHttpClient {
        return OkHttpClient.Builder()
            .readTimeout(READ_TIMEOUT_SEC, TimeUnit.SECONDS)
            .connectTimeout(CONNECT_TIMEOUT_SEC, TimeUnit.SECONDS)
            .addInterceptor(httpLoggingInterceptor)
            .addInterceptor(authenticationInterceptor)
            .build()
    }

    @Singleton
    @Provides
    fun provideArtCollectionService(retrofit: Retrofit): ArtCollectionService =
        retrofit.create(ArtCollectionService::class.java)

    companion object {
        private const val BASE_URL = "https://www.rijksmuseum.nl/api/nl/"
        private const val READ_TIMEOUT_SEC = 30L
        private const val CONNECT_TIMEOUT_SEC = 30L
    }


}