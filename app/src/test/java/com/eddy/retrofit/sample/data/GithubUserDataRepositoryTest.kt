package com.eddy.retrofit.sample.data

import com.eddy.retrofit.sample.data.api.ApiProvider
import com.eddy.retrofit.sample.data.api.OkHttpClientProvider
import com.eddy.retrofit.sample.data.api.RetrofitProvider
import junit.framework.Assert.assertEquals
import junit.framework.Assert.assertFalse
import okhttp3.mockwebserver.Dispatcher
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import okhttp3.mockwebserver.RecordedRequest
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner

@RunWith(RobolectricTestRunner::class)
class GithubUserDataRepositoryTest {

    private val baseUrl = "/"
    private val mockWebServer = MockWebServer()
    private val httpUrl = mockWebServer.url(baseUrl)

    private lateinit var repository: GithubUserRepository

    @Before
    fun setup() {
        val client = OkHttpClientProvider.provideOkHttpClient()
        val retrofit = RetrofitProvider.provideRetrofit(client, httpUrl.toString())
        val api = ApiProvider.provideApiForGithub(retrofit)
        repository = GithubUserDataRepository(api)
        mockWebServer.dispatcher = dispatcher
    }

    @Test
    fun testGithubUserRepository_success() {
        val response = repository.getUser("shnoble").execute()
        val recordedRequest = mockWebServer.takeRequest()
        assertEquals(response.body()!!.login, "shnoble")
        assertEquals(response.body()!!.id, 12043398)
    }

    @Test
    fun testGithubUserRepository_failure() {
        val response = repository.getUser("notUser").execute()
        val recordedRequest = mockWebServer.takeRequest()
        assertFalse(response.isSuccessful)
        assertEquals(response.code(), 404)
    }



    private val dispatcher = object : Dispatcher() {
        override fun dispatch(request: RecordedRequest?): MockResponse {
            return when (request?.path) {
                "/users/shnoble" -> MockResponse().setBody(SUCCESS)
                else -> MockResponse().setResponseCode(404)
            }
        }
    }

    companion object {
        val SUCCESS = """
            {
              "login": "shnoble",
              "id": 12043398,
              "node_id": "MDQ6VXNlcjEyMDQzMzk4",
              "avatar_url": "https://avatars1.githubusercontent.com/u/12043398?v=4",
              "gravatar_id": "",
              "url": "https://api.github.com/users/shnoble",
              "html_url": "https://github.com/shnoble",
              "followers_url": "https://api.github.com/users/shnoble/followers",
              "following_url": "https://api.github.com/users/shnoble/following{/other_user}",
              "gists_url": "https://api.github.com/users/shnoble/gists{/gist_id}",
              "starred_url": "https://api.github.com/users/shnoble/starred{/owner}{/repo}",
              "subscriptions_url": "https://api.github.com/users/shnoble/subscriptions",
              "organizations_url": "https://api.github.com/users/shnoble/orgs",
              "repos_url": "https://api.github.com/users/shnoble/repos",
              "events_url": "https://api.github.com/users/shnoble/events{/privacy}",
              "received_events_url": "https://api.github.com/users/shnoble/received_events",
              "type": "User",
              "site_admin": false,
              "name": null,
              "company": null,
              "blog": "",
              "location": null,
              "email": null,
              "hireable": null,
              "bio": null,
              "public_repos": 29,
              "public_gists": 0,
              "followers": 0,
              "following": 0,
              "created_at": "2015-04-21T03:03:33Z",
              "updated_at": "2019-02-11T01:44:47Z"
            }
            """.trimIndent()

        val NOT_FOUND = """
            {
              "message": "Not Found",
              "documentation_url": "https://developer.github.com/v3/users/#get-a-single-user"
            }
            """.trimIndent()
    }
}