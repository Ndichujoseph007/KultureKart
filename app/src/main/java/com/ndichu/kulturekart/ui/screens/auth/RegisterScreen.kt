package com.ndichu.kulturekart.ui.screens.auth


import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.ndichu.kulturekart.R
import com.ndichu.kulturekart.data.AuthViewModel
import com.ndichu.kulturekart.navigation.ROUTE_LOGIN
import com.ndichu.kulturekart.ui.components.SectionCard

@Composable
fun RegisterScreen(navController: NavHostController) {
    val viewModel: AuthViewModel = viewModel()
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var name by remember { mutableStateOf("") }
    var confpassword by remember { mutableStateOf("") }
    var role by remember { mutableStateOf("buyer") }
    var error by remember { mutableStateOf("") }
    var showPassword by remember { mutableStateOf(false) }
    var showConfPassword by remember { mutableStateOf(false) }
    var isLoading by remember { mutableStateOf(false) }

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Image(
            painter = painterResource(id = R.drawable.background), // or use rememberAsyncImagePainter for URL
            contentDescription = null,
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop // Fit, FillBounds, Crop etc.
        )
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth()
        ) {
            SectionCard(title = "Create An Account!", MaterialTheme.colorScheme.primary) {
                Spacer(modifier = Modifier.height(12.dp))

                Text(
                    "Join the golden bridge between makers and explorers.Only at KultureKart.",
                    style = MaterialTheme.typography.bodyMedium
                )

                Spacer(modifier = Modifier.height(32.dp))

                OutlinedTextField(
                    value = name,
                    onValueChange = { name = it },
                    label = { Text("Full Name") },
                    leadingIcon = {
                        Icon(
                            imageVector = Icons.Default.Person,
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.primary
                        )
                    },
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(8.dp),
                    colors = OutlinedTextFieldDefaults.colors()
                )

                Spacer(modifier = Modifier.height(10.dp))

                OutlinedTextField(
                    value = email,
                    onValueChange = { email = it },
                    label = { Text("Email") },
                    leadingIcon = {
                        Icon(
                            imageVector = Icons.Default.Email,
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.primary
                        )
                    },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(8.dp),
                    colors = OutlinedTextFieldDefaults.colors()
                )

                Spacer(modifier = Modifier.height(10.dp))

                OutlinedTextField(
                    value = password,
                    onValueChange = { password = it },
                    label = { Text("Password") },
                    leadingIcon = {
                        Icon(
                            imageVector = Icons.Default.Lock,
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.primary
                        )
                    },
                    visualTransformation =if (showPassword) VisualTransformation.None else PasswordVisualTransformation(),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(8.dp),
                    colors = OutlinedTextFieldDefaults.colors()
                )

                Spacer(modifier = Modifier.height(10.dp))

                OutlinedTextField(
                    value = confpassword,
                    onValueChange = { confpassword = it },
                    label = { Text("Confirm Password") },
                    leadingIcon = {
                        Icon(
                            imageVector = Icons.Default.Lock,
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.primary
                        )
                    },
                    visualTransformation = if (showConfPassword) VisualTransformation.None else PasswordVisualTransformation(),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(8.dp),
                    colors = OutlinedTextFieldDefaults.colors()
                )

                Spacer(modifier = Modifier.height(10.dp))

                Row(verticalAlignment = Alignment.CenterVertically) {
                    Checkbox(
                        checked = role == "buyer",
                        onCheckedChange = { role = if (it) "buyer" else "seller" }
                    )
                    Text("Want to Buy?")
                }

                Spacer(modifier = Modifier.height(10.dp))

                Button(
                    onClick = {
                        if (password != confpassword) {
                            error = "Passwords do not match"
                        } else {
                            viewModel.register(email, password, role) { success, msg ->
                                if (true) {
                                    navController.navigate(ROUTE_LOGIN)
                                } else error = msg
                            }
                        }
                    },
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(8.dp)
                ) {
                    if (isLoading) {
                        CircularProgressIndicator(
                            color = MaterialTheme.colorScheme.onPrimary,
                            strokeWidth = 2.dp,
                            modifier = Modifier.size(24.dp)
                        )
                    } else {
                        Text("Register", fontSize = 16.sp, fontWeight = FontWeight.SemiBold)
                    }
                }

                Spacer(modifier = Modifier.height(8.dp))

                if (error.isNotEmpty()) {
                    Text(error, color = Color.Red)
                    Spacer(modifier = Modifier.height(8.dp))
                }

                TextButton(onClick = { navController.navigate(ROUTE_LOGIN) }) {
                    Text(
                        text = "Already have an account? Login",
                        color = Color(0xFFFFD700) // Gold
                    )
                }
            }
        }
    }
}
