package com.ndichu.kulturekart.ui.screens.seller

import android.annotation.SuppressLint
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.ndichu.kulturekart.data.ProductViewModel
import com.ndichu.kulturekart.model.Product
import com.ndichu.kulturekart.navigation.ROUTE_ADD_PRODUCT
import com.ndichu.kulturekart.navigation.ROUTE_PROFILE
import com.ndichu.kulturekart.navigation.ROUTE_SELLER_HOME
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import coil.compose.AsyncImage
import coil.compose.rememberAsyncImagePainter
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material.icons.filled.Search



@SuppressLint("StateFlowValueCalledInComposition")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SellerHomeScreen(
    navController: NavController,
    viewModel: ProductViewModel = viewModel()
) {


    val role by viewModel.userRole.collectAsState()
    val context = LocalContext.current
    val sellerProducts by viewModel.sellerProducts.collectAsState()

    // --- NEW: State for Search and Filters ---
    var searchText by remember { mutableStateOf("") }
    var selectedFilter by remember { mutableStateOf("All Products") }
    val filterOptions = listOf("All Products", "In Stock", "Out of Stock")



    LaunchedEffect(Unit) {
        viewModel.fetchUserRole()
        viewModel.loadSellerProducts()
    }



    // --- Filtered Products Calculation ---


    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Your Shop",
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
                FloatingActionButton(
                    onClick = { navController.navigate(ROUTE_ADD_PRODUCT) },
                    containerColor = MaterialTheme.colorScheme.secondaryContainer,
                    contentColor = MaterialTheme.colorScheme.onSecondaryContainer
                ) {
                    Icon(Icons.Default.Add, contentDescription = "Add Product")
                }
            }
        },
        bottomBar = {
            NavigationBar(containerColor = MaterialTheme.colorScheme.primaryContainer) { // Use primaryContainer for a distinct golden bar
                NavigationBarItem(
                    selected = true, // Set to true for the current screen
                    onClick = { navController.navigate(ROUTE_SELLER_HOME) },
                    icon = {
                        Icon(
                            imageVector = Icons.Filled.Home,
                            contentDescription = "Home",
                            tint = MaterialTheme.colorScheme.onPrimaryContainer // Golden text on primary container
                        )
                    },
                    label = { Text("Home", color = MaterialTheme.colorScheme.onPrimaryContainer) }
                )
                NavigationBarItem(
                    selected = false,
                    onClick = { navController.navigate(ROUTE_PROFILE) },
                    icon = {
                        Icon(
                            imageVector = Icons.Filled.Person,
                            contentDescription = "Profile",
                            tint = MaterialTheme.colorScheme.onPrimaryContainer // Golden text on primary container
                        )
                    },
                    label = {
                        Text(
                            "Profile",
                            color = MaterialTheme.colorScheme.onPrimaryContainer
                        )
                    }
                )
            }
        }

    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues) // Apply padding from Scaffold
        ) {
            // --- NEW: Summary Card ---
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 8.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primaryContainer),
                shape = RoundedCornerShape(12.dp),
                elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
            ) {
                Column(Modifier.padding(12.dp)) {
                    Text(
                        text = "Shop Overview",
                        style = MaterialTheme.typography.titleMedium,
                        color = MaterialTheme.colorScheme.onPrimaryContainer
                    )
                    Spacer(Modifier.height(4.dp))
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "Total Active Products: ${sellerProducts.size}",
                            style = MaterialTheme.typography.bodyLarge,
                            color = MaterialTheme.colorScheme.onPrimaryContainer
                        )
                    }
                }
            }
            // --- END NEW: Summary Card ---

            // --- NEW: Search Bar ---
            OutlinedTextField(
                value = searchText,
                onValueChange = { searchText = it },
                label = { Text("Search Products") },
                leadingIcon = {
                    Icon(
                        Icons.Default.Search,
                        contentDescription = "Search products"
                    )
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 8.dp),
                singleLine = true,
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = MaterialTheme.colorScheme.primary,
                    unfocusedBorderColor = MaterialTheme.colorScheme.outline,
                    cursorColor = MaterialTheme.colorScheme.primary,
                    focusedContainerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.1f),
                    unfocusedContainerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.1f)
                )
            )
            // --- END NEW: Search Bar ---

            // --- NEW: Filter Chips ---
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .horizontalScroll(rememberScrollState()) // Allows horizontal scrolling for many chips
                    .padding(horizontal = 16.dp, vertical = 8.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                filterOptions.forEach { filter ->
                    FilterChip(
                        selected = selectedFilter == filter,
                        onClick = { selectedFilter = filter },
                        label = { Text(filter) },
                    )
                }
            }
            // --- END NEW: Filter Chips ---

            // Main Content Area (Product Grid or Empty State)
            if (sellerProducts.isEmpty()) { // Use filteredProducts here
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .weight(1f), // Take remaining space
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Icon(
                        Icons.Default.Add,
                        contentDescription = "Empty Shop",
                        modifier = Modifier.size(64.dp),
                        tint = MaterialTheme.colorScheme.outline
                    )
                    Spacer(Modifier.height(16.dp))
                    Text(
                        "Your shop is empty!",
                        style = MaterialTheme.typography.headlineSmall,
                        color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.7f),
                        textAlign = TextAlign.Center
                    )
                    Spacer(Modifier.height(8.dp))
                    Text(
                        "It looks like you haven't added any products yet.",
                        style = MaterialTheme.typography.bodyLarge,
                        color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.6f),
                        textAlign = TextAlign.Center
                    )
                    Spacer(Modifier.height(8.dp))
                    Text(
                        "Tap the '+' button below to add your first item!",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.6f),
                        textAlign = TextAlign.Center
                    )

                }
            } else {
                LazyVerticalGrid(
                    columns = GridCells.Fixed(2),
                    modifier = Modifier
                        .fillMaxSize()
                        .weight(1f), // Take remaining space
                    contentPadding = PaddingValues(8.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    items(sellerProducts.size) { index ->
                        val product = sellerProducts[index]
                        ProductItemCard(
                            product = product,
                            onSave = { updatedProduct, imageUri ->
                                viewModel.updateProductInList(updatedProduct)
                                Toast.makeText(context, "Product updated", Toast.LENGTH_SHORT)
                                    .show()
                            },
                            onDeleteClick = {
                                val productId = product.id
                                viewModel.deleteProduct(context, productId, navController)
                            }
                        )
                    }
                }
            }
        }
    }
}


    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    fun ProductItemCard(
        product: Product,
        onDeleteClick: () -> Unit,
        onSave: (Product, Uri?) -> Unit,
        modifier: Modifier = Modifier
    ) {
        val context = LocalContext.current
        var showSheet by remember { mutableStateOf(false) }

        // Editing State
        var name by remember { mutableStateOf(product.name) }
        var price by remember { mutableStateOf(product.price) }
        var region by remember { mutableStateOf(product.region) }
        var description by remember { mutableStateOf(product.description) }
        var imageUri by remember { mutableStateOf<Uri?>(null) }


        val launcher = rememberLauncherForActivityResult(ActivityResultContracts.GetContent()) {
            imageUri = it
        }

        // Main Product Card
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .height(380.dp),
            shape = RoundedCornerShape(12.dp),
            elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
        ) {
            Column(
                modifier = Modifier.padding(8.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Image(
                    painter = rememberAsyncImagePainter(product.imageUrl),
                    contentDescription = product.name,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(120.dp)
                        .clip(RoundedCornerShape(8.dp)),
                    contentScale = ContentScale.Crop // Ensure images fill space nicely
                )


                Text(
                    text = "Name: ${product.name}",
                    style = MaterialTheme.typography.titleMedium,
                    maxLines = 1,
                    color = MaterialTheme.colorScheme.onSurface // Golden text on surface
                )
                Text(
                    text = "Region: ${product.region}",
                    style = MaterialTheme.typography.titleMedium,
                    maxLines = 1,
                    color = MaterialTheme.colorScheme.onSurface // Golden text on surface
                )

                Text(
                    text = "Price: $${product.price}",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.secondary // Golden secondary for price emphasis
                )
                Text(
                    text = "Description: ${product.description}",
                    style = MaterialTheme.typography.bodyMedium,
                    maxLines = 4,
                    color = MaterialTheme.colorScheme.onSurfaceVariant // Golden text on surface variant
                )
                Row(
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    IconButton(onClick = { showSheet = true }) {
                        Icon(
                            Icons.Default.Edit,
                            contentDescription = "Edit",
                            tint = MaterialTheme.colorScheme.primary // Golden edit icon
                        )
                    }
                    IconButton(onClick = onDeleteClick) {
                        Icon(
                            Icons.Default.Delete,
                            contentDescription = "Delete",
                            tint = MaterialTheme.colorScheme.error // Error color for delete
                        )
                    }
                }
            }
        }

        // Modal Bottom Sheet for Editing (remains unchanged)
        if (showSheet) {
            ModalBottomSheet(
                onDismissRequest = { showSheet = false },
                containerColor = MaterialTheme.colorScheme.surface,
                shape = RoundedCornerShape(topStart = 20.dp, topEnd = 20.dp)
            ) {
                Column(
                    Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    Text(
                        text = "Edit Product",
                        style = MaterialTheme.typography.titleLarge,
                        color = MaterialTheme.colorScheme.primary
                    )

                    OutlinedTextField(
                        value = name,
                        onValueChange = { name = it },
                        label = { Text("Name") },
                        modifier = Modifier.fillMaxWidth(),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = MaterialTheme.colorScheme.primary,
                            unfocusedBorderColor = MaterialTheme.colorScheme.outline,
                            cursorColor = MaterialTheme.colorScheme.primary,
                            focusedContainerColor = MaterialTheme.colorScheme.surfaceVariant.copy(
                                alpha = 0.1f
                            ),
                            unfocusedContainerColor = MaterialTheme.colorScheme.surfaceVariant.copy(
                                alpha = 0.1f
                            )
                        )
                    )
                    OutlinedTextField(
                        value = price,
                        onValueChange = { price = it },
                        label = { Text("Price") },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                        modifier = Modifier.fillMaxWidth(),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = MaterialTheme.colorScheme.primary,
                            unfocusedBorderColor = MaterialTheme.colorScheme.outline,
                            cursorColor = MaterialTheme.colorScheme.primary,
                            focusedContainerColor = MaterialTheme.colorScheme.surfaceVariant.copy(
                                alpha = 0.1f
                            ),
                            unfocusedContainerColor = MaterialTheme.colorScheme.surfaceVariant.copy(
                                alpha = 0.1f
                            )
                        )
                    )
                    OutlinedTextField(
                        value = region,
                        onValueChange = { region = it },
                        label = { Text("Region") },
                        modifier = Modifier.fillMaxWidth(),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = MaterialTheme.colorScheme.primary,
                            unfocusedBorderColor = MaterialTheme.colorScheme.outline,
                            cursorColor = MaterialTheme.colorScheme.primary,
                            focusedContainerColor = MaterialTheme.colorScheme.surfaceVariant.copy(
                                alpha = 0.1f
                            ),
                            unfocusedContainerColor = MaterialTheme.colorScheme.surfaceVariant.copy(
                                alpha = 0.1f
                            )
                        )
                    )
                    OutlinedTextField(
                        value = description,
                        onValueChange = { description = it },
                        label = { Text("Description") },
                        modifier = Modifier.fillMaxWidth(),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = MaterialTheme.colorScheme.primary,
                            unfocusedBorderColor = MaterialTheme.colorScheme.outline,
                            cursorColor = MaterialTheme.colorScheme.primary,
                            focusedContainerColor = MaterialTheme.colorScheme.surfaceVariant.copy(
                                alpha = 0.1f
                            ),
                            unfocusedContainerColor = MaterialTheme.colorScheme.surfaceVariant.copy(
                                alpha = 0.1f
                            )
                        )
                    )



                    Button(
                        onClick = { launcher.launch("image/*") },
                        modifier = Modifier.fillMaxWidth(),
                        colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.secondary)
                    ) {
                        Text("Pick Image", color = MaterialTheme.colorScheme.onSecondary)
                    }

                    imageUri?.let {
                        AsyncImage(
                            model = it,
                            contentDescription = null,
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(140.dp)
                                .clip(RoundedCornerShape(8.dp)),
                            contentScale = ContentScale.Crop
                        )
                    }

                    Button(
                        onClick = {
                            onSave(
                                product.copy(
                                    name = name,
                                    price = price,
                                    region = region,
                                    description = description,
                                    imageUrl = imageUri?.toString() ?: product.imageUrl,
                                ),
                                imageUri
                            )
                            showSheet = false
                        },
                        modifier = Modifier.fillMaxWidth(),
                        colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary)
                    ) {
                        Text("Save", color = MaterialTheme.colorScheme.onPrimary)
                    }

                    TextButton(
                        onClick = { showSheet = false },
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text("Cancel", color = MaterialTheme.colorScheme.error)
                    }
                }
            }
        }
    }


