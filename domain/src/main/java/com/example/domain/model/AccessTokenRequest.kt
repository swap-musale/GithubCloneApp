package com.example.domain.model

data class AccessTokenRequest(
    val client_id: String,
    val client_secret: String,
    val code: String,
)