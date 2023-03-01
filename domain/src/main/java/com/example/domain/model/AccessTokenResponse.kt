package com.example.domain.model

data class AccessTokenResponse(
    val access_token: String,
    val token_type: String,
    val scope: String
)