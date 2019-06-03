package com.eddy.retrofit.sample.data.api

import android.support.annotation.CheckResult
import com.eddy.retrofit.sample.model.User
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface GithubApi {
    @GET("/users/{userName}")
    @CheckResult
    fun getUser(@Path("userName") userName: String): Call<User>
}