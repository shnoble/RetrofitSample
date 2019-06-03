package com.eddy.retrofit.sample

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import com.eddy.retrofit.sample.data.GithubUserDataRepository
import com.eddy.retrofit.sample.data.GithubUserRepository
import com.eddy.retrofit.sample.data.api.ApiProvider
import com.eddy.retrofit.sample.data.api.OkHttpClientProvider
import com.eddy.retrofit.sample.data.api.RetrofitProvider
import com.eddy.retrofit.sample.model.User
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {

    lateinit var githubUserRepository: GithubUserRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val okHttpClient = OkHttpClientProvider.provideOkHttpClient()
        val retrofit = RetrofitProvider.provideRetrofit(okHttpClient, "https://api.github.com")
        val api = ApiProvider.provideApiForGithub(retrofit)
        githubUserRepository = GithubUserDataRepository(api)

        val response = githubUserRepository.getUser("shnoble")
        val callback = object : Callback<User> {
            override fun onFailure(call: Call<User>, t: Throwable) {
                Log.e(TAG, "Error: ${t.message}")
            }

            override fun onResponse(call: Call<User>, response: Response<User>) {
                Log.d(TAG, response.toString())
                Log.d(TAG, response.body().toString())
            }
        }
        response.enqueue(callback)
    }

    companion object {
        const val TAG = "MainActivity"
    }
}