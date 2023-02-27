package com.example.githubcloneapp.ui

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
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import com.example.githubcloneapp.R
import com.example.githubcloneapp.util.Constant

@Composable
fun AuthenticationScreen() {

    val context = LocalContext.current

    val authUrl by remember {
        mutableStateOf("https://github.com/login/oauth/authorize?client_id=${Constant.APP_CLIENT_ID}&scope=repo&redirect_uri=${Constant.CALLBACK_URL}")
    }

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Column(
            modifier = Modifier.weight(weight = 1f),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Welcome User !",
                color = colorResource(id = R.color.black)
            )
        }

        Button(
            modifier = Modifier.padding(vertical = 20.dp),
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
                text = "Sign in with Github",
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