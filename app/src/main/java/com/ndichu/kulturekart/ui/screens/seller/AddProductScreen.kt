//package com.ndichu.kulturekart.ui.screens.seller
//
//
//import android.R.attr.navigationIcon
//import android.net.Uri
//import android.widget.Toast
//import androidx.activity.compose.rememberLauncherForActivityResult
//import androidx.activity.result.contract.ActivityResultContracts
//import androidx.compose.foundation.Image
//import androidx.compose.foundation.layout.*
//import androidx.compose.foundation.rememberScrollState
//import androidx.compose.foundation.shape.RoundedCornerShape
//import androidx.compose.foundation.text.KeyboardOptions
//import androidx.compose.foundation.verticalScroll
//import androidx.compose.material.icons.Icons
//import androidx.compose.material.icons.filled.ArrowBack
//import androidx.compose.material3.*
//import androidx.compose.runtime.*
//import androidx.compose.ui.Alignment
//import androidx.compose.ui.Modifier
//import androidx.compose.ui.draw.clip
//import androidx.compose.ui.draw.paint
//import androidx.compose.ui.graphics.Color
//import androidx.compose.ui.layout.ContentScale
//import androidx.compose.ui.platform.LocalContext
//import androidx.compose.ui.res.painterResource
//import androidx.compose.ui.text.input.KeyboardType
//import androidx.compose.ui.unit.dp
//import androidx.lifecycle.viewmodel.compose.viewModel
//import androidx.navigation.NavController
//import coil.compose.AsyncImage
//import coil.compose.rememberAsyncImagePainter
//import com.ndichu.kulturekart.R
//import com.ndichu.kulturekart.data.ProductViewModel // Import your ProductViewModel
//@Composable
//fun AddProductScreen(
//    navController: NavController,
//    viewModel: ProductViewModel = viewModel()
//) {
//    val context = LocalContext.current
//    val imageUri = remember { mutableStateOf<Uri?>(null) }
//    val launcher = rememberLauncherForActivityResult(ActivityResultContracts.GetContent()) { uri ->
//        imageUri.value = uri
//    }
//
//    var name by remember { mutableStateOf("") }
//    var price by remember { mutableStateOf("") }
//    var region by remember { mutableStateOf("") }
//    var description by remember { mutableStateOf("") }
//
//    val isLoading by viewModel.isLoading.collectAsState()
//    val error by viewModel.uploadError.collectAsState()
//    val background = painterResource(id = R.drawable.background)
//    val GoldDark = Color(0xFFB8860B) // Dark goldenrod hex color
//
//    Surface(
//        modifier = Modifier.fillMaxSize(),
//        color = MaterialTheme.colorScheme.background
//    ) {
//
//
//        Column(
//            modifier = Modifier
//                .padding(16.dp)
//                .verticalScroll(rememberScrollState())
//        ) {
//            Box(modifier = Modifier.fillMaxWidth()) {
//                IconButton(
//                    onClick = { navController.popBackStack() },
//                    modifier = Modifier.align(Alignment.CenterStart)
//                ) {
//                    Icon(Icons.Filled.ArrowBack, contentDescription = "Back")
//                }
//            }
//
//            Card(
//                modifier = Modifier.fillMaxWidth(),
//                shape = RoundedCornerShape(16.dp),
//                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
//                elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
//            ) {
//                Column(modifier = Modifier.padding(16.dp)) {
//                    Text(
//                        text = "Add New Product",
//                        style = MaterialTheme.typography.headlineMedium,
//                        color = MaterialTheme.colorScheme.primary
//                    )
//
//
//                    OutlinedTextField(
//                        value = name,
//                        onValueChange = { name = it },
//                        label = { Text("Product Name") },
//                        modifier = Modifier.fillMaxWidth(),
//                        singleLine = true
//                    )
//
//                    Spacer(modifier = Modifier.height(12.dp))
//
//                    OutlinedTextField(
//                        value = price,
//                        onValueChange = { price = it },
//                        label = { Text("Price (USD)") },
//                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
//                        modifier = Modifier.fillMaxWidth(),
//                        singleLine = true
//                    )
//
//                    Spacer(modifier = Modifier.height(12.dp))
//
//                    OutlinedTextField(
//                        value = region,
//                        onValueChange = { region = it },
//                        label = { Text("Region") },
//                        modifier = Modifier.fillMaxWidth(),
//                        singleLine = true
//                    )
//
//                    Spacer(modifier = Modifier.height(12.dp))
//
//                    OutlinedTextField(
//                        value = description,
//                        onValueChange = { description = it },
//                        label = { Text("Description") },
//                        modifier = Modifier
//                            .fillMaxWidth()
//                            .height(120.dp)
//                    )
//
//                    Spacer(modifier = Modifier.height(16.dp))
//
//                    Button(
//                        onClick = { launcher.launch("image/*") },
//                        modifier = Modifier.fillMaxWidth(),
//                        shape = RoundedCornerShape(12.dp)
//                    ) {
//                        Text("Select Image")
//                    }
//
//                    Text(
//                        text = "Your Craft deserve a Golden stage-Start sharing",
//                        style = MaterialTheme.typography.titleLarge,
//                        color = MaterialTheme.colorScheme.onPrimary
//                    )
//
//                    if (isLoading) {
//                        CircularProgressIndicator(modifier = Modifier.align(Alignment.CenterHorizontally))
//                    } else {
//                        Button(
//                            onClick = {
//                                if (name.isNotBlank() && price.isNotBlank() && region.isNotBlank() && description.isNotBlank() && imageUri.value != null) {
//                                    viewModel.uploadProductWithImage( // Use the correct function from ViewModel
//                                        uri = imageUri.value!!,
//                                        context = context,
//                                        name = name,
//                                        region = region,
//                                        description = description,
//                                        price = price,
//                                        navController = navController
//                                    )
//                                } else {
//                                    Toast.makeText(context, "Please fill all fields and select an image", Toast.LENGTH_SHORT).show()
//                                }
//                            },
//                            modifier = Modifier.fillMaxWidth(),
//                            shape = RoundedCornerShape(12.dp)
//                        ) {
//                            Text("Upload Product")
//                        }
//                    }
//
//                    if (error != null) {
//                        Spacer(modifier = Modifier.height(8.dp))
//                        Text(
//                            text = "Error: $error",
//                            color = MaterialTheme.colorScheme.error,
//                            style = MaterialTheme.typography.bodyMedium
//                        )
//                    }
//                }
//            }
//        }
//    }
//}
//
//
//



package com.ndichu.kulturekart.ui.screens.seller

import android.net.Uri
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
// For dropdown icon
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.ndichu.kulturekart.data.ProductViewModel

@OptIn(ExperimentalMaterial3Api::class) // Required for ExposedDropdownMenuBox
@Composable
fun AddProductScreen(
    navController: NavController,
    viewModel: ProductViewModel = viewModel()
) {
    val context = LocalContext.current
    val imageUri = remember { mutableStateOf<Uri?>(null) }
    val launcher = rememberLauncherForActivityResult(ActivityResultContracts.GetContent()) { uri ->
        imageUri.value = uri
    }

    var name by remember { mutableStateOf("") }
    var price by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }

    // --- New State for Region Dropdown ---
    val categories = remember {
        listOf("West Africa", "East Africa", "Central Africa", "North Africa", "South Africa", "Others")
    }
    var selectedRegion by remember { mutableStateOf(categories.first()) } // Default to first category
    var expanded by remember { mutableStateOf(false) } // State for dropdown menu expansion
    // --- End New State ---

    val isLoading by viewModel.isLoading.collectAsState()
    val error by viewModel.uploadError.collectAsState()
    var stockQuantity by remember { mutableStateOf("1") } // Default to "1" for new products


    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background // Use MaterialTheme background
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp)
                .verticalScroll(rememberScrollState())
        ) {
            // Back Button
            Box(modifier = Modifier.fillMaxWidth()) {
                IconButton(
                    onClick = { navController.popBackStack() },
                    modifier = Modifier.align(Alignment.CenterStart)
                ) {
                    Icon(
                        Icons.Filled.ArrowBack,
                        contentDescription = "Back",
                        tint = MaterialTheme.colorScheme.onBackground // Consistent icon color
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp)) // Space between back button and card

            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface), // Use surface for cards
                elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
            ) {
                Column(
                    modifier = Modifier
                        .padding(16.dp)
                        .fillMaxWidth() // Ensure column fills card width
                ) {
                    Text(
                        text = "Add New Product",
                        style = MaterialTheme.typography.headlineMedium,
                        color = MaterialTheme.colorScheme.primary // Golden color for title
                    )

                    Spacer(modifier = Modifier.height(16.dp)) // Space after title

                    OutlinedTextField(
                        value = name,
                        onValueChange = { name = it },
                        label = { Text("Product Name") },
                        modifier = Modifier.fillMaxWidth(),
                        singleLine = true,
                        colors = OutlinedTextFieldDefaults.colors( // Consistent Text Field Styling
                            focusedBorderColor = MaterialTheme.colorScheme.primary,
                            unfocusedBorderColor = MaterialTheme.colorScheme.outline,
                            cursorColor = MaterialTheme.colorScheme.primary,
                            focusedContainerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.1f),
                            unfocusedContainerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.1f)
                        )
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    OutlinedTextField(
                        value = price,
                        onValueChange = { price = it },
                        label = { Text("Price (USD)") },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                        modifier = Modifier.fillMaxWidth(),
                        singleLine = true,
                        colors = OutlinedTextFieldDefaults.colors( // Consistent Text Field Styling
                            focusedBorderColor = MaterialTheme.colorScheme.primary,
                            unfocusedBorderColor = MaterialTheme.colorScheme.outline,
                            cursorColor = MaterialTheme.colorScheme.primary,
                            focusedContainerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.1f),
                            unfocusedContainerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.1f)
                        )
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    // --- Region Dropdown Implementation ---
                    ExposedDropdownMenuBox(
                        expanded = expanded,
                        onExpandedChange = { expanded = !expanded },
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        OutlinedTextField(
                            value = selectedRegion,
                            onValueChange = { /* Do nothing, value is controlled by dropdown */ },
                            readOnly = true,
                            label = { Text("Region") },
                            trailingIcon = {
                                ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded)
                            },
                            modifier = Modifier
                                .menuAnchor() // This is crucial for the dropdown to anchor
                                .fillMaxWidth(),
                            colors = OutlinedTextFieldDefaults.colors( // Consistent Text Field Styling
                                focusedBorderColor = MaterialTheme.colorScheme.primary,
                                unfocusedBorderColor = MaterialTheme.colorScheme.outline,
                                cursorColor = MaterialTheme.colorScheme.primary,
                                focusedContainerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.1f),
                                unfocusedContainerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.1f)
                            )
                        )

                        ExposedDropdownMenu(
                            expanded = expanded,
                            onDismissRequest = { expanded = false },
                        ) {
                            categories.forEach { category ->
                                DropdownMenuItem(
                                    text = { Text(category) },
                                    onClick = {
                                        selectedRegion = category
                                        expanded = false
                                    },
                                    colors = MenuDefaults.itemColors(
                                        textColor = MaterialTheme.colorScheme.onSurface
                                    )
                                )
                            }
                        }
                    }
                    // --- End Region Dropdown Implementation ---

                    Spacer(modifier = Modifier.height(12.dp))

                    OutlinedTextField(
                        value = description,
                        onValueChange = { description = it },
                        label = { Text("Description") },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(120.dp),
                        colors = OutlinedTextFieldDefaults.colors( // Consistent Text Field Styling
                            focusedBorderColor = MaterialTheme.colorScheme.primary,
                            unfocusedBorderColor = MaterialTheme.colorScheme.outline,
                            cursorColor = MaterialTheme.colorScheme.primary,
                            focusedContainerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.1f),
                            unfocusedContainerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.1f)
                        )
                    )



                    Spacer(modifier = Modifier.height(16.dp))

                    // Image Selection Section
                    Button(
                        onClick = { launcher.launch("image/*") },
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(12.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = MaterialTheme.colorScheme.secondary, // Use secondary for image selection
                            contentColor = MaterialTheme.colorScheme.onSecondary
                        )
                    ) {
                        Text("Select Image")
                    }

                    Spacer(modifier = Modifier.height(8.dp))

                    // Display selected image thumbnail
                    imageUri.value?.let { uri ->
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(150.dp)
                                .clip(RoundedCornerShape(8.dp))
                                .background(MaterialTheme.colorScheme.surfaceVariant) // Muted background for image preview
                                .clickable { launcher.launch("image/*") }, // Allow re-selecting image
                            contentAlignment = Alignment.Center
                        ) {
                            AsyncImage(
                                model = uri,
                                contentDescription = "Selected Product Image",
                                modifier = Modifier.fillMaxSize(),
                                contentScale = ContentScale.Fit // Fit the image within the box
                            )
                        }
                        Spacer(modifier = Modifier.height(8.dp))
                    }


                    Text(
                        text = "Your Craft deserves a Golden Stage - Start Sharing!",
                        style = MaterialTheme.typography.titleMedium,
                        color = MaterialTheme.colorScheme.onSurface,
                        modifier = Modifier.padding(vertical = 16.dp)
                    )

                    // Upload Product Button
                    if (isLoading) {
                        CircularProgressIndicator(
                            modifier = Modifier.align(Alignment.CenterHorizontally),
                            color = MaterialTheme.colorScheme.primary // Golden progress indicator
                        )
                    } else {
                        Button(
                            onClick = {
                                if (name.isNotBlank() && price.isNotBlank() && description.isNotBlank()  && imageUri.value != null) {
                                    viewModel.uploadProductWithImage(
                                        uri = imageUri.value!!,
                                        context = context,
                                        name = name,
                                        region = selectedRegion,
                                        description = description,
                                        price = price,
                                        navController = navController
                                    )
                                } else {
                                    Toast.makeText(context, "Please fill all fields and select an image", Toast.LENGTH_SHORT).show()
                                }
                            },
                            modifier = Modifier.fillMaxWidth(),
                            shape = RoundedCornerShape(12.dp),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = MaterialTheme.colorScheme.primary, // Golden primary for main action
                                contentColor = MaterialTheme.colorScheme.onPrimary
                            )
                        ) {
                            Text("Upload Product")
                        }
                    }

                    if (error != null) {
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = "Error: $error",
                            color = MaterialTheme.colorScheme.error,
                            style = MaterialTheme.typography.bodyMedium
                        )
                    }
                }
            }
            Spacer(modifier = Modifier.height(24.dp)) // Extra space at bottom
        }
    }
}