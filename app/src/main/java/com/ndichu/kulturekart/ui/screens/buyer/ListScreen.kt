package com.ndichu.kulturekart.ui.screens.buyer


import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.material3.CardDefaults
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.ndichu.kulturekart.model.Product
import com.ndichu.kulturekart.data.ProductViewModel
import com.google.firebase.auth.FirebaseAuth
import com.ndichu.kulturekart.navigation.ROUTE_BUYER_PRODUCT_DETAIL

@Composable
fun ProductListSreen(
    navController: NavHostController,
    productViewModel: ProductViewModel
) {
    val context = LocalContext.current
    val allProducts by productViewModel.products.collectAsState()
    val currentUserId = FirebaseAuth.getInstance().currentUser?.uid
    var searchQuery by remember { mutableStateOf(TextFieldValue("")) }
    var selectedRegion by remember { mutableStateOf("All") }

    val regionOptions = listOf("All", "Nairobi", "Coast", "Central", "Western", "Nyanza")

    LaunchedEffect(Unit) {
        productViewModel.fetchProducts()
    }

    val filteredProducts = allProducts.filter { product ->
        (selectedRegion == "All" || product.region == selectedRegion) &&
                product.sellerId == currentUserId &&
                (product.name.contains(searchQuery.text, true) || product.region.contains(searchQuery.text, true))
    }

    Column(modifier = Modifier
        .fillMaxSize()
        .padding(16.dp)) {

        OutlinedTextField(
            value = searchQuery,
            onValueChange = { searchQuery = it },
            label = { Text("Search") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(12.dp))

        // Dropdown for region filter
        RegionDropdown(selectedRegion) { selectedRegion = it }

        Spacer(modifier = Modifier.height(16.dp))

        if (filteredProducts.isEmpty()) {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Text("No products found")
            }
        } else {
            LazyColumn(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                items(filteredProducts) { product ->
                    ProductCard(product = product) {
                        navController.navigate("$ROUTE_BUYER_PRODUCT_DETAIL/${product.id}")
                    }
                }
            }
        }
    }
}

@Composable
fun RegionDropdown(selected: String, onSelectedChange: (String) -> Unit) {
    var expanded by remember { mutableStateOf(false) }
    val regions = listOf("All", "Nairobi", "Coast", "Central", "Western", "Nyanza")

    Box {
        OutlinedButton(onClick = { expanded = true }) {
            Text("Region: $selected")
        }
        DropdownMenu(expanded = expanded, onDismissRequest = { expanded = false }) {
            regions.forEach { region ->
                DropdownMenuItem(text = { Text(region) }, onClick = {
                    onSelectedChange(region)
                    expanded = false
                })
            }
        }
    }
}

@Composable
fun ProductCard(product: Product, onClick: () -> Unit) {
    Card(
        onClick = onClick,
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Row(modifier = Modifier.padding(16.dp)) {
            AsyncImage(
                model = product.imageUrl,
                contentDescription = null,
                modifier = Modifier
                    .size(80.dp)
                    .padding(end = 12.dp)
            )
            Column {
                Text(text = product.name, style = MaterialTheme.typography.titleMedium)
                Text(text = "Region: ${product.region}", style = MaterialTheme.typography.bodyMedium)
                Text(text = "Price: $${product.price}", style = MaterialTheme.typography.bodyMedium)
            }
        }
    }
}
