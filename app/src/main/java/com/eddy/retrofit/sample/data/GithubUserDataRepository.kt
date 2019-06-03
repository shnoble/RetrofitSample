package com.eddy.retrofit.sample.data

import android.support.annotation.CheckResult
import com.eddy.retrofit.sample.data.api.GithubApi

class GithubUserDataRepository(
    private val api: GithubApi
) : GithubUserRepository {
    @CheckResult
    override fun getUser(userName: String) = api.getUser(userName)
}