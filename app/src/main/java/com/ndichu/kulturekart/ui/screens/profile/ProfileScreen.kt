//package com.ndichu.kulturekart.ui.screens.profile
//
//import androidx.compose.foundation.Image
//import androidx.compose.foundation.layout.Column
//import androidx.compose.foundation.layout.Row
//import androidx.compose.foundation.layout.padding
//import androidx.compose.material3.Button
//import androidx.compose.material3.RadioButton
//import androidx.compose.material3.Text
//import androidx.compose.runtime.*
//import androidx.compose.ui.Alignment
//import androidx.compose.ui.Modifier
//import androidx.compose.ui.unit.dp
//import androidx.navigation.NavHostController
//import androidx.lifecycle.viewmodel.compose.viewModel
//import com.ndichu.kulturekart.data.AuthViewModel
//import com.ndichu.kulturekart.navigation.ROUTE_SELLER_HOME
//import com.ndichu.kulturekart.navigation.ROUTE_SPLASH
//import androidx.compose.foundation.layout.*
//import androidx.compose.material3.TextButton
//import com.ndichu.kulturekart.navigation.ROUTE_ABOUT
//import com.ndichu.kulturekart.navigation.ROUTE_DASHBOARD
//
//import androidx.compose.material.icons.Icons
//import androidx.compose.material.icons.filled.ArrowBack
//import androidx.compose.material3.Icon
//import androidx.compose.material3.IconButton
//import androidx.compose.material3.MaterialTheme
//import androidx.compose.ui.draw.paint
//import androidx.compose.ui.layout.ContentScale
//import androidx.compose.ui.res.painterResource
//import androidx.compose.ui.tooling.preview.Preview
//import androidx.navigation.compose.rememberNavController
//import com.ndichu.kulturekart.R
//import com.ndichu.kulturekart.ui.components.SectionCard
//
//
//@Composable
//fun ProfileScreen(navController: NavHostController) {
//    val viewModel: AuthViewModel = viewModel()
//    val user by viewModel.currentUser.collectAsState()
//    var selectedRole by remember { mutableStateOf(user?.role ?: "buyer") }
//
//    Column(
//        modifier = Modifier
//            .fillMaxSize()
//
//            .padding(24.dp),
//        horizontalAlignment = Alignment.CenterHorizontally
//    ) {
//
//        // Back Button top-left aligned
//        Box(modifier = Modifier.fillMaxWidth()) {
//            IconButton(
//                onClick = { navController.popBackStack() },
//                modifier = Modifier.align(Alignment.CenterStart)
//            ) {
//                Icon(Icons.Filled.ArrowBack, contentDescription = "Back")
//            }
//        }
//
//        Spacer(Modifier.height(24.dp))
//        SectionCard(title = "Profile") {
//
//            Spacer(Modifier.height(32.dp))
//
//            // Email and current role info
//            Text(
//                text = "Email: ${user?.email ?: "Unknown"}",
//                style = MaterialTheme.typography.bodyLarge
//            )
//            Spacer(Modifier.height(8.dp))
//            Text(
//                text = "Current Role: ${user?.role ?: "N/A"}",
//                style = MaterialTheme.typography.bodyLarge
//            )
//
//            Spacer(Modifier.height(32.dp))
//
//            Text(
//                text = "Switch Role",
//                style = MaterialTheme.typography.titleMedium,
//                color = MaterialTheme.colorScheme.onPrimary
//            )
//
//            Spacer(Modifier.height(12.dp))
//
//            Row(verticalAlignment = Alignment.CenterVertically) {
//                RadioButton(
//                    selected = selectedRole == "buyer",
//                    onClick = { selectedRole = "buyer" },
//                    colors = androidx.compose.material3.RadioButtonDefaults.colors(
//                        selectedColor = MaterialTheme.colorScheme.primary
//                    )
//                )
//                Spacer(Modifier.width(4.dp))
//                Text(
//                    text = "Want to buy?",
//                    style = MaterialTheme.typography.bodyLarge
//                )
//
//                Spacer(Modifier.width(24.dp))
//
//                RadioButton(
//                    selected = selectedRole == "seller",
//                    onClick = { selectedRole = "seller" },
//                    colors = androidx.compose.material3.RadioButtonDefaults.colors(
//                        selectedColor = MaterialTheme.colorScheme.primary
//                    )
//                )
//                Spacer(Modifier.width(4.dp))
//                Text(
//                    text = "Want to sell?",
//                    style = MaterialTheme.typography.bodyLarge
//                )
//            }
//
//            Spacer(Modifier.height(32.dp))
//
//            Button(
//                onClick = {
//                    viewModel.switchRole(selectedRole) { success ->
//                        if (success) {
//                            val route =
//                                if (selectedRole == "buyer") ROUTE_DASHBOARD else ROUTE_SELLER_HOME
//                            navController.navigate(route) {
//                                popUpTo(0)
//                            }
//                        }
//                    }
//                },
//                modifier = Modifier.fillMaxWidth(),
//                shape = MaterialTheme.shapes.medium
//            ) {
//                Text(
//                    text = "Switch Role",
//                    style = MaterialTheme.typography.titleMedium,
//                    color = MaterialTheme.colorScheme.onPrimary
//                )
//            }
//
//            Spacer(Modifier.height(24.dp))
//
//            TextButton(
//                onClick = {
//                    navController.navigate(ROUTE_ABOUT)
//                },
//                modifier = Modifier.fillMaxWidth()
//            ) {
//                Text(
//                    text = "About Us",
//                    style = MaterialTheme.typography.bodyLarge,
//                    color = MaterialTheme.colorScheme.primary
//                )
//            }
//
//            Spacer(Modifier.height(8.dp))
//
//            TextButton(
//                onClick = {
//                    viewModel.logout()
//                    navController.navigate(ROUTE_SPLASH) {
//                        popUpTo(0)
//                    }
//                },
//                modifier = Modifier.fillMaxWidth()
//            ) {
//                Text(
//                    text = "Logout",
//                    style = MaterialTheme.typography.bodyLarge,
//                    color = MaterialTheme.colorScheme.error
//                )
//            }
//        }
//    }
//}
//@Preview(showBackground = true, showSystemUi = true)
//@Composable
//fun ProfileScreenPreview() {
//    val navController = rememberNavController()
//    ProfileScreen(navController = navController)
//}


package com.ndichu.kulturekart.ui.screens.profile

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.* // Import all Material 3 components
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.ExitToApp // For logout icon
import androidx.compose.material.icons.filled.Info // For About Us icon
import androidx.compose.material.icons.filled.Home // For bottom nav
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.ndichu.kulturekart.R // Assuming you have an R file for drawables
import com.ndichu.kulturekart.data.AuthViewModel
import com.ndichu.kulturekart.navigation.ROUTE_ABOUT
import com.ndichu.kulturekart.navigation.ROUTE_DASHBOARD
import com.ndichu.kulturekart.navigation.ROUTE_PROFILE
import com.ndichu.kulturekart.navigation.ROUTE_SELLER_HOME
import com.ndichu.kulturekart.navigation.ROUTE_SPLASH
import kotlinx.coroutines.flow.MutableStateFlow

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileScreen(navController: NavHostController) {
    val viewModel: AuthViewModel = viewModel()
    val user by viewModel.currentUser.collectAsState()
    var selectedRole by remember { mutableStateOf(user?.role ?: "buyer") }
    val context = LocalContext.current // Get context for toast or other actions

    // Effect to update selectedRole when user changes (e.g., after login)
    LaunchedEffect(user?.role) {
        user?.role?.let {
            selectedRole = it
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "My Profile",
                        style = MaterialTheme.typography.titleLarge,
                        color = MaterialTheme.colorScheme.onPrimary
                    )
                },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(
                            Icons.Filled.ArrowBack,
                            contentDescription = "Back",
                            tint = MaterialTheme.colorScheme.onPrimary
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary // Golden primary for app bar
                )
            )
        },
        bottomBar = {
            NavigationBar(containerColor = MaterialTheme.colorScheme.primaryContainer) {
                // Determine selected state based on current route
                val currentRoute = navController.currentDestination?.route

                NavigationBarItem(
                    selected = currentRoute == ROUTE_SELLER_HOME || currentRoute == ROUTE_DASHBOARD, // Home or Dashboard could be "home"
                    onClick = {
                        // Navigate based on actual role if possible, or a default home
                        val homeRoute = if (user?.role == "seller") ROUTE_SELLER_HOME else ROUTE_DASHBOARD
                        navController.navigate(homeRoute) {
                            popUpTo(navController.graph.startDestinationId) // Clears back stack up to start
                            launchSingleTop = true // Prevents multiple copies of the same destination
                        }
                    },
                    icon = {
                        Icon(
                            imageVector = Icons.Filled.Home,
                            contentDescription = "Home",
                            tint = MaterialTheme.colorScheme.onPrimaryContainer
                        )
                    },
                    label = { Text("Home", color = MaterialTheme.colorScheme.onPrimaryContainer) }
                )
                NavigationBarItem(
                    selected = currentRoute == ROUTE_PROFILE,
                    onClick = { navController.navigate(ROUTE_PROFILE) {
                        launchSingleTop = true
                        restoreState = true
                    } },
                    icon = {
                        Icon(
                            imageVector = Icons.Filled.Person,
                            contentDescription = "Profile",
                            tint = MaterialTheme.colorScheme.onPrimaryContainer
                        )
                    },
                    label = { Text("Profile", color = MaterialTheme.colorScheme.onPrimaryContainer) }
                )
            }
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues) // Apply padding from Scaffold
                .background(MaterialTheme.colorScheme.background)
                .verticalScroll(rememberScrollState()) // Use background color
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top // Align content to the top
        ) {
            // No need for a separate back button Box here if TopAppBar handles it

            Spacer(Modifier.height(24.dp)) // Space below the top bar

            // Profile Picture Placeholder
            Card(
                shape = CircleShape,
                modifier = Modifier.size(120.dp),
                elevation = CardDefaults.cardElevation(defaultElevation = 6.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant) // Muted background for image placeholder
            ) {
                // You would typically load a user's profile image here
                // For now, using a generic person icon or a drawable placeholder
                Image(
                    painter = painterResource(id = R.drawable.logo1),
                    contentDescription = "Profile Picture",
                    modifier = Modifier.fillMaxSize().clip(CircleShape),
                    contentScale = ContentScale.Crop
                )
                // OR use AsyncImage if you have a URL:
                /*
                AsyncImage(
                    model = user?.profileImageUrl, // Assuming your User model has a profileImageUrl property
                    contentDescription = "Profile Picture",
                    modifier = Modifier.fillMaxSize().clip(CircleShape),
                    contentScale = ContentScale.Crop,
                    placeholder = painterResource(id = R.drawable.ic_default_profile), // Fallback
                    error = painterResource(id = R.drawable.ic_default_profile) // Error fallback
                )
                */
            }

            Spacer(Modifier.height(24.dp))

            // User Info Section - use SectionCard for a cleaner look
            SectionCard(title = "User Information") { // Title for the card
                Column(modifier = Modifier.fillMaxWidth().padding(16.dp)) {
                    Text(
                        text = "Email: ${user?.email ?: "N/A"}",
                        style = MaterialTheme.typography.bodyLarge,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                    Spacer(Modifier.height(8.dp))
                    Text(
                        text = "Current Role: ${user?.role?.replaceFirstChar { it.uppercase() } ?: "N/A"}", // Capitalize role
                        style = MaterialTheme.typography.bodyLarge,
                        fontWeight = FontWeight.SemiBold,
                        color = MaterialTheme.colorScheme.secondary // Highlight with secondary color
                    )
                }
            }

            Spacer(Modifier.height(24.dp))

            // Role Switch Section - use SectionCard
            SectionCard(title = "Switch Role") {
                Column(modifier = Modifier.fillMaxWidth().padding(16.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceAround // Distribute radio buttons
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            RadioButton(
                                selected = selectedRole == "buyer",
                                onClick = { selectedRole = "buyer" },
                                colors = RadioButtonDefaults.colors(
                                    selectedColor = MaterialTheme.colorScheme.primary,
                                    unselectedColor = MaterialTheme.colorScheme.outline
                                )
                            )
                            Spacer(Modifier.width(4.dp))
                            Text(
                                text = "Buyer",
                                style = MaterialTheme.typography.bodyLarge,
                                color = MaterialTheme.colorScheme.onSurface
                            )
                        }

                        Row(verticalAlignment = Alignment.CenterVertically) {
                            RadioButton(
                                selected = selectedRole == "seller",
                                onClick = { selectedRole = "seller" },
                                colors = RadioButtonDefaults.colors(
                                    selectedColor = MaterialTheme.colorScheme.primary,
                                    unselectedColor = MaterialTheme.colorScheme.outline
                                )
                            )
                            Spacer(Modifier.width(4.dp))
                            Text(
                                text = "Seller",
                                style = MaterialTheme.typography.bodyLarge,
                                color = MaterialTheme.colorScheme.onSurface
                            )
                        }
                    }

                    Spacer(Modifier.height(24.dp))

                    Button(
                        onClick = {
                            viewModel.switchRole(selectedRole) { success ->
                                if (success) {
                                    val route =
                                        if (selectedRole == "buyer") ROUTE_DASHBOARD else ROUTE_SELLER_HOME
                                    navController.navigate(route) {
                                        popUpTo(navController.graph.startDestinationId) { inclusive = true } // Clear back stack
                                        launchSingleTop = true
                                    }
                                } else {

                                     Toast.makeText(context, "Failed to switch role.", Toast.LENGTH_SHORT).show()
                                }
                            }
                        },
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(8.dp), // Consistent rounded corners
                        colors = ButtonDefaults.buttonColors(
                            containerColor = MaterialTheme.colorScheme.primary // Golden primary for action button
                        )
                    ) {
                        Text(
                            text = "Switch Role",
                            style = MaterialTheme.typography.bodyLarge, // Adjust text style
                            color = MaterialTheme.colorScheme.onPrimary
                        )
                    }
                }
            }

            Spacer(Modifier.height(24.dp))

            // Additional Actions Section - using SectionCard
            SectionCard(title = "Actions") {
                Column(modifier = Modifier.fillMaxWidth().padding(16.dp)) {
                    TextButton(
                        onClick = {
                            navController.navigate(ROUTE_ABOUT)
                        },
                        modifier = Modifier.fillMaxWidth(),
                        colors = ButtonDefaults.textButtonColors(contentColor = MaterialTheme.colorScheme.primary)
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(Icons.Filled.Info, contentDescription = "About Us",
                                modifier = Modifier.size(20.dp).padding(end = 4.dp))
                            Text(
                                text = "About Us",
                                style = MaterialTheme.typography.bodyLarge
                            )
                        }
                    }

                    Spacer(Modifier.height(8.dp))

                    TextButton(
                        onClick = {
                            viewModel.logout()
                            navController.navigate(ROUTE_SPLASH) {
                                popUpTo(0)
                            }
                        },
                        modifier = Modifier.fillMaxWidth(),
                        colors = ButtonDefaults.textButtonColors(contentColor = MaterialTheme.colorScheme.error)
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(Icons.Filled.ExitToApp, contentDescription = "Logout",
                                modifier = Modifier.size(20.dp).padding(end = 4.dp))
                            Text(
                                text = "Logout",
                                style = MaterialTheme.typography.bodyLarge
                            )
                        }
                    }
                }
            }
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SectionCard(title: String, content: @Composable () -> Unit) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface), // Use surface for cards
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Text(
                text = title,
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.SemiBold,
                color = MaterialTheme.colorScheme.onSurface
            )
            Spacer(modifier = Modifier.height(12.dp))
            content()
        }
    }
}


