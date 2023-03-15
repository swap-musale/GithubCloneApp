package com.example.data

import androidx.datastore.preferences.core.stringPreferencesKey

object Constants {
    val AccessTokenKey = stringPreferencesKey("access_token")
    val RefreshTokenKey = stringPreferencesKey("refresh_token")
    const val BaseUrl = "https://api.github.com/graphql"
    const val AuthorizeUrl =
        "https://github.com/login/oauth/authorize?client_id=${BuildConfig.ghClientID}" +
            "&scope=repo%20user:email%20read:user&redirect_uri=${BuildConfig.ghCallbackUrl}"
    const val AccessTokenUrl = "https://github.com/login/oauth/access_token"

    const val CLIENT_ID_PARAM = "client_id"
    const val CLIENT_SECRET_PARAM = "client_secret"
    const val CODE_PARAM = "code"
    const val REDIRECT_URI_PARAM = "redirect_uri"
}
