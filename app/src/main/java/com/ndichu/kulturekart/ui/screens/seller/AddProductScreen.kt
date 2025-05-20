package com.ndichu.kulturekart.ui.screens.seller


import android.R.attr.navigationIcon
import android.net.Uri
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.paint
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import coil.compose.rememberAsyncImagePainter
import com.ndichu.kulturekart.R
import com.ndichu.kulturekart.data.ProductViewModel // Import your ProductViewModel
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
    var region by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }

    val isLoading by viewModel.isLoading.collectAsState()
    val error by viewModel.uploadError.collectAsState()
    val background = painterResource(id = R.drawable.background)
    val GoldDark = Color(0xFFB8860B) // Dark goldenrod hex color

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {


        Column(
            modifier = Modifier
                .padding(16.dp)
                .verticalScroll(rememberScrollState())
        ) {
            Box(modifier = Modifier.fillMaxWidth()) {
                IconButton(
                    onClick = { navController.popBackStack() },
                    modifier = Modifier.align(Alignment.CenterStart)
                ) {
                    Icon(Icons.Filled.ArrowBack, contentDescription = "Back")
                }
            }

            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(
                        text = "Add New Product",
                        style = MaterialTheme.typography.headlineMedium,
                        color = MaterialTheme.colorScheme.primary
                    )


                    OutlinedTextField(
                        value = name,
                        onValueChange = { name = it },
                        label = { Text("Product Name") },
                        modifier = Modifier.fillMaxWidth(),
                        singleLine = true
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    OutlinedTextField(
                        value = price,
                        onValueChange = { price = it },
                        label = { Text("Price (USD)") },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                        modifier = Modifier.fillMaxWidth(),
                        singleLine = true
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    OutlinedTextField(
                        value = region,
                        onValueChange = { region = it },
                        label = { Text("Region") },
                        modifier = Modifier.fillMaxWidth(),
                        singleLine = true
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    OutlinedTextField(
                        value = description,
                        onValueChange = { description = it },
                        label = { Text("Description") },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(120.dp)
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    Button(
                        onClick = { launcher.launch("image/*") },
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(12.dp)
                    ) {
                        Text("Select Image")
                    }
                    imageUri?.let {
                        AsyncImage(
                            model = it,
                            contentDescription = null,
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(100.dp)
                        )
                    }
                    Spacer(modifier = Modifier.height(20.dp))
                    Text(
                        text = "Your Craft deserve a Golden stage-Start sharing",
                        style = MaterialTheme.typography.titleLarge,
                        color = MaterialTheme.colorScheme.onPrimary
                    )

                    if (isLoading) {
                        CircularProgressIndicator(modifier = Modifier.align(Alignment.CenterHorizontally))
                    } else {
                        Button(
                            onClick = {
                                if (name.isNotBlank() && price.isNotBlank() && region.isNotBlank() && description.isNotBlank() && imageUri.value != null) {
                                    viewModel.uploadProductWithImage( // Use the correct function from ViewModel
                                        uri = imageUri.value!!,
                                        context = context,
                                        name = name,
                                        region = region,
                                        description = description,
                                        price = price,
                                        navController = navController
                                    )
                                } else {
                                    Toast.makeText(context, "Please fill all fields and select an image", Toast.LENGTH_SHORT).show()
                                }
                            },
                            modifier = Modifier.fillMaxWidth(),
                            shape = RoundedCornerShape(12.dp)
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
        }
    }
}



