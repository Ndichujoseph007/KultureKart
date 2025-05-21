//
//package com.ndichu.kulturekart.ui.screens.auth
//
//import androidx.compose.animation.AnimatedVisibility
//import androidx.compose.animation.fadeIn
//import androidx.compose.animation.fadeOut
//import androidx.compose.animation.slideInVertically
//import androidx.compose.animation.slideOutVertically
//import androidx.compose.foundation.Image
//import androidx.compose.foundation.layout.*
//import androidx.compose.foundation.shape.RoundedCornerShape
//import androidx.compose.foundation.text.KeyboardOptions
//import androidx.compose.material.icons.Icons
//import androidx.compose.material.icons.filled.Email
//import androidx.compose.material.icons.filled.Lock
//import androidx.compose.material3.*
//import androidx.compose.runtime.*
//import androidx.compose.ui.Alignment
//import androidx.compose.ui.Modifier
//import androidx.compose.ui.graphics.Color
//import androidx.compose.ui.layout.ContentScale
//import androidx.compose.ui.res.painterResource
//import androidx.compose.ui.text.font.FontWeight
//import androidx.compose.ui.text.input.KeyboardType
//import androidx.compose.ui.text.input.PasswordVisualTransformation
//import androidx.compose.ui.unit.dp
//import androidx.compose.ui.unit.sp
//import androidx.lifecycle.viewmodel.compose.viewModel
//import androidx.navigation.NavHostController
//import com.ndichu.kulturekart.R
//import com.ndichu.kulturekart.data.AuthViewModel
//import com.ndichu.kulturekart.navigation.ROUTE_DASHBOARD
//import com.ndichu.kulturekart.navigation.ROUTE_REGISTER
//import com.ndichu.kulturekart.navigation.ROUTE_SELLER_HOME
//import com.ndichu.kulturekart.ui.components.SectionCard
//
//@Composable
//fun LoginScreen(navController: NavHostController) {
//    val viewModel: AuthViewModel = viewModel()
//    var email by remember { mutableStateOf("") }
//    var password by remember { mutableStateOf("") }
//    var error by remember { mutableStateOf("") }
//    var isLoading by remember { mutableStateOf(false) }
//
//    Box(
//        modifier = Modifier.fillMaxSize(),
//        contentAlignment = Alignment.Center
//    ) {
//        AnimatedVisibility(
//            visible = true,
//            enter = fadeIn() + slideInVertically(initialOffsetY = { fullHeight -> fullHeight / 2 }),
//            exit = fadeOut() + slideOutVertically(targetOffsetY = { fullHeight -> fullHeight / 2 })
//        ) {
//            Column(
//                horizontalAlignment = Alignment.CenterHorizontally,
//                modifier = Modifier
//                    .padding(24.dp)
//                    .fillMaxWidth()
//            ) {
//                Spacer(modifier = Modifier.height(8.dp))
//                SectionCard(title = "Welcome Back \uD83D\uDC4B", MaterialTheme.colorScheme.primary) {
//                    Spacer(modifier = Modifier.height(12.dp))
//
//                    Text(
//                        "Login to your KultureKart account",
//                        style = MaterialTheme.typography.bodyMedium
//                    )
//
//                    Spacer(modifier = Modifier.height(32.dp))
//                    OutlinedTextField(
//                        value = email,
//                        onValueChange = { email = it },
//                        label = { Text("Email", fontSize = 16.sp, fontWeight = FontWeight.Medium) },
//                        leadingIcon = {
//                            Icon(Icons.Default.Email, contentDescription = null, tint = MaterialTheme.colorScheme.primary)
//                        },
//                        modifier = Modifier.fillMaxWidth(),
//                        shape = RoundedCornerShape(12.dp),
//                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
//                        colors = OutlinedTextFieldDefaults.colors()
//                    )
//
//                    Spacer(modifier = Modifier.height(12.dp))
//
//                    OutlinedTextField(
//                        value = password,
//                        onValueChange = { password = it },
//                        label = { Text("Password", fontSize = 16.sp, fontWeight = FontWeight.Medium) },
//                        leadingIcon = {
//                            Icon(Icons.Default.Lock, contentDescription = null, tint = MaterialTheme.colorScheme.primary)
//                        },
//                        visualTransformation = PasswordVisualTransformation(),
//                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
//                        modifier = Modifier.fillMaxWidth(),
//                        shape = RoundedCornerShape(12.dp),
//                        colors = OutlinedTextFieldDefaults.colors()
//                    )
//
//                    Spacer(modifier = Modifier.height(20.dp))
//
//                    Button(
//                        onClick = {
//                            isLoading = true
//                            viewModel.login(email, password) { user, msg ->
//                                isLoading = false
//                                if (user != null) {
//                                    val route = if (user.role == "buyer") ROUTE_DASHBOARD else ROUTE_SELLER_HOME
//                                    navController.navigate(route)
//                                } else {
//                                    error = msg
//                                }
//                            }
//                        },
//                        modifier = Modifier
//                            .fillMaxWidth()
//                            .height(50.dp),
//                        shape = RoundedCornerShape(12.dp)
//                    ) {
//                        if (isLoading) {
//                            CircularProgressIndicator(
//                                color = MaterialTheme.colorScheme.onPrimary,
//                                strokeWidth = 2.dp,
//                                modifier = Modifier.size(24.dp)
//                            )
//                        } else {
//                            Text("Login", fontSize = 16.sp, fontWeight = FontWeight.SemiBold)
//                        }
//                    }
//
//                    if (error.isNotEmpty()) {
//                        Spacer(modifier = Modifier.height(8.dp))
//                        Text(error, color = Color.Red, fontSize = 14.sp)
//                    }
//
//                    Spacer(modifier = Modifier.height(20.dp))
//
//                    TextButton(onClick = { navController.navigate(ROUTE_REGISTER) }) {
//                        Text(
//                            text = "Don't have an account? Register",
//                            fontSize = 14.sp,
//                            fontWeight = FontWeight.Medium,
//                            color = Color(0xFFFFD700)
//                        )
//                    }
//                }
//            }
//        }
//    }
//}


package com.ndichu.kulturekart.ui.screens.auth

import android.widget.Toast
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.ndichu.kulturekart.R
import com.ndichu.kulturekart.data.AuthViewModel
import com.ndichu.kulturekart.navigation.ROUTE_DASHBOARD
import com.ndichu.kulturekart.navigation.ROUTE_REGISTER
import com.ndichu.kulturekart.navigation.ROUTE_SELLER_HOME
// Assuming SectionCard is defined globally or within a shared components package
import com.ndichu.kulturekart.ui.components.SectionCard // Ensure this import is correct
import com.ndichu.kulturekart.ui.screens.buyer.DashboardScreen

@Composable
fun LoginScreen(navController: NavHostController) {
    val viewModel: AuthViewModel = viewModel()
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var error by remember { mutableStateOf("") }
    var isLoading by remember { mutableStateOf(false) }
    val context = LocalContext.current // For Toast messages

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        // --- Enhanced Background Image with Overlay ---
        Image(
            painter = painterResource(id = R.drawable.background),
            contentDescription = null,
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        )
        // Semi-transparent overlay to improve text readability over background
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    Brush.verticalGradient(
                        colors = listOf(
                            MaterialTheme.colorScheme.primary.copy(alpha = 0.3f), // Top golden tint
                            MaterialTheme.colorScheme.background.copy(alpha = 0.7f), // Darker, opaque bottom
                            MaterialTheme.colorScheme.background.copy(alpha = 0.8f)
                        )
                    )
                )
        )
        // --- End Background Enhancement ---

        // Animated Login Card Container
        AnimatedVisibility(
            visible = true, // Always visible for login screen, animation plays on launch
            enter = fadeIn(animationSpec = tween(durationMillis = 800)) + slideInVertically(
                animationSpec = tween(durationMillis = 800),
                initialOffsetY = { fullHeight -> fullHeight / 4 } // Starts slightly from bottom
            ),
            exit = fadeOut(animationSpec = tween(durationMillis = 500)) + slideOutVertically(
                animationSpec = tween(durationMillis = 500),
                targetOffsetY = { fullHeight -> fullHeight / 4 }
            )
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .padding(horizontal = 24.dp) // Consistent horizontal padding
                    .fillMaxWidth()
                    .align(Alignment.Center) // Center the column within the box
            ) {

                // Main Login Card
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(20.dp), // More rounded corners for modern look
                    elevation = CardDefaults.cardElevation(defaultElevation = 10.dp), // Deeper shadow
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface) // Card background
                ) {





                    Column(
                        modifier = Modifier
                            .padding(24.dp) // Inner padding for card content
                            .fillMaxWidth(),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {

                        Text(
                            "Welcome Back 👋",
                            style = MaterialTheme.typography.headlineMedium.copy(
                                fontWeight = FontWeight.Bold,
                                fontSize = 30.sp // Larger title
                            ),
                            color = MaterialTheme.colorScheme.primary // Golden title
                        )

                        Spacer(modifier = Modifier.height(8.dp))

                        Text(
                            "Login to your KultureKart account",
                            style = MaterialTheme.typography.bodyLarge, // Adjusted font size
                            color = MaterialTheme.colorScheme.onSurfaceVariant // Muted text color
                        )

                        Spacer(modifier = Modifier.height(32.dp))

                        // Email TextField
                        OutlinedTextField(
                            value = email,
                            onValueChange = { email = it },
                            label = {
                                Text(
                                    "Email",
                                    fontSize = 16.sp,
                                    fontWeight = FontWeight.Medium
                                )
                            },
                            leadingIcon = {
                                Icon(
                                    Icons.Default.Email,
                                    contentDescription = "Email Icon",
                                    tint = MaterialTheme.colorScheme.primary // Golden icon
                                )
                            },
                            modifier = Modifier.fillMaxWidth(),
                            shape = RoundedCornerShape(12.dp),
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                            colors = OutlinedTextFieldDefaults.colors(
                                focusedBorderColor = MaterialTheme.colorScheme.primary,
                                unfocusedBorderColor = MaterialTheme.colorScheme.outline,
                                cursorColor = MaterialTheme.colorScheme.primary,
                                focusedLabelColor = MaterialTheme.colorScheme.primary,
                                unfocusedLabelColor = MaterialTheme.colorScheme.onSurfaceVariant,
                                focusedContainerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.1f),
                                unfocusedContainerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.05f)
                            )
                        )

                        Spacer(modifier = Modifier.height(16.dp)) // Slightly more space

                        // Password TextField
                        OutlinedTextField(
                            value = password,
                            onValueChange = { password = it },
                            label = {
                                Text(
                                    "Password",
                                    fontSize = 16.sp,
                                    fontWeight = FontWeight.Medium
                                )
                            },
                            leadingIcon = {
                                Icon(
                                    Icons.Default.Lock,
                                    contentDescription = "Password Icon",
                                    tint = MaterialTheme.colorScheme.primary // Golden icon
                                )
                            },
                            visualTransformation = PasswordVisualTransformation(),
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                            modifier = Modifier.fillMaxWidth(),
                            shape = RoundedCornerShape(12.dp),
                            colors = OutlinedTextFieldDefaults.colors(
                                focusedBorderColor = MaterialTheme.colorScheme.primary,
                                unfocusedBorderColor = MaterialTheme.colorScheme.outline,
                                cursorColor = MaterialTheme.colorScheme.primary,
                                focusedLabelColor = MaterialTheme.colorScheme.primary,
                                unfocusedLabelColor = MaterialTheme.colorScheme.onSurfaceVariant,
                                focusedContainerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.1f),
                                unfocusedContainerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.05f)
                            )
                        )

                        Spacer(modifier = Modifier.height(24.dp)) // More space before button

                        // Login Button
                        Button(
                            onClick = {
                                if (email.isBlank() || password.isBlank()) {
                                    error = "Email and Password cannot be empty."
                                    return@Button
                                }
                                isLoading = true
                                error = "" // Clear previous errors
                                viewModel.login(email, password) { user, msg ->
                                    isLoading = false
                                    if (user != null) {
                                        val route = if (user.role == "buyer") ROUTE_DASHBOARD else ROUTE_SELLER_HOME
                                        navController.navigate(route) {
                                            popUpTo(navController.graph.id) { inclusive = true } // Clear back stack
                                        }
                                        Toast.makeText(context, "Logged in as ${user.role}!", Toast.LENGTH_SHORT).show()
                                    } else {
                                        error = msg ?: "Login failed. Please try again."
                                        Toast.makeText(context, error, Toast.LENGTH_LONG).show() // Show toast for error
                                    }
                                }
                            },
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(56.dp), // Taller button
                            shape = RoundedCornerShape(12.dp), // Consistent rounded corners
                            colors = ButtonDefaults.buttonColors(
                                containerColor = MaterialTheme.colorScheme.primary, // Golden primary button
                                contentColor = MaterialTheme.colorScheme.onPrimary
                            ),
                            enabled = !isLoading // Disable button while loading
                        ) {
                            if (isLoading) {
                                CircularProgressIndicator(
                                    color = MaterialTheme.colorScheme.onPrimary,
                                    strokeWidth = 2.dp,
                                    modifier = Modifier.size(28.dp) // Slightly larger spinner
                                )
                            } else {
                                Text(
                                    "Login",
                                    fontSize = 18.sp, // Larger text
                                    fontWeight = FontWeight.SemiBold
                                )
                            }
                        }

                        // Error Message
                        AnimatedVisibility(
                            visible = error.isNotEmpty(),
                            enter = fadeIn() + slideInVertically(),
                            exit = fadeOut() + slideOutVertically()
                        ) {
                            Text(
                                error,
                                color = MaterialTheme.colorScheme.error,
                                fontSize = 14.sp,
                                modifier = Modifier.padding(top = 8.dp)
                            )
                        }


                        Spacer(modifier = Modifier.height(24.dp))

                        // Register Button
                        TextButton(onClick = { navController.navigate(ROUTE_REGISTER) }) {
                            Text(
                                text = "Don't have an account? Register",
                                fontSize = 15.sp,
                                fontWeight = FontWeight.Medium,
                                color = MaterialTheme.colorScheme.secondary
                            )
                        }
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun LoginScreenPreview() {
    val navController = rememberNavController()
   LoginScreen(navController = navController)
}