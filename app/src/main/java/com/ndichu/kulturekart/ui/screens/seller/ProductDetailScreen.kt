//package com.ndichu.kulturekart.ui.screens.seller
//
//import android.net.Uri
//import android.widget.Toast
//import androidx.activity.compose.rememberLauncherForActivityResult
//import androidx.activity.result.contract.ActivityResultContracts
//import androidx.compose.foundation.Image
//import androidx.compose.foundation.layout.Box
//import androidx.compose.foundation.layout.Column
//import androidx.compose.foundation.layout.Spacer
//import androidx.compose.foundation.layout.fillMaxSize
//import androidx.compose.foundation.layout.fillMaxWidth
//import androidx.compose.foundation.layout.height
//import androidx.compose.foundation.layout.padding
//import androidx.compose.foundation.layout.size
//import androidx.compose.foundation.rememberScrollState
//import androidx.compose.foundation.shape.RoundedCornerShape
//import androidx.compose.foundation.text.KeyboardOptions
//import androidx.compose.foundation.verticalScroll
//import androidx.compose.material3.Button
//import androidx.compose.material3.ButtonDefaults
//import androidx.compose.material3.CircularProgressIndicator
//import androidx.compose.material3.MaterialTheme
//import androidx.compose.material3.OutlinedButton
//import androidx.compose.material3.OutlinedTextField
//import androidx.compose.material3.Scaffold
//import androidx.compose.material3.Text
//import androidx.compose.runtime.Composable
//import androidx.compose.runtime.mutableStateOf
//import androidx.compose.runtime.remember
//import androidx.compose.runtime.setValue
//import androidx.compose.ui.Alignment
//import androidx.compose.ui.Modifier
//import androidx.compose.ui.draw.clip
//import androidx.compose.ui.layout.ContentScale
//import androidx.compose.ui.platform.LocalContext
//import androidx.compose.ui.text.input.KeyboardType
//import androidx.compose.ui.unit.dp
//import androidx.lifecycle.viewmodel.compose.viewModel
//import androidx.navigation.NavController
//import coil.compose.AsyncImage
//import coil.compose.rememberAsyncImagePainter
//import androidx.compose.runtime.collectAsState
//import androidx.compose.runtime.derivedStateOf
//import androidx.compose.runtime.getValue
//import com.ndichu.kulturekart.data.ProductViewModel
//import com.ndichu.kulturekart.ui.components.BottomNavigationBar
//
//@Composable
//fun ProductDetailScreen(
//    productId: String,
//    navController: NavController,
//    viewModel: ProductViewModel = viewModel()
//) {
//    // Collect the products from the ViewModel.
//    val products by viewModel.products.collectAsState()
//    val context = LocalContext.current
//
//    // Find the product by ID.  Use derivedStateOf to ensure this is only recalculated when products changes.
//    val product by remember {
//        derivedStateOf {
//            products.find { it.id == productId }
//        }
//    }
//
//    // Local states for editing
//    var name by remember { mutableStateOf(product?.name.orEmpty()) }
//    var price by remember { mutableStateOf(product?.price?.toString().orEmpty()) } //handle null
//    var region by remember { mutableStateOf(product?.region.orEmpty()) }
//    var description by remember { mutableStateOf(product?.description.orEmpty()) }
//    var imageUri by remember { mutableStateOf<Uri?>(null) }
//
//
//    // Launcher for picking a new image
//    val launcher = rememberLauncherForActivityResult(ActivityResultContracts.GetContent()) {
//        imageUri = it
//    }
//
//    val isLoading by viewModel.isLoading.collectAsState()
//    val error by viewModel.uploadError.collectAsState()
//
//
//    if (product == null) {
//        // Product not found
//        Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
//            Text("Product not found", style = MaterialTheme.typography.bodyLarge)
//        }
//        return
//    }
//
//    Scaffold(
//        bottomBar = {
//            BottomNavigationBar(navController)  // your existing floating bar
//        }
//    ) { padding ->
//        Column(
//            modifier = Modifier
//                .fillMaxSize()
//                .padding(padding)
//                .padding(16.dp)
//                .verticalScroll(rememberScrollState())
//        ) {
//            Text("Edit Product", style = MaterialTheme.typography.headlineSmall)
//            Spacer(Modifier.height(16.dp))
//
//            // Editable fields
//            OutlinedTextField(
//                value = name,
//                onValueChange = { name = it },
//                label = { Text("Name") },
//                modifier = Modifier.fillMaxWidth()
//            )
//
//            OutlinedTextField(
//                value = price,
//                onValueChange = { price = it },
//                label = { Text("Price (USD)") },
//                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
//                modifier = Modifier.fillMaxWidth()
//            )
//
//            OutlinedTextField(
//                value = region,
//                onValueChange = { region = it },
//                label = { Text("Region") },
//                modifier = Modifier.fillMaxWidth()
//            )
//
//            OutlinedTextField(
//                value = description,
//                onValueChange = { description = it },
//                label = { Text("Description..") },
//                modifier = Modifier
//                    .fillMaxWidth()
//                    .height(120.dp)
//            )
//
//            Spacer(Modifier.height(12.dp))
//
//            // Current or newly selected image
//            Text("Product Image", style = MaterialTheme.typography.bodyMedium)
//            Spacer(Modifier.height(8.dp))
//            Box(Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
//                imageUri?.let {
//                    Image(
//                        painter = rememberAsyncImagePainter(it),
//                        contentDescription = null,
//                        modifier = Modifier
//                            .size(200.dp)
//                            .clip(RoundedCornerShape(12.dp)),
//                        contentScale = ContentScale.Crop
//                    )
//                }
//            }
//            Spacer(Modifier.height(8.dp))
//
//            Button(onClick = { launcher.launch("image/*") }, modifier = Modifier.align(Alignment.CenterHorizontally)) {
//                Text("Change Image")
//            }
//
//            Spacer(Modifier.height(24.dp))
//
//            if (isLoading) {
//                CircularProgressIndicator(modifier = Modifier.align(Alignment.CenterHorizontally))
//            } else {
//                // Save / Update
//                Button(
//                    onClick = {
//                        // Ensure price is not null
//                        val safePrice = price.toDoubleOrNull() ?: 0.0
//                        viewModel.updateproducts(
//                            context = context,
//                            navController = navController,
//                            name = name,
//                            region = region,
//                            price = safePrice.toString(),
//                            description = description,
//                            productId = product!!.id,
//                        )},
//                    modifier = Modifier.fillMaxWidth()
//                ) {
//                    Text("Save Changes")
//                }
//
//                Spacer(Modifier.height(12.dp))
//
//                // Delete
//                OutlinedButton(
//                    onClick = {
//                        viewModel.deleteProduct(context = context, productId = product!!.id, navController = navController)
//
//                    },
//                    modifier = Modifier.fillMaxWidth(),
//                    colors = ButtonDefaults.outlinedButtonColors(contentColor = MaterialTheme.colorScheme.error)
//                ) {
//                    Text("Delete Product")
//                }
//            }
//
//            error?.let {
//                Spacer(Modifier.height(8.dp))
//                Text("Error: $it", color = MaterialTheme.colorScheme.error)
//            }
//        }
//    }
//}
//
