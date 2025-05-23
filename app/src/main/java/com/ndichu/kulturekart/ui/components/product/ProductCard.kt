package com.ndichu.kulturekart.ui.components.product

import android.content.Context
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.ndichu.kulturekart.model.Product
import coil.compose.rememberAsyncImagePainter
import com.ndichu.kulturekart.data.CartViewModel
import com.ndichu.kulturekart.navigation.ROUTE_CART


@Composable
fun ProductCard(
    navController: NavController,
    product: Product,
    onClick: () -> Unit,

) {

    val context = LocalContext.current
    Card(
        onClick = onClick,
        modifier = Modifier
            .fillMaxWidth(),
    ) {
        Row(modifier = Modifier.padding(16.dp)) {
            Image(
                painter = rememberAsyncImagePainter(product.imageUrl),
                contentDescription = product.name,
                modifier = Modifier
                    .size(140.dp)
                    .padding(end = 16.dp)
            )
            Column {
                Text(
                    text = "Name:${product.name}"
                    ,style = MaterialTheme.typography.titleMedium
                )
                Text(
                    text = "Price: $${product.price}",
                    style = MaterialTheme.typography.bodyMedium
                )
                Text(
                    text = "Region: ${product.region}",
                    style = MaterialTheme.typography.bodySmall
                )
                Text(
                    text = "Description: ${product.description}",
                    style = MaterialTheme.typography.bodySmall
                )
            }
        }
        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {

            val viewModel: CartViewModel = viewModel()
            Button(
                onClick = {
                    viewModel.addToCart(product,context=context)
                    navController.navigate(ROUTE_CART)
                }
            ) {
                Icon(
                    imageVector = Icons.Filled.ShoppingCart,
                    contentDescription = "Cart",
                    modifier = Modifier.weight(0.2f)
                )
                Text("Add to Cart")
            }

        }
    }
}
