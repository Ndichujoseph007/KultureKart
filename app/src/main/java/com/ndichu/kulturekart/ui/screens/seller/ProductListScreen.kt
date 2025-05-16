package com.ndichu.kulturekart.ui.screens.seller


import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.ndichu.kulturekart.data.ProductViewModel
import com.ndichu.kulturekart.model.Product
import androidx.compose.ui.Alignment
import androidx.compose.ui.platform.LocalContext

import androidx.compose.ui.text.input.KeyboardType
import com.google.firebase.auth.FirebaseAuth
import com.ndichu.kulturekart.navigation.ROUTE_ADD_PRODUCT
import com.ndichu.kulturekart.navigation.ROUTE_SELLER_PRODUCT_DETAIL
import com.ndichu.kulturekart.ui.components.product.ProductCard

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProductListScreen(
    navController: NavController,
    viewModel: ProductViewModel = viewModel()
) {
    LaunchedEffect(Unit) {
        viewModel.fetchProducts()
            viewModel.fetchUserRole()

    }
    // Collect the products from the ViewModel.  Use remember to prevent re-fetching
    val products by remember { viewModel.products }.collectAsState()
    val context = LocalContext.current


    val userRole by viewModel.userRole.collectAsState()





    // State to hold the product details for editing.
    val (editingProduct, setEditingProduct) = remember { mutableStateOf<Product?>(null) }
    var name by remember { mutableStateOf("") }
    var price by remember { mutableStateOf("") }
    var region by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }
    val currentUserId = FirebaseAuth.getInstance().currentUser?.uid

    val isLoading by viewModel.isLoading.collectAsState()
    val error by viewModel.uploadError.collectAsState()
    val sellerProducts = remember(products) {
        products.filter { it.sellerId == currentUserId }
    }

    // Function to show a dialog for editing a product
    fun showEditDialog(product: Product) {
        setEditingProduct(product)
        name = product.name
        price = product.price
        region = product.region
        description = product.description
    }


    // Function to handle the update
    fun handleUpdate(navController: NavController) {
        editingProduct?.let {  // Use let for null check
            viewModel.updateproducts(
                context = context,
                navController = navController,
                name = name,
                region = region,
                price = price,
                description = description,
                productId = it.id // Use it.id
            )
            setEditingProduct(null) // Close the dialog
        }
    }

    // Show loading indicator
    if (isLoading) {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            CircularProgressIndicator()
        }
        return // Don't show the list if loading
    }

    // Show error message
    if (error != null) {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            Text("Error: $error")
        }
        return
    }

    // Main UI: Display the list of products
    Column(modifier = Modifier.fillMaxSize()) {
        Scaffold(
            topBar = {
                TopAppBar(title = { Text("Products") }, actions = {
                    Button(onClick = { navController.navigate(ROUTE_ADD_PRODUCT) }) {
                        Text("Add Product")
                    }
                })
            }
        ) { paddingValues ->

            Column(
                modifier = Modifier
                .fillMaxSize()
                    .padding(paddingValues)
                .padding(16.dp)) {

                Text(
                    text = "All Products",
                    style = MaterialTheme.typography.headlineSmall,
                    modifier = Modifier.padding(bottom = 12.dp)
                )

                if (isLoading) {
                    CircularProgressIndicator()
                } else {
                    LazyColumn(
                        verticalArrangement = Arrangement.spacedBy(12.dp),
                        modifier = Modifier.fillMaxSize()

                    ) {
                        items(sellerProducts) { product ->
                            ProductCard1(
                                product = product,
                                onClick = {
                                    navController.navigate("productDetail/${product.id}")
                                },
                                onEdit = if (userRole == "seller") {
                                    { showEditDialog(product) }
                                } else null,
                                onDelete = if (userRole == "seller") {
                                    { viewModel.deleteProduct(   context = context,
                                        productId = product.id,
                                        navController = navController) }
                                } else null
                            )
                        }
                        }
                    }

                }
        }

    }

    // Edit Product Dialog.
    if (editingProduct != null) {
        AlertDialog(
            onDismissRequest = { setEditingProduct(null) },
            title = { Text("Edit Product") },
            text = {
                Column {
                    OutlinedTextField(
                        value = name,
                        onValueChange = { name = it },
                        label = { Text("Product Name") },
                        modifier = Modifier.fillMaxWidth(),
                        singleLine = true
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    OutlinedTextField(
                        value = price,
                        onValueChange = { price = it },
                        label = { Text("Price") },
                        modifier = Modifier.fillMaxWidth(),
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                        singleLine = true
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    OutlinedTextField(
                        value = region,
                        onValueChange = { region = it },
                        label = { Text("Region") },
                        modifier = Modifier.fillMaxWidth(),
                        singleLine = true
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    OutlinedTextField(
                        value = description,
                        onValueChange = { description = it },
                        label = { Text("Description") },
                        modifier = Modifier.fillMaxWidth(),
                        singleLine = true
                    )
                }
            },
            confirmButton = {
                Button(onClick = { handleUpdate(navController) }) {
                    Text("Update")
                }
            },
            dismissButton = {
                Button(onClick = { setEditingProduct(null) }) {
                    Text("Cancel")
                }
            }
        )
    }
}

@Composable
fun ProductCard1(
    product: Product,
    onClick: () -> Unit = {},
    onEdit: (() -> Unit)? = null,
    onDelete: (() -> Unit)? = null
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(4.dp),
        elevation = CardDefaults.elevatedCardElevation()
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(text = product.name, style = MaterialTheme.typography.headlineSmall)
            Spacer(modifier = Modifier.height(4.dp))
            Text(text = "Price: ${product.price}", style = MaterialTheme.typography.bodyLarge)
            Spacer(modifier = Modifier.height(4.dp))
            Text(text = "Region: ${product.region}", style = MaterialTheme.typography.bodyMedium)
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = product.description, style = MaterialTheme.typography.bodySmall)
            Spacer(modifier = Modifier.height(8.dp))

            if (onEdit != null && onDelete != null) {
                Row(
                    horizontalArrangement = Arrangement.End,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Button(
                        onClick = onEdit,
                        modifier = Modifier.padding(end = 8.dp)
                    ) {
                        Text("Edit")
                    }
                    Button(
                        onClick = onDelete,
                        colors = ButtonDefaults.buttonColors(
                            containerColor = MaterialTheme.colorScheme.error
                        )
                    ) {
                        Text("Delete")
                    }
                }
            }
        }
    }
}


