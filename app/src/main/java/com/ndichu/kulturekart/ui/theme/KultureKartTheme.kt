package com.ndichu.kulturekart.ui.theme


import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.navigation.compose.rememberNavController
import com.ndichu.kulturekart.navigation.AppNavHost

@Composable
fun KultureKartTheme() {
    val navController = rememberNavController()
    AppNavHost(navController = navController)
}
