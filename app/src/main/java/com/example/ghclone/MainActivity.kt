package com.example.ghclone

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContract
import androidx.browser.customtabs.CustomTabsIntent
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import com.example.data.Constants
import com.example.ghclone.ui.screens.AuthScreen
import com.example.ghclone.ui.screens.ProfileScreen
import com.example.ghclone.ui.theme.GHCloneTheme
import com.example.ghclone.viewmodels.MainViewModel
import org.koin.android.ext.android.get


class MainActivity : ComponentActivity() {
    private val mainViewModel = get<MainViewModel>()
    private val mCustomTabLauncher =
        registerForActivityResult(
            object : ActivityResultContract<String?, Int?>() {
                override fun parseResult(resultCode: Int, intent: Intent?): Int {
                    return resultCode
                }

                override fun createIntent(context: Context, input: String?): Intent {
                    val builder = CustomTabsIntent.Builder()
                        .setInitialActivityHeightPx(
                            resources.displayMetrics.heightPixels.times(0.75f).toInt()
                        )
                        .setCloseButtonPosition(CustomTabsIntent.CLOSE_BUTTON_POSITION_END)
                        .setToolbarCornerRadiusDp(10)
                    val customTabsIntent = builder.build().intent
                    with(customTabsIntent) {
                        data = Uri.parse(input)
                        setPackage("com.android.chrome")
                    }
                    return customTabsIntent
                }
            }
        ) {}

    @OptIn(ExperimentalAnimationApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            GHCloneTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val isLoggedInState = mainViewModel.isLoggedIn.collectAsState()
                    val authState by mainViewModel.authState.collectAsState()
                    val userState by mainViewModel.profileState.collectAsState()

                    AnimatedContent(targetState = isLoggedInState.value) { isLoggedIn ->
                        if (isLoggedIn == true) {
                            ProfileScreen(
                                userState = userState,
                                fetchUser = mainViewModel::getUser
                            )
                        } else {
                            AuthScreen(authState = authState) {
                                mCustomTabLauncher.launch(Constants.AuthorizeUrl)
                            }
                        }
                    }
                }
            }
        }
    }

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
        intent?.data?.getQueryParameter("code")?.let { code ->
            mainViewModel.authenticate(code = code)
        }
    }
}
