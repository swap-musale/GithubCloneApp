package com.example.ghclone.navigation

import com.example.ghclone.R

data class BottomNavItem(
    val label: String,
    val icon: Int,
    val route: String,
)

val BottomNavItems = listOf(
    BottomNavItem(
        label = "Home",
        icon = R.drawable.ic_tab_home,
        route = AppNav.HomeScreen.route,
    ),
    BottomNavItem(
        label = "Notifications",
        icon = R.drawable.ic_tab_notification,
        route = AppNav.NotificationScreen.route,
    ),
    BottomNavItem(
        label = "Explore",
        icon = R.drawable.ic_tab_explore,
        route = AppNav.ExploreScreen.route,
    ),
    BottomNavItem(
        label = "Profile",
        icon = R.drawable.ic_tab_person,
        route = AppNav.ProfileScreen.route,
    ),
)
