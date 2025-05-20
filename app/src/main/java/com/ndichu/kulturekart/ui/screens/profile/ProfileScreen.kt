package com.ndichu.kulturekart.ui.screens.profile

import androidx.compose.foundation.Image
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
import com.ndichu.kulturekart.navigation.ROUTE_SELLER_HOME
import com.ndichu.kulturekart.navigation.ROUTE_SPLASH
import androidx.compose.foundation.layout.*
import androidx.compose.material3.TextButton
import com.ndichu.kulturekart.navigation.ROUTE_ABOUT
import com.ndichu.kulturekart.navigation.ROUTE_DASHBOARD

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.ui.draw.paint
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.rememberNavController
import com.ndichu.kulturekart.R
import com.ndichu.kulturekart.ui.components.SectionCard


@Composable
fun ProfileScreen(navController: NavHostController) {
    val viewModel: AuthViewModel = viewModel()
    val user by viewModel.currentUser.collectAsState()
    var selectedRole by remember { mutableStateOf(user?.role ?: "buyer") }
    val background = painterResource(id = R.drawable.background)

    Column(
        modifier = Modifier
            .fillMaxSize()
            .paint(
                painter = background,
                contentScale = ContentScale.Crop
            )
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        // Back Button top-left aligned
        Box(modifier = Modifier.fillMaxWidth()) {
            IconButton(
                onClick = { navController.popBackStack() },
                modifier = Modifier.align(Alignment.CenterStart)
            ) {
                Icon(Icons.Filled.ArrowBack, contentDescription = "Back")
            }
        }

        Spacer(Modifier.height(24.dp))
        SectionCard(title = "Profile") {

            Spacer(Modifier.height(32.dp))

            // Email and current role info
            Text(
                text = "Email: ${user?.email ?: "Unknown"}",
                style = MaterialTheme.typography.bodyLarge
            )
            Spacer(Modifier.height(8.dp))
            Text(
                text = "Current Role: ${user?.role ?: "N/A"}",
                style = MaterialTheme.typography.bodyLarge
            )

            Spacer(Modifier.height(32.dp))

            Text(
                text = "Switch Role",
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.onPrimary
            )

            Spacer(Modifier.height(12.dp))

            Row(verticalAlignment = Alignment.CenterVertically) {
                RadioButton(
                    selected = selectedRole == "buyer",
                    onClick = { selectedRole = "buyer" },
                    colors = androidx.compose.material3.RadioButtonDefaults.colors(
                        selectedColor = MaterialTheme.colorScheme.primary
                    )
                )
                Spacer(Modifier.width(4.dp))
                Text(
                    text = "Want to buy?",
                    style = MaterialTheme.typography.bodyLarge
                )

                Spacer(Modifier.width(24.dp))

                RadioButton(
                    selected = selectedRole == "seller",
                    onClick = { selectedRole = "seller" },
                    colors = androidx.compose.material3.RadioButtonDefaults.colors(
                        selectedColor = MaterialTheme.colorScheme.primary
                    )
                )
                Spacer(Modifier.width(4.dp))
                Text(
                    text = "Want to sell?",
                    style = MaterialTheme.typography.bodyLarge
                )
            }

            Spacer(Modifier.height(32.dp))

            Button(
                onClick = {
                    viewModel.switchRole(selectedRole) { success ->
                        if (success) {
                            val route =
                                if (selectedRole == "buyer") ROUTE_DASHBOARD else ROUTE_SELLER_HOME
                            navController.navigate(route) {
                                popUpTo(0)
                            }
                        }
                    }
                },
                modifier = Modifier.fillMaxWidth(),
                shape = MaterialTheme.shapes.medium
            ) {
                Text(
                    text = "Switch Role",
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.onPrimary
                )
            }

            Spacer(Modifier.height(24.dp))

            TextButton(
                onClick = {
                    navController.navigate(ROUTE_ABOUT)
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = "About Us",
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.primary
                )
            }

            Spacer(Modifier.height(8.dp))

            TextButton(
                onClick = {
                    viewModel.logout()
                    navController.navigate(ROUTE_SPLASH) {
                        popUpTo(0)
                    }
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = "Logout",
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.error
                )
            }
        }
    }
}
@Preview(showBackground = true, showSystemUi = true)
@Composable
fun ProfileScreenPreview() {
    val navController = rememberNavController()
    ProfileScreen(navController = navController)
}
