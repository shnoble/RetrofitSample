package com.eddy.retrofit.sample.data.api

import android.support.annotation.CheckResult
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitProvider {
    @CheckResult
    fun provideRetrofitForGithub(client: OkHttpClient): Retrofit =
            Retrofit.Builder()
                .client(client)
                .baseUrl("https://api.github.com")
                .addConverterFactory(GsonConverterFactory.create())
                .build()
}