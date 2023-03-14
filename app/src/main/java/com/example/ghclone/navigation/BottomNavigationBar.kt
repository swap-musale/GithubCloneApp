package com.example.ghclone.navigation

import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.ghclone.R

@Composable
fun BottomNavigationBar(navController: NavHostController) {
    BottomNavigation(backgroundColor = colorResource(id = R.color.white)) {
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentRoute = navBackStackEntry?.destination?.route

        BottomNavItems.forEach { navItem ->
            BottomNavigationItem(
                selectedContentColor = colorResource(id = R.color.blue),
                unselectedContentColor = colorResource(id = R.color.divider_grey),
                selected = currentRoute == navItem.route,
                label = { Text(text = stringResource(id = navItem.label)) },
                onClick = { navController.navigate(route = navItem.route) },
                icon = {
                    Icon(
                        painter = painterResource(id = navItem.icon),
                        contentDescription = stringResource(id = navItem.label),
                        tint = if (currentRoute == navItem.route) {
                            colorResource(id = R.color.blue)
                        } else {
                            colorResource(id = R.color.divider_grey)
                        },
                    )
                },
            )
        }
    }
}
