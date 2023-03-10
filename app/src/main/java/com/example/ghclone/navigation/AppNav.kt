package com.example.ghclone.navigation

sealed class AppNav(val route: String) {
    object AuthScreen : AppNav(route = "auth")
    object HomeScreen : AppNav(route = "home")
    object NotificationScreen : AppNav(route = "notification")
    object ExploreScreen : AppNav(route = "explore")
    object ProfileScreen : AppNav(route = "profile")
}
