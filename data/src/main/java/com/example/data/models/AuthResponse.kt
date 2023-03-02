package com.example.data.models


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class AuthResponse(
    @SerialName("access_token")
    val accessToken: String? = null,
    @SerialName("expires_in")
    val expiresIn: Int? = null,
    @SerialName("refresh_token")
    val refreshToken: String? = null,
    @SerialName("refresh_token_expires_in")
    val refreshTokenExpiresIn: Int? = null,
    @SerialName("scope")
    val scope: String? = null,
    @SerialName("token_type")
    val tokenType: String? = null
)