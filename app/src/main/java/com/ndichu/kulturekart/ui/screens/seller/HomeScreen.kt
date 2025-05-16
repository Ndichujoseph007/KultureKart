package com.ndichu.kulturekart.ui.screens.seller

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.paint
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.google.firebase.auth.FirebaseAuth
import com.ndichu.kulturekart.data.ProductViewModel
import com.ndichu.kulturekart.model.Product
import com.ndichu.kulturekart.navigation.ROUTE_ADD_PRODUCT
import coil.compose.AsyncImage
import com.ndichu.kulturekart.R
import com.ndichu.kulturekart.navigation.ROUTE_PRODUCT_DETAIL
import com.ndichu.kulturekart.navigation.ROUTE_PROFILE
import com.ndichu.kulturekart.navigation.ROUTE_SELLER_HOME
import com.ndichu.kulturekart.navigation.ROUTE_SELLER_PRODUCT_DETAIL
import kotlinx.coroutines.flow.MutableStateFlow

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SellerHomeScreen(
    navController: NavController,
    viewModel: ProductViewModel = viewModel()
) {

    val userRole = MutableStateFlow<String>("buyer") // default
    val role by userRole.collectAsState()

    val currentUserId = FirebaseAuth.getInstance().currentUser?.uid

    // Collect products from StateFlow
    val products by viewModel.products.collectAsState()


    // Filter only products belonging to current seller
    val sellerProducts = remember(products, currentUserId) {
        products.filter { it.sellerId == currentUserId }
    }
    val background = painterResource(id = R.drawable.background)



    // Fetch products on first composition


    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Your Products",
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
        floatingActionButton = {
            if (role == "seller") {
                FloatingActionButton(onClick = { navController.navigate(ROUTE_ADD_PRODUCT) })  {
                    Icon(Icons.Default.Add, contentDescription = "Add Product")
                }
            }


        },
        bottomBar = {
            NavigationBar(containerColor = MaterialTheme.colorScheme.primary) {
                NavigationBarItem(
                    selected = false,
                    onClick = { navController.navigate(ROUTE_SELLER_HOME) },
                    icon = {
                        Icon(
                            imageVector = Icons.Filled.Home,
                            contentDescription = "Home",
                            tint = MaterialTheme.colorScheme.onPrimary
                        )
                    },
                    label = { Text("Home", color = MaterialTheme.colorScheme.onPrimary) }
                )

                NavigationBarItem(
                    selected = false,
                    onClick = { navController.navigate(ROUTE_PROFILE) },
                    icon = {
                        Icon(
                            imageVector = Icons.Filled.Person,
                            contentDescription = "Profile",
                            tint = MaterialTheme.colorScheme.onPrimary
                        )
                    },
                    label = { Text("Profile", color = MaterialTheme.colorScheme.onPrimary) }
                )
                NavigationBarItem(
                    selected = false,
                    onClick = { navController.navigate(ROUTE_ADD_PRODUCT) },
                    icon = {
                        Icon(
                            imageVector = Icons.Filled.Add,
                            contentDescription = "Add Product",
                            tint = MaterialTheme.colorScheme.onPrimary
                        )
                    },
                    label = { Text("Add Product", color = MaterialTheme.colorScheme.onPrimary) }
                )
            }
        }

    ) { paddingValues ->
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .paint(
                        painter = background,
                        contentScale = ContentScale.Crop
                    )
                    .padding(paddingValues),
                contentAlignment = Alignment.Center
            ) {
            LazyColumn(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                items(sellerProducts) { product ->
                    ProductItemCard(product = product) {
                        navController.navigate("$ROUTE_SELLER_PRODUCT_DETAIL/${product.id}")
                    }
                }
            }
        }


    }
}

@Composable
fun ProductItemCard(
    product: Product,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() },
        shape = MaterialTheme.shapes.medium,
        elevation = CardDefaults.cardElevation(defaultElevation = 6.dp)
    ) {
        Row(modifier = Modifier.padding(16.dp)) {
            AsyncImage(
                model = product.imageUrl,
                contentDescription = product.name,
                modifier = Modifier.size(100.dp),
                contentScale = androidx.compose.ui.layout.ContentScale.Crop
            )
            Spacer(modifier = Modifier.width(16.dp))
            Column(
                modifier = Modifier.align(Alignment.CenterVertically)
            ) {
                Text(text = product.name, style = MaterialTheme.typography.titleMedium)
                Spacer(modifier = Modifier.height(4.dp))
                Text(text = "$${product.price}", color = MaterialTheme.colorScheme.primary)
                Spacer(modifier = Modifier.height(4.dp))
                Text(text = product.region, style = MaterialTheme.typography.bodySmall)
            }
        }
    }
}
