////package com.ndichu.kulturekart.ui.screens.buyer
////
////import android.widget.Toast
////import androidx.compose.foundation.Image
////import androidx.compose.foundation.background
////import androidx.compose.foundation.clickable
////import androidx.compose.foundation.layout.Arrangement
////import androidx.compose.foundation.layout.Box
////import androidx.compose.foundation.layout.Column
////import androidx.compose.foundation.layout.Row
////import androidx.compose.foundation.layout.fillMaxSize
////import androidx.compose.foundation.layout.fillMaxWidth
////import androidx.compose.foundation.layout.height
////import androidx.compose.foundation.layout.padding
////import androidx.compose.foundation.layout.size
////import androidx.compose.foundation.pager.HorizontalPager
////import androidx.compose.foundation.pager.rememberPagerState
////import androidx.compose.foundation.shape.CircleShape
////import androidx.compose.foundation.shape.RoundedCornerShape
////import androidx.compose.material.icons.Icons
////import androidx.compose.material.icons.filled.Search
////import androidx.compose.material3.Icon
////import androidx.compose.material3.MaterialTheme
////import androidx.compose.material3.OutlinedTextField
////import androidx.compose.material3.Text
////import androidx.compose.runtime.Composable
////import androidx.compose.runtime.LaunchedEffect
////import androidx.compose.runtime.collectAsState
////import androidx.compose.runtime.getValue
////import androidx.compose.runtime.mutableStateOf
////import androidx.compose.runtime.remember
////import androidx.compose.runtime.rememberCoroutineScope
////import androidx.compose.runtime.setValue
////import androidx.compose.ui.Modifier
////import androidx.compose.ui.draw.clip
////import androidx.compose.ui.graphics.Color
////import androidx.compose.ui.layout.ContentScale
////import androidx.compose.ui.platform.LocalContext
////import androidx.compose.ui.unit.dp
////import androidx.lifecycle.viewmodel.compose.viewModel
////import androidx.navigation.NavController
////import coil.compose.rememberAsyncImagePainter
////import com.ndichu.kulturekart.data.ProductViewModel
////import com.ndichu.kulturekart.model.Product
////import kotlinx.coroutines.delay
////
////
////@Composable
////fun DashboardScreen(
////    navController: NavController,
////    viewModel: ProductViewModel = viewModel()
////) {
////    val context = LocalContext.current
////    val allProducts by viewModel.productList.collectAsState()
////    var searchQuery by remember { mutableStateOf("") }
////
////    val filteredProducts = allProducts.filter { product ->
////        product.name.contains(searchQuery, ignoreCase = true) ||
////                product.region.contains(searchQuery, ignoreCase = true)
////    }
////
////    LaunchedEffect(Unit) {
////        viewModel.fetchProducts()
////    }
////
////    Column(modifier = Modifier.fillMaxSize()) {
////        // 🔍 Search Bar
////        OutlinedTextField(
////            value = searchQuery,
////            onValueChange = { searchQuery = it },
////            placeholder = { Text("Search products or region") },
////            modifier = Modifier
////                .fillMaxWidth()
////                .padding(16.dp),
////            singleLine = true,
////            leadingIcon = {
////                Icon(Icons.Default.Search, contentDescription = "Search")
////            }
////        )
////
////        // 🎞️ Image Slider
////        ImageSlider(
////            products = filteredProducts.take(5),
////            navController = navController
////        )
////
////
////        // ⭐ Featured Products Title
////        Text(
////            text = "Featured Products",
////            style = MaterialTheme.typography.headlineSmall,
////            modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
////        )
//////
//////        // 🛍️ Product List
//////        filteredProducts.take(5).forEach { product ->
//////            ProductItemCard(product = product) {
//////                Toast.makeText(context, product.name, Toast.LENGTH_SHORT).show()
//////            }
//////        }
////    }
////}
////
////
////
////@Composable
////fun ImageSlider(
////    products: List<Product>,
////    navController: NavController
////) {
////    val pagerState = rememberPagerState { products.size }
////    val coroutineScope = rememberCoroutineScope()
////
////    // 🔁 Auto-scroll effect
////    LaunchedEffect(pagerState.currentPage) {
////        delay(1000) // 3 seconds
////        val nextPage = (pagerState.currentPage + 1) % products.size
////        pagerState.animateScrollToPage(nextPage)
////    }
////
////    Column(modifier = Modifier.fillMaxWidth()) {
////        HorizontalPager(
////            state = pagerState,
////            modifier = Modifier
////                .fillMaxWidth()
////                .height(200.dp)
////                .clickable {
////                    val clickedProduct = products[pagerState.currentPage]
////                    // 🔀 Navigate to product detail screen
////                    navController.navigate("productDetail/${clickedProduct.id}")
////                }
////        ) { page ->
////            Image(
////                painter = rememberAsyncImagePainter(products[page].imageUrl),
////                contentDescription = products[page].name,
////                modifier = Modifier
////                    .fillMaxSize()
////                    .padding(horizontal = 8.dp)
////                    .clip(RoundedCornerShape(12.dp)),
////                contentScale = ContentScale.Crop
////            )
////        }
////
////        // Dots Indicator
////        Row(
////            modifier = Modifier
////                .fillMaxWidth()
////                .padding(top = 8.dp),
////            horizontalArrangement = Arrangement.Center
////        ) {
////            repeat(products.size) { index ->
////                val isSelected = pagerState.currentPage == index
////                Box(
////                    Modifier
////                        .size(if (isSelected) 10.dp else 6.dp)
////                        .padding(2.dp)
////                        .clip(CircleShape)
////                        .background(
////                            if (isSelected) MaterialTheme.colorScheme.primary else Color.Gray
////                        )
////                )
////            }
////        }
////    }
////}
//
//
//
////package com.ndichu.kulturekart.ui.screens.buyer
////
////
////
////import android.content.Intent
////import android.net.Uri
////import androidx.compose.foundation.Image
////import androidx.compose.foundation.clickable
////import androidx.compose.foundation.layout.*
////import androidx.compose.foundation.shape.RoundedCornerShape
////import androidx.compose.material.icons.Icons
////import androidx.compose.material.icons.filled.*
////import androidx.compose.material3.*
////import androidx.compose.runtime.Composable
////import androidx.compose.runtime.mutableStateOf
////import androidx.compose.runtime.remember
////import androidx.compose.ui.Alignment
////import androidx.compose.ui.Modifier
////import androidx.compose.ui.layout.ContentScale
////import androidx.compose.ui.platform.LocalContext
////import androidx.compose.ui.res.painterResource
////import androidx.compose.ui.tooling.preview.Preview
////import androidx.compose.ui.unit.dp
////import androidx.navigation.NavController
////import androidx.navigation.compose.rememberNavController
////import com.ndichu.kulturekart.R
////import com.ndichu.kulturekart.data.ProductViewModel
////import com.ndichu.kulturekart.navigation.ROUTE_ADD_PRODUCT
////import androidx.compose.foundation.Image
////import androidx.compose.foundation.clickable
////import androidx.compose.foundation.layout.*
////import androidx.compose.foundation.lazy.LazyColumn
////import androidx.compose.foundation.lazy.items
////import androidx.compose.material3.*
////import androidx.compose.runtime.*
////import androidx.compose.ui.text.font.FontWeight
////import androidx.compose.ui.unit.sp
////import coil.compose.rememberAsyncImagePainter
////import com.ndichu.kulturekart.model.Product
////
////@OptIn(ExperimentalMaterial3Api::class)
////@Composable
////fun DashboardScreen(
////    navController: NavController,
////    productViewModel: ProductViewModel = androidx.lifecycle.viewmodel.compose.viewModel()
////) {
////    val selectedItem = remember { mutableStateOf(0) }
////    val products by productViewModel.products.collectAsState()
////
////    Scaffold(
////        bottomBar = {
////            NavigationBar {
////                NavigationBarItem(
////                    selected = selectedItem.value == 0,
////                    onClick = { selectedItem.value = 0 },
////                    icon = { Icon(Icons.Filled.Home, contentDescription = "Home") },
////                    label = { Text("Home") },
////                    alwaysShowLabel = true,
////                )
////                NavigationBarItem(
////                    selected = selectedItem.value == 1,
////                    onClick = { selectedItem.value = 1 },
////                    icon = { Icon(Icons.Filled.Person, contentDescription = "Profile") },
////                    label = { Text("Profile") },
////                    alwaysShowLabel = true,
////                )
////            }
////        }
////    ) { innerPadding ->
////
////        Box(
////            modifier = Modifier
////                .fillMaxSize()
////                .padding(innerPadding)
////        ) {
////            Image(
////                painter = painterResource(id = R.drawable.background),
////                contentDescription = "background image",
////                contentScale = ContentScale.FillBounds,
////                modifier = Modifier.matchParentSize()
////            )
////        }
////
////        Column(
////            modifier = Modifier
////                .fillMaxSize()
////                .padding(16.dp)
////        ) {
////            Text(
////                text = "Available Products",
////                style = MaterialTheme.typography.headlineMedium,
////                modifier = Modifier.padding(bottom = 12.dp)
////            )
////
////            LazyColumn(
////                modifier = Modifier.fillMaxSize(),
////                verticalArrangement = Arrangement.spacedBy(12.dp)
////            ) {
////                items(products) { product ->
////                    ProductCard(product = product, onClick = {
////                         navController.navigate("productDetails/${product.id}")
////                    })
////                }
////            }
////        }
////    }
////}
////
////@Composable
////fun ProductCard(product: Product, onClick: () -> Unit) {
////    Card(
////        modifier = Modifier
////            .fillMaxWidth()
////            .height(150.dp)
////            .clickable { onClick() },
////        shape = RoundedCornerShape(16.dp),
////        elevation = CardDefaults.cardElevation(6.dp)
////    ) {
////        Row(
////            modifier = Modifier.fillMaxSize()
////        ) {
////            // Product Image
////            Image(
////                painter = rememberAsyncImagePainter(model = product.imageUrl),
////                contentDescription = product.name,
////                contentScale = ContentScale.Crop,
////                modifier = Modifier
////                    .width(150.dp)
////                    .fillMaxHeight()
////            )
////
////            Spacer(modifier = Modifier.width(16.dp))
////
////            // Product Details
////            Column(
////                modifier = Modifier
////                    .padding(vertical = 16.dp)
////                    .fillMaxHeight(),
////                verticalArrangement = Arrangement.SpaceBetween
////            ) {
////                Text(
////                    text = product.name,
////                    style = MaterialTheme.typography.titleMedium.copy(
////                        fontWeight = FontWeight.Bold,
////                        fontSize = 18.sp
////                    )
////                )
////                Text(
////                    text = product.region,
////                    style = MaterialTheme.typography.bodyMedium
////                )
////                Text(
////                    text = "$${product.price}",
////                    style = MaterialTheme.typography.titleMedium.copy(
////                        color = MaterialTheme.colorScheme.primary,
////                        fontWeight = FontWeight.Bold
////                    )
////                )
////            }
////        }
////    }
////}
//import android.content.Intent
//import android.net.Uri
//import androidx.compose.animation.core.*
//import androidx.compose.foundation.Image
//import androidx.compose.foundation.background
//import androidx.compose.foundation.clickable
//import androidx.compose.foundation.layout.*
//import androidx.compose.foundation.lazy.LazyColumn
//import androidx.compose.foundation.lazy.LazyRow
//import androidx.compose.foundation.lazy.items
//import androidx.compose.foundation.lazy.itemsIndexed
//import androidx.compose.foundation.shape.RoundedCornerShape
//import androidx.compose.material.icons.Icons
//import androidx.compose.material.icons.filled.*
//import androidx.compose.material3.*
//import androidx.compose.runtime.*
//import androidx.compose.ui.Alignment
//import androidx.compose.ui.Modifier
//import androidx.compose.ui.draw.clip
//import androidx.compose.ui.graphics.Color
//import androidx.compose.ui.layout.ContentScale
//import androidx.compose.ui.platform.LocalContext
//import androidx.compose.ui.res.painterResource
//import androidx.compose.ui.text.input.TextFieldValue
//import androidx.compose.ui.tooling.preview.Preview
//import androidx.compose.ui.unit.dp
//import androidx.navigation.NavController
//import androidx.navigation.compose.rememberNavController
//import coil.compose.rememberAsyncImagePainter
//import com.ndichu.kulturekart.R
//import com.ndichu.kulturekart.data.ProductViewModel
//import com.ndichu.kulturekart.model.Product
//import com.ndichu.kulturekart.navigation.ROUTE_ADD_PRODUCT
//import com.ndichu.kulturekart.navigation.ROUTE_DASHBOARD
//import com.ndichu.kulturekart.navigation.ROUTE_PRODUCT_DETAIL
//import com.ndichu.kulturekart.navigation.ROUTE_PRODUCT_LIST
//import com.ndichu.kulturekart.navigation.ROUTE_PROFILE
//import kotlinx.coroutines.delay
//
//@OptIn(ExperimentalMaterial3Api::class)
//@Composable
//fun DashboardScreen(
//    navController: NavController,
//    productViewModel: ProductViewModel = androidx.lifecycle.viewmodel.compose.viewModel()
//) {
//    val selectedItem = remember { mutableStateOf(0) }
//    val products by productViewModel.products.collectAsState()
//    val context = LocalContext.current
//
//    // Search Query State
//    var searchQuery by remember { mutableStateOf(TextFieldValue("")) }
//
//    // Filtered products based on search query
//    val filteredProducts = remember(searchQuery, products) {
//        if (searchQuery.text.isEmpty()) products
//        else products.filter {
//            it.name.contains(searchQuery.text, ignoreCase = true) ||
//                    it.description.contains(searchQuery.text, ignoreCase = true) ||
//                    it.region.contains(searchQuery.text, ignoreCase = true)
//        }
//    }
//
//    // Image slider images (could be product images or predefined)
//    val sliderImages = listOf(
//        R.drawable.logo1,
//        R.drawable.logo,
//
//    )
//
//    // Auto-scroll state for image slider
//    val carouselState = remember { mutableStateOf(0) }
//
//    // Auto slide effect
//    LaunchedEffect(key1 = carouselState.value) {
//        delay(3000) // 3 seconds per slide
//        carouselState.value = (carouselState.value + 1) % sliderImages.size
//    }
//
//    Scaffold(
//        topBar = {
//            TopAppBar(
//                title = { Text("KultureKart") },
//
//
//                actions = {
//                    IconButton(onClick = {
//                        val sendIntent = Intent().apply {
//                            action = Intent.ACTION_SEND
//                            putExtra(Intent.EXTRA_TEXT, "Download KultureKart here: https://www.download.com")
//                            type = "text/plain"
//                        }
//                        val shareIntent = Intent.createChooser(sendIntent, null)
//                        context.startActivity(shareIntent)
//                    }) {
//                        Icon(Icons.Filled.Share, contentDescription = "Share")
//                    }
//                    IconButton(onClick = {
//                        val intent = Intent(Intent.ACTION_DIAL).apply {
//                            data = Uri.parse("tel:0114306063")
//                        }
//                        context.startActivity(intent)
//                    }) {
//                        Icon(Icons.Filled.Phone, contentDescription = "Phone")
//                    }
//                    IconButton(onClick = {
//                        val intent = Intent(Intent.ACTION_SENDTO).apply {
//                            data = Uri.parse("mailto:info@joendichu")
//                            putExtra(Intent.EXTRA_SUBJECT, "Inquiry")
//                            putExtra(Intent.EXTRA_TEXT, "Hello, I would like to join you. Please help.")
//                        }
//                        context.startActivity(intent)
//                    }) {
//                        Icon(Icons.Filled.Email, contentDescription = "Email")
//                    }
//                }
//            )
//        },
//        bottomBar = {
//            NavigationBar {
//                NavigationBarItem(
//                    selected = selectedItem.value == 0,
//                    onClick = { navController.navigate(ROUTE_DASHBOARD)},
//                    icon = { Icon(Icons.Filled.Home, contentDescription = "Home") },
//                    label = { Text("Home") },
//                    alwaysShowLabel = true,
//                )
//                NavigationBarItem(
//                    selected = selectedItem.value == 1,
//                    onClick = { navController.navigate(ROUTE_PROFILE) },
//                    icon = { Icon(Icons.Filled.Person, contentDescription = "Profile") },
//                    label = { Text("Profile") },
//                    alwaysShowLabel = true,
//                )
//                NavigationBarItem(
//                    selected = selectedItem.value == 1,
//                    onClick = { navController.navigate(ROUTE_PRODUCT_LIST) },
//                    icon = { Icon(Icons.Filled.List, contentDescription = "category") },
//                    label = { Text("Profile") },
//                    alwaysShowLabel = true,
//                )
//            }
//        }
//    ) { innerPadding ->
//
//        Column(
//            modifier = Modifier
//                .fillMaxSize()
//                .padding(innerPadding)
//                .padding(16.dp)
//        ) {
//
//            // Search Field
//            OutlinedTextField(
//                value = searchQuery,
//                onValueChange = { searchQuery = it },
//                placeholder = { Text("Search products...") },
//                leadingIcon = { Icon(Icons.Filled.Search, contentDescription = "Search Icon") },
//                modifier = Modifier.fillMaxWidth(),
//                singleLine = true,
//                shape = RoundedCornerShape(8.dp),
//
//            )
//
//            Spacer(modifier = Modifier.height(16.dp))
//
//            // Image slider (carousel)
//            Box(
//                modifier = Modifier
//                    .fillMaxWidth()
//                    .height(180.dp),
//                contentAlignment = Alignment.Center
//            ) {
//                LazyRow(
//                    modifier = Modifier.fillMaxSize(),
//                    userScrollEnabled = false // disable manual scrolling for auto-slide effect
//                ) {
//                    itemsIndexed(sliderImages) { index, imageRes ->
//                        if (index == carouselState.value) {
//                            Image(
//                                painter = painterResource(id = imageRes),
//                                contentDescription = "Slider Image $index",
//                                modifier = Modifier
//                                    .fillParentMaxWidth()
//                                    .fillParentMaxHeight(),
//                                contentScale = ContentScale.Crop
//                            )
//                        }
//                    }
//                }
//            }
//
//            Spacer(modifier = Modifier.height(24.dp))
//
//            Text(
//                text = "Featured Products",
//                style = MaterialTheme.typography.headlineSmall,
//                modifier = Modifier.padding(bottom = 8.dp)
//            )
//            // List of products filtered by search
//            LazyColumn(
//                verticalArrangement = Arrangement.spacedBy(12.dp),
//                modifier = Modifier.fillMaxSize()
//            ) {
//                items(filteredProducts) { product ->
//                    ProductCard(product = product, onClick = {
//                        navController.navigate("$ROUTE_PRODUCT_DETAIL{product.id}")
//                    })
//                }
//            }
//        }
//    }
//}
//
//@Composable
//fun ProductCard(product: Product, onClick: () -> Unit) {
//    Card(
//        modifier = Modifier
//            .fillMaxWidth()
//            .clickable { onClick() },
//        shape = RoundedCornerShape(12.dp),
//        elevation = CardDefaults.cardElevation(6.dp)
//    ) {
//        Row(
//            modifier = Modifier.padding(12.dp),
//            verticalAlignment = Alignment.CenterVertically
//        ) {
//            // Product image
//            Image(
//                painter = rememberAsyncImagePainter(product.imageUrl),
//                contentDescription = product.name,
//                modifier = Modifier
//                    .size(80.dp)
//                    .clip(RoundedCornerShape(8.dp)),
//                contentScale = ContentScale.Crop
//            )
//            Spacer(modifier = Modifier.width(12.dp))
//
//            Column(modifier = Modifier.weight(1f)) {
//                Text(text = product.name, style = MaterialTheme.typography.titleMedium)
//                Spacer(modifier = Modifier.height(4.dp))
//                Text(
//                    text = product.description,
//                    style = MaterialTheme.typography.bodyMedium,
//                    maxLines = 2,
//                    color = MaterialTheme.colorScheme.onSurfaceVariant
//                )
//                Spacer(modifier = Modifier.height(4.dp))
//                Text(
//                    text = "$${product.price}",
//                    style = MaterialTheme.typography.bodyLarge,
//                    color = MaterialTheme.colorScheme.primary
//                )
//            }
//        }
//    }
//}
//
//
//
//@Preview(showBackground = true, showSystemUi = true)
//@Composable
//fun DashboardScreenPreview() {
//    DashboardScreen(navController = rememberNavController())
//}

package com.ndichu.kulturekart.ui.screens.buyer


import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
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
import com.ndichu.kulturekart.navigation.ROUTE_BUYER_PRODUCT_DETAIL
import com.ndichu.kulturekart.navigation.ROUTE_CART
import com.ndichu.kulturekart.navigation.ROUTE_PRODUCT_DETAIL_WITH_ARG
import com.ndichu.kulturekart.navigation.ROUTE_PROFILE
import com.ndichu.kulturekart.ui.components.product.ProductCard

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DashboardScreen(
    navController: NavController,
    viewModel: ProductViewModel = androidx.lifecycle.viewmodel.compose.viewModel()
) {
    val context = LocalContext.current
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
                        text = "KultureKart Market Place",
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
                            product = product,
                            onClick = {
                                navController.navigate("$ROUTE_BUYER_PRODUCT_DETAIL/${product.id}")
                            }
                        )
                    }
                }
            }
        }
    }
}

//
//@Composable
//fun ProductCard(
//    product: Product,
//    onClick: () -> Unit,
//    onEdit: (() -> Unit)? = null,
//    onDelete: (() -> Unit)? = null
//) {
//    Card(
//        onClick = onClick,
//        modifier = Modifier.fillMaxWidth()
//    ) {
//        Row(modifier = Modifier.padding(16.dp)) {
//            Image(
//                painter = rememberAsyncImagePainter(product.imageUrl),
//                contentDescription = product.name,
//                modifier = Modifier
//                    .size(80.dp)
//                    .padding(end = 16.dp)
//            )
//            Column {
//                Text(product.name, style = MaterialTheme.typography.titleMedium)
//                Text(product.region, style = MaterialTheme.typography.bodySmall)
//                Text("$${product.price}", style = MaterialTheme.typography.bodyMedium)
//            }
//        }
//    }
//}
@Preview(showBackground = true, showSystemUi = true)
@Composable
fun DashboardScreenPreview() {
    val navController = rememberNavController()
    DashboardScreen(navController = navController)
}

