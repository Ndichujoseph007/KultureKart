//package com.ndichu.kulturekart.ui.components
//
//
//import androidx.compose.material.icons.Icons
//import androidx.compose.material.icons.filled.AccountCircle
//import androidx.compose.material.icons.filled.PlayArrow
//import androidx.compose.material.icons.filled.ShoppingCart
//import androidx.compose.material3.*
//import androidx.compose.runtime.Composable
//import androidx.compose.ui.graphics.Color
//import androidx.compose.ui.unit.dp
//import androidx.navigation.NavController
//import androidx.navigation.compose.currentBackStackEntryAsState
//import com.ndichu.kulturekart.navigation.ROUTE_BUYER_HOME
//import com.ndichu.kulturekart.navigation.ROUTE_PROFILE
//import com.ndichu.kulturekart.navigation.ROUTE_SELLER_HOME
//import com.ndichu.kulturekart.ui.theme.Gold
//
//
//@Composable
//fun BottomBar(navController: NavController) {
//    val items = listOf(
//        BottomNavItem("Buyer", Icons.Filled.ShoppingCart, ROUTE_BUYER_HOME),
//        BottomNavItem("Seller", Icons.Filled.PlayArrow, ROUTE_SELLER_HOME), // Updated icon
//        BottomNavItem("Profile", Icons.Filled.AccountCircle, ROUTE_PROFILE)
//    )
//
//    NavigationBar(
//        containerColor = Gold,
//        tonalElevation = 4.dp
//    ) {
//        val navBackStackEntryState = navController.currentBackStackEntryAsState()
//        val currentRoute = navBackStackEntryState.value?.destination?.route
//
//        items.forEach { item ->
//            val isSelected = currentRoute == item.route
//
//            NavigationBarItem(
//                selected = isSelected,
//                onClick = {
//                    if (currentRoute != item.route) {
//                        navController.navigate(item.route) {
//                            popUpTo(navController.graph.startDestinationId) {
//                                saveState = true
//                            }
//                            launchSingleTop = true
//                            restoreState = true
//                        }
//                    }
//                },
//                icon = {
//                    Icon(
//                        imageVector = item.icon,
//                        contentDescription = item.label,
//                        tint = if (isSelected) MaterialTheme.colorScheme.primary else Color.Black
//                    )
//                },
//                label = {
//                    Text(
//                        text = item.label,
//                        color = if (isSelected) MaterialTheme.colorScheme.primary else Color.Black
//                    )
//                }
//            )
//        }
//    }
//}
