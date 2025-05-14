//package com.ndichu.kulturekart.ui.components
package com.ndichu.kulturekart.ui.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.ndichu.kulturekart.navigation.ROUTE_DASHBOARD
import com.ndichu.kulturekart.navigation.ROUTE_PROFILE

@Composable
fun BottomNavigationBar(navController: NavController) {
    NavigationBar(
        tonalElevation = 8.dp
    ) {
        NavigationBarItem(
            icon = { Icon(Icons.Default.Home, contentDescription = "Home") },
            label = { Text("Home") },
            selected = false,
            onClick = {
                navController.navigate(ROUTE_DASHBOARD)
            }
        )
        NavigationBarItem(
            icon = { Icon(Icons.Default.Person, contentDescription = "Profile") },
            label = { Text("Profile") },
            selected = false,
            onClick = {
                navController.navigate(ROUTE_PROFILE)
            }
        )
    }
}
