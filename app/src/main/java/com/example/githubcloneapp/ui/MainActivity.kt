package com.example.githubcloneapp.ui

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.ui.Modifier
import com.example.githubcloneapp.ui.theme.GithubCloneAppTheme
import com.example.githubcloneapp.util.Constant

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            GithubCloneAppTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    AuthenticationScreen()
                }
            }
        }
    }

    override fun onResume() {
        super.onResume()

        intent.data?.let { uriData ->
            if (uriData.toString().startsWith(Constant.CALLBACK_URL) && uriData.getBooleanQueryParameter("code", false)) {
                val authorizationCode = uriData.getQueryParameter("code")
                Log.d("TAG", "authorizationCode: $authorizationCode")
            }
        }
    }
}