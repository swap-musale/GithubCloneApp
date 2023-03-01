package com.example.data.source.remote

import com.example.domain.model.AccessTokenRequest
import com.example.domain.model.AccessTokenResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST

interface GithubService {
    @Headers("Accept: application/json")
    @POST("login/oauth/access_token")
    suspend fun getUserAccessToken(@Body request: AccessTokenRequest): Response<AccessTokenResponse>
}