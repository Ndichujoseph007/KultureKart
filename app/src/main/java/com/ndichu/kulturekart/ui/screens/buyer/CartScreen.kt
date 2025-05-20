package com.ndichu.kulturekart.ui.screens.buyer

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import com.ndichu.kulturekart.data.CartViewModel
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import androidx.compose.foundation.lazy.items
import androidx.navigation.NavHostController


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CartScreen(navController: NavHostController,viewModel: CartViewModel = viewModel()) {
    val cartItems by viewModel.cartItems

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
        bottomBar = {
            val totalPrice = cartItems.sumOf { it.price * it.quantity }
            Button(
                onClick = { /* Handle checkout */ },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                Text("Checkout - $${"%.2f".format(totalPrice)}")
            }
        }
    ) { padding ->
        LazyColumn(modifier = Modifier.padding(padding)) {
            items(cartItems) { item ->
                Card(
                    modifier = Modifier
                        .padding(8.dp)
                        .fillMaxWidth()
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.padding(8.dp)
                    ) {
                        AsyncImage(
                            model = item.imageUrl,
                            contentDescription = item.name,
                            modifier = Modifier
                                .size(64.dp)
                                .clip(RoundedCornerShape(8.dp))
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Column(modifier = Modifier.weight(1f)) {
                            Text(item.name, style = MaterialTheme.typography.titleMedium)
                            Text("Price: $${item.price}")
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                IconButton(onClick = {
                                    if (item.quantity > 1)
                                        viewModel.updateQuantity(item.id, item.quantity - 1)
                                }) {
                                    Text("-")
                                }
                                Text("${item.quantity}", modifier = Modifier.padding(horizontal = 8.dp))
                                IconButton(onClick = {
                                    viewModel.updateQuantity(item.id, item.quantity + 1)
                                }) {
                                    Text("+")
                                }
                            }
                        }
                        IconButton(onClick = {
                            viewModel.removeFromCart(item.id)
                        }) {
                            Icon(Icons.Default.Delete, contentDescription = "Remove")
                        }
                    }
                }
            }
        }
    }
}


//@OptIn(ExperimentalMaterial3Api::class)
//@Composable
//fun CartScreen(viewModel: CartViewModel = viewModel()) {
//    val cartItems by viewModel.cartItems
//
//    Scaffold(
//        topBar = {
//            TopAppBar(title = { Text("Your Cart") })
//        },
//        bottomBar = {
//
//            val totalPrice = cartItems.sumOf { it.price.times(it.quantity) }
//
//
//
//
//            Button(
//                onClick = { /* TODO: Handle checkout */ },
//                modifier = Modifier
//                    .fillMaxWidth()
//                    .padding(16.dp)
//            ) {
//                Text("Checkout - $${"%.2f".format(totalPrice)}")
//            }
//        }
//    ) { padding ->
//        LazyColumn(modifier = Modifier.padding(padding)) {
//            items(cartItems) { item ->
//                Card(
//                    modifier = Modifier
//                        .padding(8.dp)
//                        .fillMaxWidth()
//                ) {
//                    Row(
//                        verticalAlignment = Alignment.CenterVertically,
//                        modifier = Modifier.padding(8.dp)
//                    ) {
//                        AsyncImage(
//                            model = item.imageUrl,
//                            contentDescription = item.name,
//                            modifier = Modifier
//                                .size(64.dp)
//                                .clip(RoundedCornerShape(8.dp))
//                        )
//                        Spacer(modifier = Modifier.width(8.dp))
//                        Column(modifier = Modifier.weight(1f)) {
//                            Text(item.name, style = MaterialTheme.typography.titleMedium)
//                            Text("Price: $${item.price}")
//                            Text("Quantity: ${item.quantity}")
//                        }
//                        IconButton(onClick = {
//                            viewModel.removeFromCart(item.id)
//                        }) {
//                            Icon(Icons.Default.Delete, contentDescription = "Remove")
//                        }
//                    }
//                }
//            }
//        }
//    }
//}


