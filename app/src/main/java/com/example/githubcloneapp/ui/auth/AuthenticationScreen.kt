package com.example.githubcloneapp.ui.auth

import android.content.Context
import android.net.Uri
import android.util.Log
import androidx.browser.customtabs.CustomTabsIntent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.domain.localStorage.DataStoreManager
import com.example.domain.model.AccessTokenResponse
import com.example.domain.model.UserInfo
import com.example.githubcloneapp.R
import com.example.githubcloneapp.util.Constant
import com.example.githubcloneapp.util.UiState
import kotlinx.coroutines.flow.firstOrNull
import org.koin.androidx.compose.get

@Composable
fun AuthenticationScreen(authViewModel: AuthenticationVM) {

    val context = LocalContext.current

    val dataStoreManager: DataStoreManager = get()
    var userName by remember { mutableStateOf("") }
    val authUrl by remember {
        mutableStateOf("https://github.com/login/oauth/authorize?client_id=${Constant.APP_CLIENT_ID}&scope=repo,user&redirect_uri=${Constant.CALLBACK_URL}")
    }

    LaunchedEffect(key1 = Unit) {
        if (dataStoreManager.getUserAccessToken().firstOrNull().orEmpty().isNotEmpty()) {
            authViewModel.getUserInfo()
        } else {
            Log.d("TAG", "LaunchedEffect: No token Found")
        }
    }

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        when (val uiState =
            authViewModel.uiState.collectAsState(initial = UiState.EmptyList).value) {
            is UiState.Loading -> {
                CircularProgressIndicator()
            }
            is UiState.Success -> {
                when (uiState.data) {
                    is AccessTokenResponse -> {
                        authViewModel.saveUserAccessToken(accessToken = uiState.data.access_token)
                        authViewModel.getUserInfo()
                    }
                    is UserInfo -> {
                        userName = uiState.data.name
                    }
                }
            }
            else -> Unit
        }

        Column(
            modifier = Modifier.weight(weight = 1f),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Welcome ${userName.ifEmpty { "User" }} !",
                color = colorResource(id = R.color.black)
            )
        }

        Button(
            modifier = Modifier.padding(vertical = 30.dp),
            colors = ButtonDefaults.buttonColors(backgroundColor = colorResource(id = R.color.black)),
            onClick = {
                try {
                    initAuthenticationFlow(
                        context = context,
                        authUrl = authUrl
                    )
                } catch (e: Exception) {
                    Log.d("TAG", "AuthenticationScreen: ${e.localizedMessage}")
                }

            }) {
            Text(
                modifier = Modifier.padding(all = 8.dp),
                text = "Sign in with Github",
                fontSize = 14.sp,
                color = colorResource(id = R.color.white)
            )
        }
    }
}

private fun initAuthenticationFlow(context: Context, authUrl: String) {
    val builder = CustomTabsIntent.Builder().apply {
        setInitialActivityHeightPx(200)
        setToolbarCornerRadiusDp(10)
    }

    val customTabsIntent = builder.build()
    customTabsIntent.intent.setPackage("com.android.chrome")
    customTabsIntent.launchUrl(context, Uri.parse(authUrl))
}