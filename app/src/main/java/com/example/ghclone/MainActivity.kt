package com.example.ghclone

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContract
import androidx.browser.customtabs.CustomTabsIntent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.data.Constants
import com.example.ghclone.navigation.AppNav
import com.example.ghclone.navigation.AppNavHost
import com.example.ghclone.navigation.BottomNavigationBar
import com.example.ghclone.ui.auth.AuthViewModel
import com.example.ghclone.ui.theme.GHCloneTheme

class MainActivity : ComponentActivity() {

    private lateinit var authViewModel: AuthViewModel
    private val customTabLauncher =
        registerForActivityResult(object : ActivityResultContract<String?, Int?>() {
            override fun parseResult(resultCode: Int, intent: Intent?): Int {
                return resultCode
            }

            override fun createIntent(context: Context, input: String?): Intent {
                val builder = CustomTabsIntent.Builder()
                    .setInitialActivityHeightPx(
                        resources.displayMetrics.heightPixels.times(other = 0.75f).toInt(),
                    )
                    .setCloseButtonPosition(CustomTabsIntent.CLOSE_BUTTON_POSITION_END)
                    .setToolbarCornerRadiusDp(12)
                val customTabsIntent = builder.build().intent
                with(customTabsIntent) {
                    data = Uri.parse(input)
                    setPackage("com.android.chrome")
                }
                return customTabsIntent
            }
        }) {}

    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        installSplashScreen()
        setContent {
            GHCloneTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background,
                ) {
                    val navController = rememberNavController()
                    Scaffold(
                        bottomBar = {
                            val navBackStackEntry by navController.currentBackStackEntryAsState()
                            AnimatedVisibility(
                                visible = navBackStackEntry?.destination?.route != AppNav.AuthScreen.route &&
                                    navBackStackEntry?.destination?.route != null,
                                enter = fadeIn() + slideInVertically(),
                                exit = fadeOut() + slideOutHorizontally(),
                            ) {
                                BottomNavigationBar(navController = navController)
                            }
                        },
                    ) { paddingValues ->
                        AppNavHost(
                            modifier = Modifier.padding(paddingValues = paddingValues),
                            navController = navController,
                            initAuthFlow = {
                                authViewModel = it
                                customTabLauncher.launch(Constants.AuthorizeUrl)
                            },
                        )
                    }
                }
            }
        }
    }

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
        intent?.data?.getQueryParameter("code")?.let { code ->
            if (::authViewModel.isInitialized) {
                authViewModel.authenticate(code = code)
            }
        }
    }
}
