package com.ndichu.kulturekart.ui.screens.buyer

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.ndichu.kulturekart.model.Product

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CartScreen(navController: NavController) {
    val userId = FirebaseAuth.getInstance().currentUser?.uid ?: return
    val cartRef = FirebaseDatabase.getInstance().reference.child("cart").child(userId)

    var cartItems by remember { mutableStateOf<List<Product>>(emptyList()) }

    DisposableEffect(Unit) {
        val listener = object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val items = mutableListOf<Product>()
                for (snap in snapshot.children) {
                    snap.getValue(Product::class.java)?.let { items.add(it) }
                }
                cartItems = items
            }

            override fun onCancelled(error: DatabaseError) {}
        }
        cartRef.addValueEventListener(listener)

        onDispose {
            cartRef.removeEventListener(listener)
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(title = { Text("My Cart") })
        }
    ) { padding ->
        if (cartItems.isEmpty()) {
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding)
            ) {
                Text("Your cart is empty.")
            }
        } else {
            LazyColumn(modifier = Modifier.padding(padding)) {
                items(cartItems) { product ->
                    CartItem(product = product) {
                        // Remove from cart
                        FirebaseDatabase.getInstance().reference
                            .child("cart")
                            .child(userId)
                            .child(product.id)
                            .removeValue()
                    }
                }
            }
        }
    }
}


@Composable
fun CartItem(product: Product, onRemove: () -> Unit) {
    Card(
        modifier = Modifier
            .padding(8.dp)
            .fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Image(
                painter = rememberAsyncImagePainter(product.imageUrl),
                contentDescription = product.name,
                modifier = Modifier
                    .size(100.dp)
                    .padding(8.dp),
                contentScale = ContentScale.Crop
            )
            Column(modifier = Modifier.weight(1f)) {
                Text(text = product.name, style = MaterialTheme.typography.titleMedium)
                Text(text = "Price: \$${product.price}", style = MaterialTheme.typography.bodyMedium)
            }
            Button(
                onClick = onRemove,
                modifier = Modifier.padding(8.dp)
            ) {
                Text("Remove")
            }
        }
    }
}
