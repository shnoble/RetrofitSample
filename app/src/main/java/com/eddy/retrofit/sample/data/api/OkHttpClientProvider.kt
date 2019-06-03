package com.eddy.retrofit.sample.data.api

import android.support.annotation.CheckResult
import okhttp3.OkHttpClient

object OkHttpClientProvider {
    @CheckResult
    fun provideOkHttpClientForGithub(): OkHttpClient = OkHttpClient.Builder().build()
}

