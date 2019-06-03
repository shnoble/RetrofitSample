package com.eddy.retrofit.sample.data

import android.support.annotation.CheckResult
import com.eddy.retrofit.sample.model.User
import retrofit2.Call

interface GithubUserRepository {
    @CheckResult
    fun getUser(userName: String): Call<User>
}

