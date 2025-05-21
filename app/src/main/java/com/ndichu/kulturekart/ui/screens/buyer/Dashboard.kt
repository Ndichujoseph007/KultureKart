package com.ndichu.kulturekart.ui.screens.buyer

import android.annotation.SuppressLint
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import coil.compose.AsyncImage
import coil.compose.rememberAsyncImagePainter // Make sure this is imported
import com.ndichu.kulturekart.data.CartViewModel
import com.ndichu.kulturekart.data.ProductViewModel
import com.ndichu.kulturekart.model.Product // Ensure Product model is imported
import com.ndichu.kulturekart.navigation.ROUTE_BUYER_PRODUCT_DETAIL
import com.ndichu.kulturekart.navigation.ROUTE_CART
import com.ndichu.kulturekart.navigation.ROUTE_PROFILE
import com.ndichu.kulturekart.navigation.ROUTE_DASHBOARD // Explicitly import Dashboard route

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("StateFlowValueCalledInComposition")
@Composable
fun DashboardScreen(
    navController: NavController,

    productViewModel: ProductViewModel = androidx.lifecycle.viewmodel.compose.viewModel(), // Renamed for clarity
   cartViewModel: CartViewModel = androidx.lifecycle.viewmodel.compose.viewModel()
) {
    var searchQuery by remember { mutableStateOf("") }

    val products by  productViewModel.products.collectAsState()
    val isLoading by  productViewModel.isLoading.collectAsState()
    val error by  productViewModel.uploadError.collectAsState()

    // Assume categories are fetched or hardcoded for now
    val categories = remember {
        mutableStateListOf(
            "All", "West Africa", "East Africa", "Central Africa", "North Africa", "South Africa", "Others"
        )
    }
    var selectedCategory by remember { mutableStateOf(categories.first()) }
    val context = LocalContext.current

    LaunchedEffect(Unit) {
        productViewModel.fetchProducts() // Assuming this fetches ALL products for the buyer
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "KultureKart Market Place",
                        style = MaterialTheme.typography.titleLarge,
                        color = MaterialTheme.colorScheme.onPrimary
                    )
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary
                ),
                actions = {
                    // Search Icon (can open a search dialog or just be a visual cue for the field below)
                    IconButton(onClick = { /* Could expand search bar or navigate to dedicated search screen */ }) {
                        Icon(
                            Icons.Default.Search,
                            contentDescription = "Search",
                            tint = MaterialTheme.colorScheme.onPrimary
                        )
                    }
                    // Shopping Cart Icon with Badge
                    BadgedBox(
                        badge = {
                            // Replace with actual cart item count from your ViewModel
                            val cartItemCount = 3 // Example: viewModel.cartItemCount.collectAsState().value
                            if (cartItemCount > 0) {
                                Badge(
                                    containerColor = MaterialTheme.colorScheme.error,
                                    contentColor = MaterialTheme.colorScheme.onError
                                ) {
                                    Text(cartItemCount.toString())
                                }
                            }
                        }
                    ) {
                        IconButton(onClick = { navController.navigate(ROUTE_CART) }) {
                            Icon(
                                Icons.Default.ShoppingCart,
                                contentDescription = "Shopping Cart",
                                tint = MaterialTheme.colorScheme.onPrimary
                            )
                        }
                    }
                },
                scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior()
            )
        },
        bottomBar = {
            NavigationBar(containerColor = MaterialTheme.colorScheme.primaryContainer) { // Golden container for bottom nav
                val currentRoute = navController.currentDestination?.route

                NavigationBarItem(
                    selected = currentRoute == ROUTE_DASHBOARD, // Check against the actual route
                    onClick = { navController.navigate(ROUTE_DASHBOARD) {
                        launchSingleTop = true
                        restoreState = true
                    }},
                    icon = {
                        Icon(
                            Icons.Default.Home,
                            contentDescription = "Home",
                            tint = MaterialTheme.colorScheme.onPrimaryContainer // Golden text
                        )
                    },
                    label = { Text("Home", color = MaterialTheme.colorScheme.onPrimaryContainer) }
                )

                NavigationBarItem(
                    selected = currentRoute == ROUTE_CART, // Check against the actual route
                    onClick = { navController.navigate(ROUTE_CART) {
                        launchSingleTop = true
                        restoreState = true
                    }},
                    icon = {
                        Icon(
                            Icons.Default.ShoppingCart,
                            contentDescription = "Cart",
                            tint = MaterialTheme.colorScheme.onPrimaryContainer // Golden text
                        )
                    },
                    label = { Text("Cart", color = MaterialTheme.colorScheme.onPrimaryContainer) }
                )

                NavigationBarItem(
                    selected = currentRoute == ROUTE_PROFILE, // Check against the actual route
                    onClick = { navController.navigate(ROUTE_PROFILE) {
                        launchSingleTop = true
                        restoreState = true
                    }},
                    icon = {
                        Icon(
                            Icons.Default.Person,
                            contentDescription = "Profile",
                            tint = MaterialTheme.colorScheme.onPrimaryContainer // Golden text
                        )
                    },
                    label = { Text("Profile", color = MaterialTheme.colorScheme.onPrimaryContainer) }
                )
            }
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background) // Set a background color
        ) {
            // Introductory Text
            Text(
                "Explore Authentic Cultural Treasures",
                style = MaterialTheme.typography.titleMedium.copy(fontSize = 18.sp), // Larger, more prominent
                color = MaterialTheme.colorScheme.onBackground,
                modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
            )

            // Search Bar
            OutlinedTextField(
                value = searchQuery,
                onValueChange = { searchQuery = it },
                label = { Text("Search products by name, description, or region") },
                leadingIcon = { Icon(Icons.Default.Search, contentDescription = "Search") },
                modifier = Modifier
                    .fillMaxWidth(),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = MaterialTheme.colorScheme.primary,
                    unfocusedBorderColor = MaterialTheme.colorScheme.outline,
                    cursorColor = MaterialTheme.colorScheme.primary,
                    focusedContainerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.1f),
                    unfocusedContainerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.1f)
                )
            )

            // Category Filter Chips
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .horizontalScroll(rememberScrollState()) // Allows horizontal scrolling
                    .padding(horizontal = 16.dp, vertical = 8.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                categories.forEach { category ->
                    FilterChip(
                        selected = selectedCategory == category,
                        onClick = { selectedCategory = category },
                        label = { Text(category) },

                    )
                }
            }

            // Loading / Error States
            if (isLoading) {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    CircularProgressIndicator(color = MaterialTheme.colorScheme.primary) // Golden progress indicator
                }
                return@Column
            }

            if (error != null) {
                Text(
                    "Error loading products: ${error!!}",
                    color = MaterialTheme.colorScheme.error,
                    modifier = Modifier.padding(16.dp)
                )
                return@Column
            }

            // Filter products based on search and selected category
            val filteredProducts = products.filter { product ->
                val matchesSearch = product.name.contains(searchQuery, ignoreCase = true) ||
                        product.description.contains(searchQuery, ignoreCase = true) ||
                        product.region.contains(searchQuery, ignoreCase = true)

                val matchesCategory = if (selectedCategory == "All") true else {
                    // Ensure your Product model has a 'category' field and it matches filter options
                    product.region.equals(selectedCategory, ignoreCase = true)
                }
                matchesSearch && matchesCategory
            }

            // Products Grid or Empty State
            if (filteredProducts.isEmpty()) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .weight(1f), // Take remaining space
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Icon(
                        Icons.Default.ShoppingCart, // A generic shopping cart icon
                        contentDescription = "No products found",
                        modifier = Modifier.size(64.dp),
                        tint = MaterialTheme.colorScheme.outline.copy(alpha = 0.6f) // Muted color
                    )
                    Spacer(Modifier.height(16.dp))
                    Text(
                        "No cultural treasures found!",
                        style = MaterialTheme.typography.headlineSmall,
                        color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.7f),
                        textAlign = TextAlign.Center
                    )
                    Spacer(Modifier.height(8.dp))
                    Text(
                        "Try a different search or filter.",
                        style = MaterialTheme.typography.bodyLarge,
                        color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.6f),
                        textAlign = TextAlign.Center
                    )
                }
            } else {
                Text(
                    "Featured Products",
                    style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.SemiBold),
                    color = MaterialTheme.colorScheme.onBackground,
                    modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
                )

                LazyVerticalGrid( // Switched to LazyVerticalGrid for better product display
                    columns = GridCells.Fixed(2), // Two columns
                    verticalArrangement = Arrangement.spacedBy(12.dp),
                    horizontalArrangement = Arrangement.spacedBy(12.dp),
                    contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp),
                    modifier = Modifier.fillMaxSize()
                ) {
                    items(filteredProducts.size) { index ->
                        val product = filteredProducts[index]
                        BuyerProductCard( // Changed to a new composable for buyer cards
                            product = product,

                            onAddToCartClick = { productToAdd ->
                                cartViewModel.addToCart(productToAdd, quantity = 1, context = context)
                                navController.navigate(ROUTE_CART)
                                Toast.makeText(context, "${productToAdd.name} added to cart!", Toast.LENGTH_SHORT).show()
                            }
                        )
                    }
                }
            }
        }
    }
}

// New composable for buyer-specific product cards
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BuyerProductCard(
    product: Product,

    onAddToCartClick: (Product) -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .height(260.dp) ,// Make card clickable for details
        shape = RoundedCornerShape(12.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface) // Card background
    ) {
        Column(
            modifier = Modifier
                .padding(8.dp)
                .fillMaxSize(),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            // Product Image
            AsyncImage(
                model = product.imageUrl,
                contentDescription = product.name,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(100.dp)
                    .clip(RoundedCornerShape(8.dp)),
                contentScale = ContentScale.Crop
            )

            // Product Name
            Text(
                text = product.name,
                style = MaterialTheme.typography.titleMedium,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                color = MaterialTheme.colorScheme.onSurface
            )

            // Product Price
            Text(
                text = "$${product.price}",
                style = MaterialTheme.typography.titleSmall,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.secondary // Golden accent for price
            )

            // Stock Status Chip (styled like your previous filters)
            val isOutOfStock = (product.price) <= 0.toString()
            FilterChip(
                selected = false, // It's a status, not a filter
                onClick = { /* No action on click */ },
                label = {
                    Text(
                        if (isOutOfStock) "Out of Stock" else "In Stock",
                        style = MaterialTheme.typography.labelSmall
                    )
                },

                modifier = Modifier.align(Alignment.End) // Aligns chip to the right
            )

            // Add to Cart Button
            Button(
                onClick = { onAddToCartClick(product) },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 4.dp),
                shape = RoundedCornerShape(8.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.secondary, // Use secondary for prominent action
                    contentColor = MaterialTheme.colorScheme.onSecondary
                ),
                enabled = !isOutOfStock // Disable if out of stock
            ) {
                Icon(Icons.Default.ShoppingCart, contentDescription = "Add to Cart", modifier = Modifier.size(20.dp))
                Spacer(Modifier.width(8.dp))
                Text(if (isOutOfStock) "Out of Stock" else "Add to Cart")
            }
        }
    }
}