package com.ndichu.kulturekart.ui.screens.seller


import android.net.Uri
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.ndichu.kulturekart.data.ProductViewModel
import com.ndichu.kulturekart.model.Product
import coil.compose.rememberAsyncImagePainter


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditProductScreen(
    productId: String,
    navController: NavController,
    viewModel: ProductViewModel = viewModel()
) {
    val context = LocalContext.current
    var product by remember { mutableStateOf<Product?>(null) }

    var name by remember { mutableStateOf("") }
    var price by remember { mutableStateOf("") }
    var region by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }
    var imageUri by remember { mutableStateOf<Uri?>(null) }
    var isUploading by remember { mutableStateOf(false) }

    // Validation states
    var nameError by remember { mutableStateOf(false) }
    var priceError by remember { mutableStateOf(false) }
    var regionError by remember { mutableStateOf(false) }
    var descriptionError by remember { mutableStateOf(false) }

    val imagePickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri ->
        imageUri = uri
    }

    LaunchedEffect(productId) {
        viewModel.getProductById(productId) {
            product = it
            it?.let { p ->
                name = p.name
                price = p.price.toString()
                region = p.region
                description = p.description
            }
        }
    }

    product?.let { p ->
        Scaffold(
            topBar = {
                TopAppBar(title = { Text("Edit Product") })
            }
        ) { padding ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding)
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                OutlinedTextField(
                    value = name,
                    onValueChange = {
                        name = it
                        nameError = it.isBlank()
                    },
                    label = { Text("Product Name") },
                    isError = nameError,
                    modifier = Modifier.fillMaxWidth()
                )
                if (nameError) Text("Name cannot be empty", color = MaterialTheme.colorScheme.error)

                OutlinedTextField(
                    value = price,
                    onValueChange = {
                        price = it
                        priceError = it.toDoubleOrNull() == null || it.toDouble() <= 0.0
                    },
                    label = { Text("Price") },
                    isError = priceError,
                    modifier = Modifier.fillMaxWidth()
                )
                if (priceError) Text("Enter a valid price", color = MaterialTheme.colorScheme.error)

                OutlinedTextField(
                    value = region,
                    onValueChange = {
                        region = it
                        regionError = it.isBlank()
                    },
                    label = { Text("Region") },
                    isError = regionError,
                    modifier = Modifier.fillMaxWidth()
                )
                if (regionError) Text("Region cannot be empty", color = MaterialTheme.colorScheme.error)

                OutlinedTextField(
                    value = description,
                    onValueChange = {
                        description = it
                        descriptionError = it.isBlank()
                    },
                    label = { Text("Description") },
                    isError = descriptionError,
                    modifier = Modifier.fillMaxWidth()
                )
                if (descriptionError) Text("Description cannot be empty", color = MaterialTheme.colorScheme.error)

                Button(
                    onClick = { imagePickerLauncher.launch("image/*") },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Icon(Icons.Default.AddCircle, contentDescription = "Upload Image")
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("Choose Image")
                }

                if (imageUri != null) {
                    Image(
                        painter = rememberAsyncImagePainter(imageUri),
                        contentDescription = null,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(200.dp)
                    )
                } else {
                    Image(
                        painter = rememberAsyncImagePainter(p.imageUrl),
                        contentDescription = null,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(200.dp)
                    )
                }

                val isFormValid = name.isNotBlank()
                        && price.toDoubleOrNull() != null
                        && price.toDouble() > 0.0
                        && region.isNotBlank()
                        && description.isNotBlank()

//                Button(
//                    onClick = {
//                        if (!isFormValid) {
//                            nameError = name.isBlank()
//                            priceError = price.toDoubleOrNull() == null || price.toDouble() <= 0.0
//                            regionError = region.isBlank()
//                            descriptionError = description.isBlank()
//                            return@Button
//                        }
//
//                        isUploading = true
//                        viewModel.updateProductWithImage(
//                            context = context,
//                            productId = productId,
//                            name = name,
//                            price = price.toDouble(),
//                            region = region,
//                            description = description,
//                            newImageUri = imageUri
//                        ) { success ->
//                            isUploading = false
//                            if (success) {
//                                Toast.makeText(context, "Updated successfully", Toast.LENGTH_SHORT).show()
//                                navController.popBackStack()
//                            } else {
//                                Toast.makeText(context, "Update failed", Toast.LENGTH_SHORT).show()
//                            }
//                        }
//                    },
//                    modifier = Modifier.fillMaxWidth(),
//                    enabled = !isUploading
//                ) {
//                    Text("Save Changes")
//                }
                Button(
                    onClick = {
                        viewModel.updateProductWithImage(
                            context = context,
                            navController = navController,
                            productId = productId,
                            name = name,
                            price = price,
                            region = region,
                            description = description,
                            newImageUri = imageUri, // ✅ correct param name
                            onComplete = { success ->
                                if (success) {
                                    Toast.makeText(context, "Edit Successful", Toast.LENGTH_SHORT).show()
                                    navController.popBackStack()
                                } else {
                                    Toast.makeText(context, "Failed", Toast.LENGTH_SHORT).show()
                                }
                            }
                        )
                    },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Update Product")
                }

                if (isUploading) {
                    LinearProgressIndicator(modifier = Modifier.fillMaxWidth())
                }
            }
        }
    } ?: run {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            CircularProgressIndicator()
        }
    }
}
