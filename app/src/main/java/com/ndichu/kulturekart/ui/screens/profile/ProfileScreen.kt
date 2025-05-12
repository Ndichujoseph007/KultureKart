package com.ndichu.kulturekart.ui.screens.profile

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.lifecycle.viewmodel.compose.viewModel
import com.ndichu.kulturekart.data.AuthViewModel
import com.ndichu.kulturekart.navigation.ROUTE_BUYER_HOME
import com.ndichu.kulturekart.navigation.ROUTE_SELLER_HOME
import com.ndichu.kulturekart.navigation.ROUTE_SPLASH
import androidx.compose.foundation.layout.*
import androidx.compose.material3.TextButton
import com.ndichu.kulturekart.navigation.ROUTE_ABOUT


@Composable
fun ProfileScreen(navController: NavHostController) {
    val viewModel: AuthViewModel = viewModel()
    val user by viewModel.currentUser.collectAsState()

    var selectedRole by remember { mutableStateOf(user?.role ?: "buyer") }

    Column(Modifier.padding(16.dp)) {
        Text("Email: ${user?.email ?: "Unknown"}")
        Text("Current Role: ${user?.role ?: "Unknown"}")

        Spacer(Modifier.height(16.dp))
        Text("Switch Role:")

        Row(verticalAlignment = Alignment.CenterVertically) {
            RadioButton(
                selected = selectedRole == "buyer",
                onClick = { selectedRole = "buyer" }
            )
            Text("Buyer")

            Spacer(Modifier.width(16.dp))

            RadioButton(
                selected = selectedRole == "seller",
                onClick = { selectedRole = "seller" }
            )
            Text("Seller")
        }

        Spacer(Modifier.height(16.dp))

        Button(onClick = {
            viewModel.switchRole(selectedRole) { success ->
                if (success) {
                    val route = if (selectedRole == "buyer") ROUTE_BUYER_HOME else ROUTE_SELLER_HOME
                    navController.navigate(route) { popUpTo(0) }
                }
            }
        }) {
            Text("Switch Role")
        }

        Spacer(Modifier.height(16.dp))
        TextButton(onClick = {
            viewModel.logout()
            navController.navigate(ROUTE_ABOUT) { popUpTo(0) }
        }) {
            Text("About Us")
        }

        TextButton(onClick = {
            viewModel.logout()
            navController.navigate(ROUTE_SPLASH) { popUpTo(0) }
        }) {
            Text("Logout")
        }
    }
}
