package com.ndichu.kulturekart.ui.screens.splash

import android.R.attr.background
import com.ndichu.kulturekart.data.AuthViewModel
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.*
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.paint
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.ndichu.kulturekart.R
import com.ndichu.kulturekart.navigation.ROUTE_ADD_PRODUCT
import com.ndichu.kulturekart.navigation.ROUTE_BUYER
import com.ndichu.kulturekart.navigation.ROUTE_BUYER_HOME
import com.ndichu.kulturekart.navigation.ROUTE_DASHBOARD
import com.ndichu.kulturekart.navigation.ROUTE_LOGIN
import com.ndichu.kulturekart.navigation.ROUTE_REGISTER
import com.ndichu.kulturekart.navigation.ROUTE_SELLER_HOME
import kotlinx.coroutines.delay

@Composable
fun SplashScreen(navController: NavController) {
    var visible by remember { mutableStateOf(true) }
    val viewModel: AuthViewModel = viewModel()
    val loginStateAndRole by viewModel.loginStateAndRole.collectAsStateWithLifecycle()

    LaunchedEffect(key1 = true) {
        delay(1000) // Increased delay slightly
        visible = false // Fade out the splash screen

        // Delay navigation slightly after fade out
        delay(500)

        if (loginStateAndRole.first) { // User is logged in
            if (loginStateAndRole.second == "buyer") {
                navController.navigate(ROUTE_DASHBOARD) { popUpTo(0) { inclusive = true } }
            } else if (loginStateAndRole.second == "seller") {
                navController.navigate(ROUTE_SELLER_HOME) { popUpTo(0) { inclusive = true } }
            } else {
                // Handle potential unknown role (shouldn't happen in normal flow)
                navController.navigate(ROUTE_REGISTER) { popUpTo(0) { inclusive = true } }
            }
        } else {
            navController.navigate(ROUTE_LOGIN) { popUpTo(0) { inclusive = true } }
        }
    }
    val background = painterResource(id = R.drawable.logo1)
    Box(
        modifier = Modifier
            .paint(
                painter = background,
        contentScale = ContentScale.Crop
    )
            .fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        AnimatedVisibility(
            visible = visible,
            enter = fadeIn(animationSpec = tween(durationMillis = 1000)),
            exit = fadeOut(animationSpec = tween(durationMillis = 1000))
        ) {




            // Loading Spinner
            CircularProgressIndicator(
                color = MaterialTheme.colorScheme.primary,
                strokeWidth = 3.dp
            )

        }
    }
}

