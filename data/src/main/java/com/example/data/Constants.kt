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
}
