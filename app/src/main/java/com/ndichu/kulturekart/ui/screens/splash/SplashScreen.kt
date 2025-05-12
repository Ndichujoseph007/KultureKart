package com.ndichu.kulturekart.ui.screens.splash

import com.ndichu.kulturekart.data.AuthViewModel
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.*
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.ndichu.kulturekart.R
import com.ndichu.kulturekart.navigation.ROUTE_BUYER_HOME
import com.ndichu.kulturekart.navigation.ROUTE_LOGIN
import com.ndichu.kulturekart.navigation.ROUTE_SELLER_HOME
import kotlinx.coroutines.delay

@Composable
fun SplashScreen(navController: NavController) {
    var visible by remember { mutableStateOf(true) }
    val viewModel: AuthViewModel = viewModel()
    val loginStateAndRole by viewModel.loginStateAndRole.collectAsStateWithLifecycle()

    LaunchedEffect(key1 = true) {
        delay(1500) // Increased delay slightly
        visible = false // Fade out the splash screen

        // Delay navigation slightly after fade out
        delay(500)

        if (loginStateAndRole.first) { // User is logged in
            if (loginStateAndRole.second == "buyer") {
                navController.navigate(ROUTE_BUYER_HOME) { popUpTo(0) { inclusive = true } }
            } else if (loginStateAndRole.second == "seller") {
                navController.navigate(ROUTE_SELLER_HOME) { popUpTo(0) { inclusive = true } }
            } else {
                // Handle potential unknown role (shouldn't happen in normal flow)
                navController.navigate(ROUTE_LOGIN) { popUpTo(0) { inclusive = true } }
            }
        } else {
            navController.navigate(ROUTE_LOGIN) { popUpTo(0) { inclusive = true } }
        }
    }
    Column(
        modifier = Modifier
            .fillMaxSize()
    ){
        Image(
            painter = painterResource(id = R.drawable.logo),
            contentDescription = "KultureKart Logo",
            modifier = Modifier
                .size(160.dp)
                .padding(16.dp)
                .fillMaxSize()
        )
    }

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        AnimatedVisibility(
            visible = visible,
            enter = fadeIn(animationSpec = tween(durationMillis = 1000)),
            exit = fadeOut(animationSpec = tween(durationMillis = 1000))
        ) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text(
                    text = "KultureKart",
                    fontSize = 32.sp,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.primary
                )
                Text(
                    text = "Connecting Cultures Through Artifacts",
                    fontSize = 16.sp,
                    color = MaterialTheme.colorScheme.onBackground
                )

                Spacer(modifier = Modifier.height(24.dp))
                DotsLoadingAnimation()
            }
        }
    }
}

@Composable
fun DotsLoadingAnimation() {
    val colors = listOf(
        androidx.compose.ui.graphics.Color(0xFFE53935), // Red
        androidx.compose.ui.graphics.Color(0xFFFFB300), // Yellow
        androidx.compose.ui.graphics.Color(0xFF43A047), // Green
        androidx.compose.ui.graphics.Color(0xFF1E88E5)  // Blue for variation
    )

    val dotCount = 4
    val delays = List(dotCount) { it * 150L }

    Row(
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        repeat(dotCount) { index ->
            val infiniteTransition = rememberInfiniteTransition(label = "")
            val alpha by infiniteTransition.animateFloat(
                initialValue = 0.3f,
                targetValue = 1f,
                animationSpec = infiniteRepeatable(
                    animation = tween(durationMillis = 600, delayMillis = delays[index].toInt(), easing = LinearEasing),
                    repeatMode = RepeatMode.Reverse
                ),
                label = ""
            )

            Box(
                modifier = Modifier
                    .size(12.dp)
                    .graphicsLayer { this.alpha = alpha }
                    .background(color = colors[index % colors.size], shape = CircleShape)
            )
        }
    }
}
