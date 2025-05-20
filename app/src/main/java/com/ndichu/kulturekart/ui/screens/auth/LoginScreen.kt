//package com.ndichu.kulturekart.ui.screens.auth
//
//import androidx.compose.foundation.Image
//import androidx.compose.foundation.layout.Box
//import androidx.compose.foundation.layout.Column
//import androidx.compose.foundation.layout.Spacer
//import androidx.compose.foundation.layout.fillMaxSize
//import androidx.compose.foundation.layout.fillMaxWidth
//import androidx.compose.foundation.layout.height
//import androidx.compose.foundation.layout.padding
//import androidx.compose.foundation.shape.RoundedCornerShape
//import androidx.compose.foundation.text.KeyboardOptions
//import androidx.compose.material.icons.Icons
//import androidx.compose.material.icons.filled.Email
//import androidx.compose.material.icons.filled.Lock
//import androidx.compose.material3.Button
//import androidx.compose.material3.CircularProgressIndicator
//import androidx.compose.material3.Icon
//import androidx.compose.material3.MaterialTheme
//import androidx.compose.material3.OutlinedTextField
//import androidx.compose.material3.OutlinedTextFieldDefaults
//import androidx.compose.material3.Text
//import androidx.compose.material3.TextButton
//import androidx.compose.runtime.Composable
//import androidx.compose.runtime.getValue
//import androidx.compose.runtime.mutableStateOf
//import androidx.compose.runtime.remember
//import androidx.compose.runtime.setValue
//import androidx.compose.ui.Alignment
//import androidx.compose.ui.Modifier
//import androidx.compose.ui.graphics.Color
//import androidx.compose.ui.layout.ContentScale
//import androidx.compose.ui.res.painterResource
//import androidx.compose.ui.text.input.KeyboardType
//import androidx.compose.ui.text.input.PasswordVisualTransformation
//import androidx.compose.ui.unit.dp
//import androidx.lifecycle.viewmodel.compose.viewModel
//import androidx.navigation.NavHostController
//import com.ndichu.kulturekart.R
//import com.ndichu.kulturekart.data.AuthViewModel
//import com.ndichu.kulturekart.navigation.ROUTE_DASHBOARD
//import com.ndichu.kulturekart.navigation.ROUTE_REGISTER
//import com.ndichu.kulturekart.navigation.ROUTE_SELLER_HOME
//import com.ndichu.kulturekart.ui.components.SectionCard
//
//
//@Composable
//fun LoginScreen(navController: NavHostController) {
//    val viewModel: AuthViewModel = viewModel()
//    var email by remember { mutableStateOf("") }
//    var password by remember { mutableStateOf("") }
//    var error by remember { mutableStateOf("") }
//    var isLoading by remember { mutableStateOf(false) }
//
//
//    Box(
//        modifier = Modifier.fillMaxSize(),
//        contentAlignment = Alignment.Center
//    ) {
//        Image(
//            painter = painterResource(id = R.drawable.background), // or use rememberAsyncImagePainter for URL
//            contentDescription = null,
//            modifier = Modifier.fillMaxSize(),
//            contentScale = ContentScale.Crop // Fit, FillBounds, Crop etc.
//        )
//        Column(
//            horizontalAlignment = Alignment.CenterHorizontally,
//            modifier = Modifier
//                .padding(16.dp)
//                .fillMaxWidth()
//        ) {
//            SectionCard(title = "Welcome Back!", MaterialTheme.colorScheme.primary) {
//
//            OutlinedTextField(
//                value = email,
//                onValueChange = { email = it },
//                label = { Text("Email") },
//                leadingIcon = {
//                    Icon(
//                        imageVector = Icons.Default.Email,
//                        contentDescription = null,
//                        tint = MaterialTheme.colorScheme.primary
//                    )
//                },
//                modifier = Modifier.fillMaxWidth(),
//                shape = RoundedCornerShape(8.dp),
//                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
//                colors = OutlinedTextFieldDefaults.colors()
//            )
//
//            Spacer(modifier = Modifier.height(10.dp))
//
//            OutlinedTextField(
//                value = password,
//                onValueChange = { password = it },
//                label = { Text("Password") },
//                leadingIcon = {
//                    Icon(
//                        imageVector = Icons.Default.Lock,
//                        contentDescription = null,
//                        tint = MaterialTheme.colorScheme.primary
//                    )
//                },
//                visualTransformation = PasswordVisualTransformation(),
//                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
//                modifier = Modifier.fillMaxWidth(),
//                shape = RoundedCornerShape(8.dp),
//                colors = OutlinedTextFieldDefaults.colors()
//            )
//
//            Spacer(modifier = Modifier.height(16.dp))
//
//            Button(
//                onClick = {
//                    isLoading = true
//                    viewModel.login(email, password) { user, msg ->
//                        isLoading = false
//                        if (user != null) {
//                            val route = if (user.role == "buyer") ROUTE_DASHBOARD else ROUTE_SELLER_HOME
//                            navController.navigate(route)
//                        } else {
//                            error = msg
//                        }
//                    }
//                },
//                modifier = Modifier.fillMaxWidth(),
//                shape = RoundedCornerShape(8.dp)
//            ) {
//                Text("Login")
//            }
//
//
//            if (error.isNotEmpty()) {
//                Spacer(modifier = Modifier.height(8.dp))
//                Text(error, color = Color.Red)
//            }
//
//            Spacer(modifier = Modifier.height(16.dp))
//
//            TextButton(onClick = { navController.navigate(ROUTE_REGISTER) }) {
//                Text(
//                    text = "Don't have an account? Register",
//                    color = Color(0xFFFFD700)
//                )
//            }
//        }
//    }
//}
//}

package com.ndichu.kulturekart.ui.screens.auth

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.ndichu.kulturekart.R
import com.ndichu.kulturekart.data.AuthViewModel
import com.ndichu.kulturekart.navigation.ROUTE_DASHBOARD
import com.ndichu.kulturekart.navigation.ROUTE_REGISTER
import com.ndichu.kulturekart.navigation.ROUTE_SELLER_HOME
import com.ndichu.kulturekart.ui.components.SectionCard

@Composable
fun LoginScreen(navController: NavHostController) {
    val viewModel: AuthViewModel = viewModel()
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var error by remember { mutableStateOf("") }
    var isLoading by remember { mutableStateOf(false) }

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Image(
            painter = painterResource(id = R.drawable.background),
            contentDescription = null,
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        )

        AnimatedVisibility(
            visible = true,
            enter = fadeIn() + slideInVertically(initialOffsetY = { fullHeight -> fullHeight / 2 }),
            exit = fadeOut() + slideOutVertically(targetOffsetY = { fullHeight -> fullHeight / 2 })
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .padding(24.dp)
                    .fillMaxWidth()
            ) {
                Spacer(modifier = Modifier.height(8.dp))
                SectionCard(title = "Welcome Back \uD83D\uDC4B", MaterialTheme.colorScheme.primary) {
                    Spacer(modifier = Modifier.height(12.dp))

                    Text(
                        "Login to your KultureKart account",
                        style = MaterialTheme.typography.bodyMedium
                    )

                    Spacer(modifier = Modifier.height(32.dp))
                    OutlinedTextField(
                        value = email,
                        onValueChange = { email = it },
                        label = { Text("Email", fontSize = 16.sp, fontWeight = FontWeight.Medium) },
                        leadingIcon = {
                            Icon(Icons.Default.Email, contentDescription = null, tint = MaterialTheme.colorScheme.primary)
                        },
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(12.dp),
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                        colors = OutlinedTextFieldDefaults.colors()
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    OutlinedTextField(
                        value = password,
                        onValueChange = { password = it },
                        label = { Text("Password", fontSize = 16.sp, fontWeight = FontWeight.Medium) },
                        leadingIcon = {
                            Icon(Icons.Default.Lock, contentDescription = null, tint = MaterialTheme.colorScheme.primary)
                        },
                        visualTransformation = PasswordVisualTransformation(),
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(12.dp),
                        colors = OutlinedTextFieldDefaults.colors()
                    )

                    Spacer(modifier = Modifier.height(20.dp))

                    Button(
                        onClick = {
                            isLoading = true
                            viewModel.login(email, password) { user, msg ->
                                isLoading = false
                                if (user != null) {
                                    val route = if (user.role == "buyer") ROUTE_DASHBOARD else ROUTE_SELLER_HOME
                                    navController.navigate(route)
                                } else {
                                    error = msg
                                }
                            }
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(50.dp),
                        shape = RoundedCornerShape(12.dp)
                    ) {
                        if (isLoading) {
                            CircularProgressIndicator(
                                color = MaterialTheme.colorScheme.onPrimary,
                                strokeWidth = 2.dp,
                                modifier = Modifier.size(24.dp)
                            )
                        } else {
                            Text("Login", fontSize = 16.sp, fontWeight = FontWeight.SemiBold)
                        }
                    }

                    if (error.isNotEmpty()) {
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(error, color = Color.Red, fontSize = 14.sp)
                    }

                    Spacer(modifier = Modifier.height(20.dp))

                    TextButton(onClick = { navController.navigate(ROUTE_REGISTER) }) {
                        Text(
                            text = "Don't have an account? Register",
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Medium,
                            color = Color(0xFFFFD700)
                        )
                    }
                }
            }
        }
    }
}
