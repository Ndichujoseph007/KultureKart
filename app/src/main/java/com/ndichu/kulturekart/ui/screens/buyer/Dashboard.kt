package com.ndichu.kulturekart.ui.screens.buyer

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import com.ndichu.kulturekart.data.ProductViewModel
import com.ndichu.kulturekart.ui.components.product.ProductItemCard
import com.ndichu.kulturekart.model.Product



@Composable
fun DashboardScreen(
    navController: NavController,
    viewModel: ProductViewModel = viewModel()
) {
    val context = LocalContext.current
    val allProducts by viewModel.productList.collectAsState()
    var searchQuery by remember { mutableStateOf("") }
    val filteredProducts :List<Product> = allProducts.filter {
        it.name.contains(searchQuery, ignoreCase = true) ||
                it.region.contains(searchQuery, ignoreCase = true)
    }

    LaunchedEffect(Unit) {
        viewModel.fetchProducts()
    }

    Column(modifier = Modifier.fillMaxSize()) {
        // 🔍 Search Bar
        OutlinedTextField(
            value = searchQuery,
            onValueChange = { searchQuery = it },
            placeholder = { Text("Search products or region") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            singleLine = true,
            leadingIcon = {
                Icon(Icons.Default.Search, contentDescription = "Search")
            }
        )

        // 🎞️ Image Slider (Carousel-style)
        ImageSlider(
            imageUrls = listOf(
                "https://placekitten.com/800/300",
                "https://picsum.photos/800/300",
                "https://placebear.com/800/300"
            )
        )

        // ⭐ Featured Products
        Text(
            text = "Featured Products",
            style = MaterialTheme.typography.headlineSmall,
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
        )

//        LazyColumn(
//            contentPadding = PaddingValues(16.dp),
//            verticalArrangement = Arrangement.spacedBy(12.dp)
//        ) {
//            items(filteredProducts.take(5)) { product ->
//                ProductItemCard(product = product) {
//                    Toast.makeText(context,product.name, Toast.LENGTH_SHORT).show()
//                }
//            }
//        }
    }
}

@Composable
fun ImageSlider(imageUrls: List<String>) {
    val pagerState = rememberPagerState { imageUrls.size }

    Column(modifier = Modifier.fillMaxWidth()) {
        HorizontalPager(
            state = pagerState,
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp)
        ) { page ->
            Image(
                painter = rememberAsyncImagePainter(imageUrls[page]),
                contentDescription = "Banner $page",
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 8.dp)
                    .clip(RoundedCornerShape(12.dp)),
                contentScale = ContentScale.Crop
            )
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            horizontalArrangement = Arrangement.Center
        ) {
            repeat(imageUrls.size) { index ->
                val isSelected = pagerState.currentPage == index
                Box(
                    Modifier
                        .size(if (isSelected) 10.dp else 6.dp)
                        .padding(2.dp)
                        .clip(CircleShape)
                        .background(
                            color = if (isSelected) MaterialTheme.colorScheme.primary else Color.Gray
                        )
                )
            }
        }
    }
}

