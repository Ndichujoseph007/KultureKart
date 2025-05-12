package com.ndichu.kulturekart


import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.navigation.compose.rememberNavController
import com.google.firebase.FirebaseApp
import com.ndichu.kulturekart.navigation.AppNavHost
import com.ndichu.kulturekart.ui.theme.KultureKartThemeWrapper
import dagger.hilt.android.AndroidEntryPoint


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        FirebaseApp.initializeApp(this)
        setContent {
            KultureKartThemeWrapper {
                val navController = rememberNavController()
                AppNavHost(navController = navController)
            }
        }
    }
}





