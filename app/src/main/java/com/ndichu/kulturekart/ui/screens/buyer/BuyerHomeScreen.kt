package com.ndichu.kulturekart.ui.screens.buyer

import android.R.attr.padding
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import com.ndichu.kulturekart.data.ProductViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.compose.rememberNavController
import com.ndichu.kulturekart.navigation.ROUTE_BUYER_HOME
import com.ndichu.kulturekart.navigation.ROUTE_PRODUCT_DETAIL
import com.ndichu.kulturekart.navigation.ROUTE_PROFILE
import com.ndichu.kulturekart.navigation.ROUTE_REGISTER
import com.ndichu.kulturekart.navigation.ROUTE_SELLER_HOME


@Composable
fun BuyerHomeScreen(
    modifier: Modifier = Modifier,
    navController: NavController,
    viewModel: ProductViewModel = hiltViewModel()
) {
//    val products by viewModel.products.collectAsStateWithLifecycle()
//    val isLoading by viewModel.isLoading.collectAsStateWithLifecycle()
//    val error by viewModel.error.collectAsStateWithLifecycle()
//
////    Scaffold(
////        bottomBar = {
////            BottomAppBar {
////                NavigationBarItem(
////                    selected = true,
////                    onClick = { navController.navigate(ROUTE_BUYER_HOME) }
////                    ,
////                    icon = { Icon(Icons.Default.Home, contentDescription = "Home") },
////                    label = { Text("Home") }
////                )
////                NavigationBarItem(
////                    selected = false,
////                    onClick = { navController.navigate(ROUTE_REGISTER) }
////                    ,
////                    icon = { Icon(Icons.Default.List, contentDescription = "Category") },
////                    label = { Text("Category") }
////                )
////                NavigationBarItem(
////                    selected = false,
////                    onClick = { navController.navigate(ROUTE_PROFILE) }
////                    ,
////                    icon = { Icon(Icons.Default.Person, contentDescription = "Profile") },
////                    label = { Text("Profile") }
////                )
////                NavigationBarItem(
////                    selected = false,
////                    onClick = { navController.navigate(ROUTE_PRODUCT_DETAIL) }
////,
////                            icon = { Icon(Icons.Default.ShoppingCart, contentDescription = "Cart") },
////                    label = { Text("Cart") }
////                )
////                NavigationBarItem(
////                    selected = false,
////                    onClick = { navController.navigate(ROUTE_SELLER_HOME) }
////,
////                            icon = { Icon(Icons.Default.Place, contentDescription = "Message") },
////                    label = { Text("Message") }
////                )
////            }
////        }
////    ) { padding ->
//        Column(
//            modifier = Modifier
////                .padding(padding)
//                .verticalScroll(rememberScrollState())
//        ) {
//            Spacer(Modifier.height(16.dp))
//            Text(
//                "KultureKart",
//                style = MaterialTheme.typography.headlineMedium,
//                modifier = Modifier.padding(start = 16.dp)
//            )
//            Text(
//                "Welcome, Buyer!",
//                style = MaterialTheme.typography.bodyMedium,
//                modifier = Modifier.padding(start = 16.dp, bottom = 16.dp)
//            )
//
//            if (isLoading) {
//                CircularProgressIndicator(modifier = Modifier.padding(16.dp).align(Alignment.CenterHorizontally))
//            } else if (error != null) {
//                Text("Error loading products: $error", color = MaterialTheme.colorScheme.error, modifier = Modifier.padding(16.dp))
//            } else {
//                // Horizontal slider
//                LazyRow(modifier = Modifier.fillMaxWidth().padding(horizontal = 8.dp)) {
//                    items(products.take(5).size) { index -> // Use the size of the list
//                        val product = products[index] // Access the product at the current index
//                        Image(
//                            painter = rememberAsyncImagePainter(product.imageUrl ?: ""),
//                            contentDescription = product.name ?: "",
//                            contentScale = ContentScale.Crop,
//                            modifier = Modifier
//                                .padding(8.dp)
//                                .size(200.dp)
//                                .clip(RoundedCornerShape(16.dp))
//                                .clickable {
//                                    navController.navigate("$ROUTE_PRODUCT_DETAIL/${product.name}")
//                                }
//                        )
//                    }
//                }
//
//                Spacer(Modifier.height(16.dp))
//                Text(
//                    "All Products",
//                    style = MaterialTheme.typography.titleLarge,
//                    modifier = Modifier.padding(start = 16.dp, bottom = 8.dp)
//                )
//
//                // Full product list
//                Column {
//                    products.forEach { product ->
//                        Card(
//                            modifier = Modifier
//                                .fillMaxWidth()
//                                .padding(horizontal = 16.dp, vertical = 8.dp)
//                                .clickable {
//                                    navController.navigate("$ROUTE_PRODUCT_DETAIL/${product.name}")
//                                },
//                            elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
//                        ) {
//                            Row(
//                                modifier = Modifier.padding(8.dp),
//                                verticalAlignment = Alignment.CenterVertically
//                            ) {
//                                Image(
//                                    painter = rememberAsyncImagePainter(product.imageUrl ?: ""),
//                                    contentDescription = product.name ?: "",
//
//                                    contentScale = ContentScale.Crop,
//                                    modifier = Modifier
//                                        .size(80.dp)
//                                        .clip(RoundedCornerShape(8.dp))
//                                )
//                                Spacer(Modifier.width(12.dp))
//                                Column {
//                                    Text(product.name, style = MaterialTheme.typography.titleMedium)
//                                    Text(product.region, style = MaterialTheme.typography.bodySmall)
//                                    Text("$${product.price}", style = MaterialTheme.typography.bodyMedium)
//                                }
//                            }
//                        }
//                    }
//                }
//            }
//        }
    }


