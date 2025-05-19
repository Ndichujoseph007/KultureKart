package com.ndichu.kulturekart.ui.screens.seller

import android.annotation.SuppressLint
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.paint
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.ndichu.kulturekart.data.ProductViewModel
import com.ndichu.kulturekart.model.Product
import com.ndichu.kulturekart.navigation.ROUTE_ADD_PRODUCT
import coil.compose.rememberAsyncImagePainter
import com.ndichu.kulturekart.R
import com.ndichu.kulturekart.navigation.ROUTE_EDIT_PRODUCT
import com.ndichu.kulturekart.navigation.ROUTE_PRODUCT_LIST
import com.ndichu.kulturekart.navigation.ROUTE_PROFILE
import com.ndichu.kulturekart.navigation.ROUTE_SELLER_HOME
import com.ndichu.kulturekart.navigation.ROUTE_SELLER_PRODUCT_DETAIL


@SuppressLint("StateFlowValueCalledInComposition")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SellerHomeScreen(
    navController: NavController,
    viewModel: ProductViewModel = viewModel()
) {

    val sellerProducts by viewModel.sellerProducts.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.fetchUserRole()
        viewModel.loadSellerProducts()
    }


    val role by viewModel.userRole.collectAsState()
    val context = LocalContext.current

    val background = painterResource(id = R.drawable.background)



    // Fetch products on first composition


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
        floatingActionButton = {
            if (role == "seller") {
                FloatingActionButton(onClick = { navController.navigate(ROUTE_ADD_PRODUCT) })  {
                    Icon(Icons.Default.Add, contentDescription = "Add Product")
                }
            }


        },
        bottomBar = {
            NavigationBar(containerColor = MaterialTheme.colorScheme.primary) {
                NavigationBarItem(
                    selected = false,
                    onClick = { navController.navigate(ROUTE_SELLER_HOME) },
                    icon = {
                        Icon(
                            imageVector = Icons.Filled.Home,
                            contentDescription = "Home",
                            tint = MaterialTheme.colorScheme.onPrimary
                        )
                    },
                    label = { Text("Home", color = MaterialTheme.colorScheme.onPrimary) }
                )
                NavigationBarItem(
                    selected = false,
                    onClick = { navController.navigate(ROUTE_PROFILE) },
                    icon = {
                        Icon(
                            imageVector = Icons.Filled.Person,
                            contentDescription = "Profile",
                            tint = MaterialTheme.colorScheme.onPrimary
                        )
                    },
                    label = { Text("Profile", color = MaterialTheme.colorScheme.onPrimary) }
                )
            }
        }

    ) { paddingValues ->
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .paint(
                        painter = background,
                        contentScale = ContentScale.Crop
                    )
                    .padding(paddingValues),
                contentAlignment = Alignment.Center
            ) {
            LazyColumn(verticalArrangement = Arrangement.spacedBy(12.dp)) {

                items(sellerProducts) { product ->
                    ProductItemCard(
                        product = product,
                        onEditClick = {
                            navController.navigate("$ROUTE_EDIT_PRODUCT/${product.id}")
                        },
                        onDeleteClick = {
                            val productId = product.id
                            viewModel.deleteProduct(context,productId,navController)
                        }

                    )
                }

            }
        }


    }

}

@Composable
fun ProductItemCard(
    product: Product,
    onEditClick: () -> Unit,
    onDeleteClick: () -> Unit
) {
    val context = LocalContext.current

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 8.dp, vertical = 4.dp),
        shape = MaterialTheme.shapes.medium,
        elevation = CardDefaults.cardElevation(defaultElevation = 6.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row {
                Image(
                    painter = rememberAsyncImagePainter(product.imageUrl),
                    contentDescription = product.name,
                    modifier = Modifier
                        .size(80.dp)
                        .padding(end = 16.dp)
                )
                Column(modifier = Modifier.align(Alignment.CenterVertically)) {
                    Text(text = product.name, style = MaterialTheme.typography.titleMedium)
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

            Spacer(modifier = Modifier.height(12.dp))

            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                Button(
                    onClick = onEditClick,
                    modifier = Modifier.weight(1f),
                ) {
                    Icon(
                        imageVector = Icons.Filled.Edit,
                        contentDescription = "Edit",
                        modifier = Modifier.weight(0.2f)
                    )
                    Text(
                        "Edit",
                        modifier = Modifier.weight(0.8f)
                    )
                }
                Button(
                    onClick = onDeleteClick,
                    colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.error),
                    modifier = Modifier.weight(1f)
                ) {
                    Icon(
                        imageVector = Icons.Filled.Delete,
                        contentDescription = "Delete",
                        modifier = Modifier.weight(0.2f)
                    )
                    Text(
                        "Delete",
                        modifier = Modifier.weight(0.8f)
                    )
                }
            }
        }
    }
}
