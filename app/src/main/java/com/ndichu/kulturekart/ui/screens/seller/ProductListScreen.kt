package com.ndichu.kulturekart.ui.screens.seller

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.ndichu.kulturekart.data.ProductViewModel
import androidx.compose.runtime.collectAsState
import androidx.compose.foundation.lazy.items
import com.ndichu.kulturekart.navigation.ROUTE_PRODUCT_DETAIL
import com.ndichu.kulturekart.ui.components.product.ProductItemCard

@Composable
fun ProductListScreen(
    modifier: Modifier = Modifier,
    navController: NavController,
    viewModel: ProductViewModel = viewModel()
) {
    val productList = viewModel.productList.collectAsState(initial = emptyList())
    val isLoading = viewModel.isLoading.value
    val context = LocalContext.current

    LaunchedEffect(Unit) {
        viewModel.fetchProducts()
    }

    Column(modifier = Modifier.fillMaxSize()) {
        Text(
            text = "Products",
            style = MaterialTheme.typography.headlineMedium,
            modifier = Modifier.padding(16.dp)
        )

        if (isLoading) {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator()
            }
        } else {
            LazyColumn(
                contentPadding = PaddingValues(16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(productList.value) { product ->
                    ProductItemCard(product = product) {
                        navController.navigate("$ROUTE_PRODUCT_DETAIL/${product.id}")
                        Toast.makeText(context, product.name, Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
    }
}



