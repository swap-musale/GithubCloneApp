package com.example.ghclone.navigation

import com.example.ghclone.R

data class BottomNavItem(
    val label: Int,
    val icon: Int,
    val route: String,
)

val BottomNavItems = listOf(
    BottomNavItem(
        label = R.string.tab_home,
        icon = R.drawable.ic_tab_home,
        route = AppNav.HomeScreen.route,
    ),
    BottomNavItem(
        label = R.string.tab_notifications,
        icon = R.drawable.ic_tab_notification,
        route = AppNav.NotificationScreen.route,
    ),
    BottomNavItem(
        label = R.string.tab_explore,
        icon = R.drawable.ic_tab_explore,
        route = AppNav.ExploreScreen.route,
    ),
    BottomNavItem(
        label = R.string.tab_profile,
        icon = R.drawable.ic_tab_person,
        route = AppNav.ProfileScreen.route,
    ),
)
