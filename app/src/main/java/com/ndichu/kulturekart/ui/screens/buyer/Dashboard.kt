package com.ndichu.kulturekart.ui.screens.buyer


import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.ndichu.kulturekart.data.ProductViewModel
import com.ndichu.kulturekart.navigation.ROUTE_ADD_PRODUCT
import com.ndichu.kulturekart.navigation.ROUTE_BUYER_PRODUCT_DETAIL
import com.ndichu.kulturekart.navigation.ROUTE_CART
import com.ndichu.kulturekart.navigation.ROUTE_PROFILE
import com.ndichu.kulturekart.ui.components.product.ProductCard

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DashboardScreen(
    navController: NavController,
    viewModel: ProductViewModel = androidx.lifecycle.viewmodel.compose.viewModel()
) {
    var searchQuery by remember { mutableStateOf(TextFieldValue("")) }

    val products by viewModel.products.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()
    val error by viewModel.uploadError.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.fetchProducts()
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "KultureKart Market Place.",
                        style = MaterialTheme.typography.titleLarge,
                        color = MaterialTheme.colorScheme.onPrimary
                    )
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary
                ),
                scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior()
            )

        },
        bottomBar = {
            NavigationBar {
                NavigationBarItem(
                    selected = true,
                    onClick = { /* Already on Home */ },
                    icon = { Icon(Icons.Default.Home, contentDescription = "Home") },
                    label = { Text("Home") }
                )

                NavigationBarItem(
                    selected = false,
                    onClick = { navController.navigate(ROUTE_CART) },
                    icon = { Icon(Icons.Default.ShoppingCart, contentDescription = "Profile") },
                    label = { Text("Cart") }
                )
                NavigationBarItem(
                    selected = false,
                    onClick = { navController.navigate(ROUTE_PROFILE) },
                    icon = { Icon(Icons.Default.Person, contentDescription = "Profile") },
                    label = { Text("Profile") }
                )

            }
        }
    ) { paddingValues ->

        Column(
            modifier = Modifier
                .padding(paddingValues)
                .padding(16.dp)
                .fillMaxSize()
        ) {
            OutlinedTextField(
                value = searchQuery,
                onValueChange = { searchQuery = it },
                label = { Text("Search products") },
                leadingIcon = { Icon(Icons.Default.Search, contentDescription = "Search") },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 12.dp)
            )

            if (isLoading) {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    CircularProgressIndicator()
                }
                return@Column
            }

            if (error != null) {
                Text("Error: $error", color = MaterialTheme.colorScheme.error)
            }

            val filteredProducts = products.filter {
                it.name.contains(searchQuery.text, ignoreCase = true) ||
                        it.description.contains(searchQuery.text, ignoreCase = true) ||
                        it.region.contains(searchQuery.text, ignoreCase = true)
            }

            if (filteredProducts.isEmpty()) {
                Text("No products found", style = MaterialTheme.typography.bodyMedium)
            } else {
                LazyColumn(
                    verticalArrangement = Arrangement.spacedBy(12.dp),
                    modifier = Modifier.fillMaxSize()
                ) {
                    items(filteredProducts) { product ->
                        ProductCard(
                            navController = navController,
                            product = product,
                            onClick = {
                                viewModel.addToCart(product)
                                navController.navigate(ROUTE_CART)
                            }
                        )
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun DashboardScreenPreview() {
    val navController = rememberNavController()
    DashboardScreen(navController = navController)
}


