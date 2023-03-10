package com.example.ghclone.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.ghclone.ui.auth.AuthScreen
import com.example.ghclone.ui.auth.AuthViewModel
import com.example.ghclone.ui.explore.ExploreScreen
import com.example.ghclone.ui.home.HomeScreen
import com.example.ghclone.ui.home.HomeViewModel
import com.example.ghclone.ui.notification.NotificationScreen
import com.example.ghclone.ui.profile.ProfileScreen
import org.koin.androidx.compose.getViewModel

@Composable
fun AppNavHost(
    modifier: Modifier,
    navController: NavHostController,
    initAuthFlow: (authViewModel: AuthViewModel) -> Unit,
) {
    val authViewModel: AuthViewModel = getViewModel()
    val isLoggedInState = authViewModel.isLoggedIn.collectAsStateWithLifecycle()
    if (isLoggedInState.value == null) return

    NavHost(
        modifier = modifier,
        navController = navController,
        startDestination = (if (isLoggedInState.value == true) AppNav.HomeScreen else AppNav.AuthScreen).route,
    ) {
        composable(route = AppNav.AuthScreen.route) {
            val authState by authViewModel.authState.collectAsStateWithLifecycle()

            AuthScreen(
                authState = authState,
                initAuthFlow = {
                    initAuthFlow(authViewModel)
                },
                navigateToHome = {
                    navController.navigate(route = AppNav.HomeScreen.route)
                },
            )
        }
        composable(route = AppNav.HomeScreen.route) {
            val homeViewModel: HomeViewModel = getViewModel()
            val favoriteRepositoryState by homeViewModel.favoriteRepositoryState.collectAsStateWithLifecycle()

            HomeScreen(favoriteRepositoryState = favoriteRepositoryState)
        }
        composable(route = AppNav.ExploreScreen.route) {
            ExploreScreen()
        }
        composable(route = AppNav.NotificationScreen.route) {
            NotificationScreen()
        }
        composable(route = AppNav.ProfileScreen.route) {
            ProfileScreen()
        }
    }
}
