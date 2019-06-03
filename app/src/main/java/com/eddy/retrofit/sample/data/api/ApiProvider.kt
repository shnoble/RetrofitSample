package com.eddy.retrofit.sample.data.api

import android.support.annotation.CheckResult
import retrofit2.Retrofit

object ApiProvider {
    @CheckResult
    fun provideApiForGithub(retrofit: Retrofit): GithubApi = retrofit.create(GithubApi::class.java)
}