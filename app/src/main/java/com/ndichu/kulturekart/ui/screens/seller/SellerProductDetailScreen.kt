//package com.ndichu.kulturekart.ui.screens.seller
//
//import android.widget.Toast
//import androidx.compose.foundation.clickable
//import androidx.compose.runtime.Composable
//import androidx.navigation.NavController
//import com.ndichu.kulturekart.data.ProductViewModel
//import androidx.compose.foundation.layout.*
//import androidx.compose.material3.*
//import androidx.compose.runtime.*
//import androidx.compose.ui.Alignment
//import androidx.compose.ui.Modifier
//import androidx.compose.ui.platform.LocalContext
//import androidx.compose.ui.unit.dp
//import androidx.lifecycle.viewmodel.compose.viewModel
//import coil.compose.AsyncImage
//import androidx.compose.foundation.layout.Column
//import androidx.compose.foundation.layout.Row
//import androidx.compose.foundation.layout.fillMaxSize
//import androidx.compose.foundation.layout.fillMaxWidth
//import androidx.compose.foundation.layout.padding
//import androidx.compose.foundation.lazy.LazyColumn
//import androidx.compose.material3.ExperimentalMaterial3Api
//import androidx.compose.material3.MaterialTheme
//import androidx.compose.material3.Scaffold
//import androidx.compose.material3.Text
//import androidx.compose.material3.TopAppBar
//import androidx.compose.runtime.LaunchedEffect
//import com.ndichu.kulturekart.model.Product
//import com.ndichu.kulturekart.navigation.ROUTE_EDIT_PRODUCT
//
//
////@OptIn(ExperimentalMaterial3Api::class)
////@Composable
////fun SellerProductDetailScreen(
////    productId: String,
////    navController: NavController,
////    viewModel: ProductViewModel = viewModel()
////) {
////    var product by remember { mutableStateOf<Product?>(null) }
////    val context = LocalContext.current
////
////
////    LaunchedEffect(productId) {
////        viewModel.getProductById(productId) {
////            product = it
////        }
////    }
////
////    product?.let {
////        Scaffold(
////            topBar = {
////                TopAppBar(title = { Text("Product Details") })
////            }
////        ) { padding ->
////            Column(
////                modifier = Modifier
////                    .padding(padding)
////                    .padding(16.dp)
////                    .fillMaxSize(),
////                verticalArrangement = Arrangement.spacedBy(12.dp)
////            ) {
////                AsyncImage(
////                    model = it.imageUrl,
////                    contentDescription = null,
////                    modifier = Modifier
////                        .fillMaxWidth()
////                        .height(200.dp)
////                )
////                Text(text = "Name: ${it.name}", style = MaterialTheme.typography.headlineSmall)
////                Text(text = "Price: ${it.price}", style = MaterialTheme.typography.bodyLarge)
////                Text(text = "Region: ${it.region}", style = MaterialTheme.typography.bodyLarge)
////                Text(
////                    text = "Description: ${it.description}",
////                    style = MaterialTheme.typography.bodyMedium
////                )
////
////                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
////                    Button(onClick = {
////                        navController.navigate("edit_product/${productId}")
////                    }) {
////                        Text("Edit")
////                    }
////                    Button(
////                        onClick = {
////                            viewModel.deleteProduct(productId) { success ->
////                                if (success) {
////                                    Toast.makeText(context, "Deleted", Toast.LENGTH_SHORT).show()
////                                    navController.popBackStack()
////                                } else {
////                                    Toast.makeText(context, "Failed", Toast.LENGTH_SHORT).show()
////                                }
////                            }
////
////                        },
////                        colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.error)
////                    ) {
////                        Text("Delete")
////                    }
////                }
////            }
////        }
////    } ?: run {
////        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
////            CircularProgressIndicator()
////        }
////    }
////}
//
//
//
////@OptIn(ExperimentalMaterial3Api::class)
////@Composable
////fun SellersHomeScreen(navController: NavController, viewModel: ProductViewModel = viewModel()) {
////    val sellerProducts by viewModel.sellerProducts.collectAsState()
////
////    LaunchedEffect(productId) {
////        viewModel.getProductById(productId) {
////            product = it
////        }
////    }
////
////
////
////    Scaffold(
////        topBar = { TopAppBar(title = { Text("Seller Dashboard") }) },
////        floatingActionButton = {
////            FloatingActionButton(onClick = { navController.navigate("add_product") }) {
////                Icon(Icons.Default.Add, contentDescription = "Add Product")
////            }
////        }
////    ) { padding ->
////        LazyColumn(
////            contentPadding = padding,
////            modifier = Modifier.fillMaxSize()
////        ) {
////            items(sellerProducts) { product ->
////                Card(
////                    modifier = Modifier
////                        .padding(8.dp)
////                        .fillMaxWidth()
////                        .clickable {
////                            navController.navigate("seller_product_detail/${product.id}")
////                        }
////                ) {
////                    Row(modifier = Modifier.padding(16.dp)) {
////                        AsyncImage(
////                            model = it.imageUrl,
////                            contentDescription = null,
////                            modifier = Modifier
////                                .size(80.dp)
////                                .padding(end = 16.dp)
////                        )
////                        Column {
////
////                        }
////                    }
////                }
////            }
////        }
////    }
////}
//
////@OptIn(ExperimentalMaterial3Api::class)
////@Composable
////fun SellerProductDetailScreen(
////    productId: String,
////    navController: NavController,
////    viewModel: ProductViewModel = viewModel()
////) {
////    var product by remember { mutableStateOf<Product?>(null) }
////    val context = LocalContext.current
////
////    LaunchedEffect(productId) {
////        viewModel.getProductById(productId) {
////            product = it
////        }
////    }
////
////    product?.let {
////        Scaffold(
////            topBar = {
////                TopAppBar(title = { Text("Product Details") })
////            }
////        ) { padding ->
////            LazyColumn(
////                modifier = Modifier
////                    .padding(padding)
////                    .padding(16.dp)
////                    .fillMaxSize(),
////                verticalArrangement = Arrangement.spacedBy(12.dp),
////                horizontalAlignment = Alignment.Start
////            ) {
////                item {
////                    AsyncImage(
////                        model = it.imageUrl,
////                        contentDescription = null,
////                        modifier = Modifier
////                            .fillMaxWidth()
////                            .height(200.dp)
////                    )
////                }
////
////                item {
////                    Text(text = "Name: ${it.name}", style = MaterialTheme.typography.headlineSmall)
////                }
////
////                item {
////                    Text(text = "Price: ${it.price}", style = MaterialTheme.typography.bodyLarge)
////                }
////
////                item {
////                    Text(text = "Region: ${it.region}", style = MaterialTheme.typography.bodyLarge)
////                }
////
////                item {
////                    Text(
////                        text = "Description: ${it.description}",
////                        style = MaterialTheme.typography.bodyMedium
////                    )
////                }
////
////                item {
////                    Row(
////                        modifier = Modifier.fillMaxWidth(),
////                        horizontalArrangement = Arrangement.spacedBy(8.dp)
////                    ) {
////                        Button(
////                            onClick = {
////                                navController.navigate("$ROUTE_EDIT_PRODUCT/${productId}")
////                            }
////                        ) {
////                            Text("Edit")
////                        }
////
////                        Button(
////                            onClick = {
////                                viewModel.deleteProduct(productId) { success ->
////                                    if (success) {
////                                        Toast.makeText(context, "Deleted", Toast.LENGTH_SHORT).show()
////                                        navController.popBackStack()
////                                    } else {
////                                        Toast.makeText(context, "Failed", Toast.LENGTH_SHORT).show()
////                                    }
////                                }
////                            },
////                            colors = ButtonDefaults.buttonColors(
////                                containerColor = MaterialTheme.colorScheme.error
////                            )
////                        ) {
////                            Text("Delete")
////                        }
////                    }
////                }
////            }
////        }
////    } ?: run {
////        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
////            CircularProgressIndicator()
////        }
////    }
////}
//
//@OptIn(ExperimentalMaterial3Api::class)
//@Composable
//fun SellerProductDetailScreen(
//    productId: String,
//    navController: NavController,
//    viewModel: ProductViewModel = viewModel()
//) {
//    var product by remember { mutableStateOf<Product?>(null) }
//    val context = LocalContext.current
//
//    LaunchedEffect(productId) {
//        viewModel.getProductById(productId) {
//            product = it
//        }
//    }
//
//    product?.let {
//        Scaffold(
//            topBar = {
//                TopAppBar(title = { Text("Product Details") })
//            }
//        ) { padding ->
//            LazyColumn(
//                modifier = Modifier
//                    .padding(padding)
//                    .padding(16.dp)
//                    .fillMaxSize(),
//                verticalArrangement = Arrangement.spacedBy(12.dp),
//                horizontalAlignment = Alignment.Start
//            ) {
//                item {
//                    AsyncImage(
//                        model = it.imageUrl,
//                        contentDescription = null,
//                        modifier = Modifier
//                            .fillMaxWidth()
//                            .height(200.dp)
//                    )
//                }
//
//                item {
//                    Text(text = "Name: ${it.name}", style = MaterialTheme.typography.headlineSmall)
//                }
//
//                item {
//                    Text(text = "Price: ${it.price}", style = MaterialTheme.typography.bodyLarge)
//                }
//
//                item {
//                    Text(text = "Region: ${it.region}", style = MaterialTheme.typography.bodyLarge)
//                }
//
//                item {
//                    Text(
//                        text = "Description: ${it.description}",
//                        style = MaterialTheme.typography.bodyMedium
//                    )
//                }
//
//                item {
//                    Row(
//                        modifier = Modifier.fillMaxWidth(),
//                        horizontalArrangement = Arrangement.spacedBy(8.dp)
//                    ) {
//                        Button(
//                            onClick = {
//                                navController.navigate("$ROUTE_EDIT_PRODUCT/${productId}")
//                            }
//                        ) {
//                            Text("Edit")
//                        }
//
//                        Button(
//                            onClick = {
//                                viewModel.deleteProduct(productId) { success ->
//                                    if (success) {
//                                        Toast.makeText(context, "Deleted", Toast.LENGTH_SHORT).show()
//                                        navController.popBackStack()
//                                    } else {
//                                        Toast.makeText(context, "Failed", Toast.LENGTH_SHORT).show()
//                                    }
//                                }
//                            },
//                            colors = ButtonDefaults.buttonColors(
//                                containerColor = MaterialTheme.colorScheme.error
//                            )
//                        ) {
//                            Text("Delete")
//                        }
//                    }
//                }
//            }
//        }
//    } ?: run {
//        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
//            CircularProgressIndicator()
//        }
//    }
//}
