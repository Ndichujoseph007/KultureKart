//package com.ndichu.kulturekart.ui.screens.buyer
//
//import androidx.compose.runtime.Composable
//import androidx.lifecycle.viewmodel.compose.viewModel
//import com.ndichu.kulturekart.data.CartViewModel
//import androidx.compose.foundation.layout.*
//import androidx.compose.foundation.lazy.LazyColumn
//import androidx.compose.foundation.shape.RoundedCornerShape
//import androidx.compose.material.icons.Icons
//import androidx.compose.material.icons.filled.Delete
//import androidx.compose.material3.*
//import androidx.compose.runtime.*
//import androidx.compose.ui.Alignment
//import androidx.compose.ui.Modifier
//import androidx.compose.ui.draw.clip
//import androidx.compose.ui.unit.dp
//import coil.compose.AsyncImage
//import androidx.compose.foundation.lazy.items
//import androidx.compose.material.icons.filled.ArrowBack
//import androidx.navigation.NavHostController
//import com.ndichu.kulturekart.model.CartItem
//import kotlinx.coroutines.coroutineScope
//import kotlinx.coroutines.launch
//
//@OptIn(ExperimentalMaterial3Api::class)
//@Composable
//fun CartScreen(navController: NavHostController, viewModel: CartViewModel = viewModel()) {
//    val cartItems by viewModel.cartItems
//    val snackbarHostState = remember { SnackbarHostState() }
//    val coroutineScope = rememberCoroutineScope()
//
//    var itemToDelete by remember { mutableStateOf<CartItem?>(null) }
//
//    Scaffold(
//        topBar = {
//            TopAppBar(
//                title = {
//                    Text(
//                        text = "My Cart.",
//                        style = MaterialTheme.typography.titleLarge,
//                        color = MaterialTheme.colorScheme.onPrimary
//                    )
//                },
//                colors = TopAppBarDefaults.topAppBarColors(
//                    containerColor = MaterialTheme.colorScheme.primary
//                ),
//                navigationIcon = {
//                    IconButton(onClick = { navController.popBackStack() }) {
//                        Icon(Icons.Filled.ArrowBack, contentDescription = "Back")
//                    }
//                }
//            )
//        },
//        bottomBar = {
//            val totalPrice = cartItems.sumOf { it.price * it.quantity }
//            Button(
//                onClick = {
//                    viewModel.checkout {
//                        coroutineScope.launch {
//                            snackbarHostState.showSnackbar("Checkout successful! Total: $${"%.2f".format(totalPrice)}")
//                        }
//                    }
//                },
//                enabled = cartItems.isNotEmpty(),
//                modifier = Modifier
//                    .fillMaxWidth()
//                    .padding(16.dp)
//            ) {
//                Text("Checkout - $${"%.2f".format(totalPrice)}")
//            }
//        },
//        snackbarHost = { SnackbarHost(snackbarHostState) }
//    ) { padding ->
//        Text(
//            text = "Turn gold into memories.",
//            style = MaterialTheme.typography.titleLarge,
//            color = MaterialTheme.colorScheme.onPrimary
//        )
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
//                            itemToDelete = item
//                        }) {
//                            Icon(Icons.Default.Delete, contentDescription = "Remove")
//                        }
//                    }
//                }
//            }
//        }
//
//        // Confirmation dialog for removing item
//        itemToDelete?.let { item ->
//            AlertDialog(
//                onDismissRequest = { itemToDelete = null },
//                confirmButton = {
//                    TextButton(onClick = {
//                        viewModel.removeFromCart(item.id)
//                        itemToDelete = null
//                        coroutineScope.launch {
//                            snackbarHostState.showSnackbar("${item.name} removed from cart.")
//                        }
//                    }) {
//                        Text("Remove")
//                    }
//                },
//                dismissButton = {
//                    TextButton(onClick = { itemToDelete = null }) {
//                        Text("Cancel")
//                    }
//                },
//                title = { Text("Remove Item") },
//                text = { Text("Are you sure you want to remove \"${item.name}\" from your cart?") }
//            )
//        }
//    }
//}
package com.ndichu.kulturekart.ui.screens.buyer

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.ndichu.kulturekart.data.CartViewModel
import com.ndichu.kulturekart.model.CartItem
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CartScreen(
    navController: NavHostController,
    viewModel: CartViewModel = viewModel()
) {
    val cartItems by viewModel.cartItems          // <-- State<List<CartItem>>
    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()

    var itemToDelete by remember { mutableStateOf<CartItem?>(null) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text("My Cart.", style = MaterialTheme.typography.titleLarge)
                },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.Filled.ArrowBack, contentDescription = "Back")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary
                )
            )
        },
        bottomBar = {
            val totalPrice = cartItems.sumOf { it.price * it.quantity }
            Button(
                onClick = {
                    viewModel.checkout {
                        scope.launch {
                            snackbarHostState.showSnackbar(
                                "Checkout successful! Total: $${"%.2f".format(totalPrice)}"
                            )
                        }
                    }
                },
                enabled = cartItems.isNotEmpty(),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) { Text("Checkout – $${"%.2f".format(totalPrice)}") }
        },
        snackbarHost = { SnackbarHost(snackbarHostState) }
    ) { padding ->
        LazyColumn(modifier = Modifier.padding(padding)) {
            items(cartItems, key = { it.id }) { item ->
                CartItemRow(
                    item = item,
                    onIncrement = { viewModel.incrementQuantity(item.id) },
                    onDecrement = { viewModel.decrementQuantity(item.id) },
                    onDelete = { itemToDelete = item }
                )
            }
        }

        /** remove-item confirmation dialog */
        itemToDelete?.let { item ->
            AlertDialog(
                onDismissRequest = { itemToDelete = null },
                confirmButton = {
                    TextButton(onClick = {
                        viewModel.removeFromCart(item.id)
                        itemToDelete = null
                        scope.launch {
                            snackbarHostState.showSnackbar(
                                "\"${item.name}\" removed from cart."
                            )
                        }
                    }) { Text("Remove") }
                },
                dismissButton = {
                    TextButton(onClick = { itemToDelete = null }) { Text("Cancel") }
                },
                title = { Text("Remove Item") },
                text = { Text("Remove \"${item.name}\" from your cart?") }
            )
        }
    }
}

/** individual cart row with quantity controls */
@Composable
private fun CartItemRow(
    item: CartItem,
    onIncrement: () -> Unit,
    onDecrement: () -> Unit,
    onDelete: () -> Unit
) {
    Card(
        modifier = Modifier
            .padding(8.dp)
            .fillMaxWidth()
    ) {
        Row(
            Modifier.padding(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            AsyncImage(
                model = item.imageUrl,
                contentDescription = item.name,
                modifier = Modifier
                    .size(64.dp)
                    .clip(RoundedCornerShape(8.dp))
            )

            Spacer(Modifier.width(8.dp))

            Column(Modifier.weight(1f)) {
                Text(item.name, style = MaterialTheme.typography.titleMedium)
                Text("Price: $${item.price}")
                Row(verticalAlignment = Alignment.CenterVertically) {
                    IconButton(
                        onClick = onDecrement,
                        enabled = item.quantity > 1
                    ) { Icon(Icons.Default.ArrowDropDown, contentDescription = "Decrease quantity") }

                    Text(
                        "${item.quantity}",
                        style = MaterialTheme.typography.titleMedium,
                        modifier = Modifier.width(24.dp),
                        textAlign = androidx.compose.ui.text.style.TextAlign.Center
                    )

                    IconButton(onClick = onIncrement) {
                        Icon(Icons.Default.Add, contentDescription = "Increase quantity")
                    }
                }
            }

            IconButton(onClick = onDelete) {
                Icon(Icons.Default.Delete, contentDescription = "Remove item")
            }
        }
    }
}

