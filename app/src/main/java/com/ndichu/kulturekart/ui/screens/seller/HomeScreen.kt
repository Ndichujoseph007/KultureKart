//package com.ndichu.kulturekart.ui.screens.seller
//
//import androidx.compose.material3.FloatingActionButton
//import androidx.compose.foundation.layout.*
//import androidx.compose.foundation.lazy.LazyColumn
//import androidx.compose.material.icons.Icons
//import androidx.compose.material.icons.filled.Add
//import androidx.compose.material3.Icon
//import androidx.compose.material3.Scaffold
//import androidx.compose.material3.Text
//import androidx.compose.material3.TopAppBar
//import androidx.compose.material3.MaterialTheme
//import androidx.compose.runtime.Composable
//import androidx.compose.runtime.collectAsState
//import androidx.compose.ui.Alignment
//import androidx.compose.ui.Modifier
//import androidx.compose.ui.unit.dp
//import androidx.lifecycle.viewmodel.compose.viewModel
//import androidx.navigation.NavController
//import com.google.firebase.auth.FirebaseAuth
//import com.ndichu.kulturekart.data.ProductViewModel
//import com.ndichu.kulturekart.ui.components.BottomNavigationBar // your custom bottom nav if any
//import androidx.compose.runtime.LaunchedEffect
//import androidx.compose.ui.platform.LocalContext
//import com.ndichu.kulturekart.navigation.ROUTE_ADD_PRODUCT
//
//import androidx.compose.foundation.clickable
//import androidx.compose.foundation.shape.RoundedCornerShape
//import androidx.compose.material3.Card
//import androidx.compose.material3.CardDefaults
//import androidx.compose.material3.ExperimentalMaterial3Api
//import coil.compose.AsyncImage
//import com.ndichu.kulturekart.model.Product
//import androidx.compose.foundation.lazy.items
//
//
//@OptIn(ExperimentalMaterial3Api::class)
//@Composable
//fun SellerHomeScreen(
//    navController: NavController,
//    viewModel: ProductViewModel = viewModel()
//) {
//    val context = LocalContext.current
//    val currentUserId = FirebaseAuth.getInstance().currentUser?.uid
//    val products by viewModel.products.collectAsState(initial = emptyList())
//
//    val sellerProducts = products.filter { it.sellerId == currentUserId }
//
//    LaunchedEffect(Unit) {
//        viewModel.fetchProducts()
//    }
//
//    Scaffold(
//        topBar = {
//            TopAppBar(title = { Text("Your Products") })
//        },
//        floatingActionButton = {
//            FloatingActionButton(onClick = {
//                navController.navigate(ROUTE_ADD_PRODUCT)
//            }) {
//                Icon(Icons.Default.Add, contentDescription = "Add Product")
//            }
//        },
//        bottomBar = {
//            BottomNavigationBar(navController)
//        }
//    ) { paddingValues ->
//
//        if (sellerProducts.isEmpty()) {
//            Box(
//                modifier = Modifier
//                    .fillMaxSize()
//                    .padding(paddingValues),
//                contentAlignment = Alignment.Center
//            ) {
//                Text("No products found. Add some!", style = MaterialTheme.typography.bodyLarge)
//            }
//        } else {
//            LazyColumn(
//                modifier = Modifier
//                    .fillMaxSize()
//                    .padding(paddingValues)
//                    .padding(horizontal = 16.dp),
//                verticalArrangement = Arrangement.spacedBy(12.dp)
//            ) {
//                items(sellerProducts) { product ->
//                    ProductItemCard(product = product, onClick = {
//                        navController.navigate("productDetail/${product.id}")
//                    })
//                }
//            }
//        }
//    }
//}
//
//@Composable
//fun ProductItemCard(
//    product: Product,
//    onClick: () -> Unit
//) {
//    Card(
//        modifier = Modifier
//            .fillMaxWidth()
//            .clickable { onClick() },
//        shape = RoundedCornerShape(12.dp),
//        elevation = CardDefaults.cardElevation(defaultElevation = 6.dp),
//    ) {
//        Row(modifier = Modifier.padding(16.dp)) {
//            AsyncImage(
//                model = product.imageUrl,
//                contentDescription = product.name,
//                modifier = Modifier
//                    .size(100.dp)
//                    .weight(1f),
//                contentScale = androidx.compose.ui.layout.ContentScale.Crop
//            )
//            Spacer(modifier = Modifier.width(16.dp))
//            Column(
//                modifier = Modifier
//                    .weight(2f)
//                    .align(alignment = androidx.compose.ui.Alignment.CenterVertically)
//            ) {
//                Text(
//                    text = product.name,
//                    style = MaterialTheme.typography.titleMedium
//                )
//                Spacer(modifier = Modifier.height(4.dp))
//                Text(
//                    text = "$${product.price}",
//                    style = MaterialTheme.typography.bodyMedium,
//                    color = MaterialTheme.colorScheme.primary
//                )
//                Spacer(modifier = Modifier.height(4.dp))
//                Text(
//                    text = product.region,
//                    style = MaterialTheme.typography.bodySmall,
//                    color = MaterialTheme.colorScheme.onSurfaceVariant
//                )
//            }
//        }
//    }
//}
//
//

package com.ndichu.kulturekart.ui.screens.seller

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.google.firebase.auth.FirebaseAuth
import com.ndichu.kulturekart.data.ProductViewModel
import com.ndichu.kulturekart.model.Product
import com.ndichu.kulturekart.navigation.ROUTE_ADD_PRODUCT
import coil.compose.AsyncImage
import com.ndichu.kulturekart.navigation.ROUTE_PRODUCT_DETAIL
import com.ndichu.kulturekart.navigation.ROUTE_PROFILE
import com.ndichu.kulturekart.navigation.ROUTE_SELLER_HOME
import com.ndichu.kulturekart.ui.screens.buyer.ProductCard

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SellerHomeScreen(
    navController: NavController,
    viewModel: ProductViewModel = viewModel()
) {
    val currentUserId = FirebaseAuth.getInstance().currentUser?.uid

    // Collect products from StateFlow
    val products by viewModel.products.collectAsState()

    // Filter only products belonging to current seller
    val sellerProducts = remember(products, currentUserId) {
        products.filter { it.sellerId == currentUserId }
    }

    // Fetch products on first composition


    Scaffold(
        topBar = {
            TopAppBar(title = { Text("Your Products") })
        },
        floatingActionButton = {
            FloatingActionButton(onClick = {
                navController.navigate(ROUTE_ADD_PRODUCT)
            }) {
                Icon(Icons.Default.Add, contentDescription = "Add Product")
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
            }
        }

    ) { paddingValues ->
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues),
                contentAlignment = Alignment.Center
            ) {
            LazyColumn(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                items(products) { product ->
                    ProductItemCard(product = product) {
                        navController.navigate("$ROUTE_PRODUCT_DETAIL/${product.id}")
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
