package com.ndichu.kulturekart.data

import android.app.AlertDialog
import android.content.Context
import android.net.Uri
import android.widget.Toast
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.ndichu.kulturekart.model.Product
import com.ndichu.kulturekart.navigation.ROUTE_PRODUCT_LIST
import com.ndichu.kulturekart.network.ImgurService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.OkHttpClient
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.File

class ProductViewModel : ViewModel() {
    private val database = FirebaseDatabase.getInstance().reference.child("Products")

    private fun getImgurService(): ImgurService {
        val logging = HttpLoggingInterceptor()
        logging.level = HttpLoggingInterceptor.Level.BODY

        val client = OkHttpClient.Builder()
            .addInterceptor(logging)
            .build()

        val retrofit = Retrofit.Builder()
            .baseUrl("https://api.imgur.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()

        return retrofit.create(ImgurService::class.java)
    }
    fun getProductById(id: String): Product? {
        return _products.value.find { it.id == id }
    }


    private fun getFileFromUri(context: Context, uri: Uri): File? {
        return try {
            val inputStream = context.contentResolver.openInputStream(uri)
            val file = File.createTempFile("temp_image", ".jpg", context.cacheDir)
            file.outputStream().use { output ->
                inputStream?.copyTo(output)
            }
            file
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }
    private val _products = MutableStateFlow<List<Product>>(emptyList())  // Initial value
    val products: StateFlow<List<Product>> = _products

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    private val _uploadError = MutableStateFlow<String?>(null)
    val uploadError: StateFlow<String?> = _uploadError

    fun uploadProductWithImage(
        uri: Uri,
        context: Context,
        name: String,
        region: String,
        description: String,
        price: String,
        navController: NavController
    ) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val file = getFileFromUri(context, uri)
                if (file == null) {
                    withContext(Dispatchers.Main) {
                        Toast.makeText(context, "Failed to process image", Toast.LENGTH_SHORT).show()
                    }
                    return@launch
                }

                val reqFile = file.asRequestBody("image/*".toMediaTypeOrNull())
                val body = MultipartBody.Part.createFormData("image", file.name, reqFile)

                val response = getImgurService().uploadImage(
                    body,
                    "Client-ID bec2e37e4bd3088"
                )

                if (response.isSuccessful) {
                    val imageUrl = response.body()?.data?.link ?: ""

                    val productId = database.push().key ?: ""
                    val product = Product(
                        name = name,  // Added name
                        region = region, // Added region
                        description = description, // Added description
                        price = price, // Changed to String
                        imageUrl = imageUrl,
                        id = productId // Changed to id
                    )

                    database.child(productId).setValue(product)
                        .addOnSuccessListener {
                            viewModelScope.launch {
                                withContext(Dispatchers.Main) {
                                    Toast.makeText(context, "Product saved successfully", Toast.LENGTH_SHORT).show() //changed from student to product
                                    navController.navigate(ROUTE_PRODUCT_LIST)
                                }
                            }
                        }.addOnFailureListener {
                            viewModelScope.launch {
                                withContext(Dispatchers.Main) {
                                    Toast.makeText(context, "Failed to save product", Toast.LENGTH_SHORT).show() //changed from student to product
                                }
                            }
                        }

                } else {
                    withContext(Dispatchers.Main) {
                        Toast.makeText(context, "Upload error", Toast.LENGTH_SHORT).show()
                    }
                }

            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    Toast.makeText(context, "Exception: ${e.localizedMessage}", Toast.LENGTH_LONG).show()
                }
            }
        }
    }

    fun fetchProducts(

    ) {
        val ref = database
        _isLoading.value = true

        ref.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val list = mutableListOf<Product>()
                for (snap in snapshot.children) {
                    snap.getValue(Product::class.java)?.let {
                        list.add(it)
                    }
                }
                _products.value = list
                _isLoading.value = false
            }

            override fun onCancelled(error: DatabaseError) {
                _isLoading.value = false
                // You can handle error state here or expose another StateFlow
            }
        })
    }

    fun updateproducts(
        context: Context,
        navController: NavController,
        name: String,
        region: String,
        price: String,
        description: String,
        productId: String
    ) {
        // Find the existing product to retain the imageUrl
        val currentImageUrl = _products.value.find { it.id == productId }?.imageUrl ?: ""

        val databaseReference = FirebaseDatabase.getInstance()
            .getReference("Products").child(productId)

        val updatedProduct = Product(
            name = name,
            region = region,
            description = description,
            price = price,
            imageUrl = currentImageUrl,
            id = productId
        )

        databaseReference.setValue(updatedProduct)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Toast.makeText(context, "Product Updated Successfully", Toast.LENGTH_LONG).show()
                    navController.navigate(ROUTE_PRODUCT_LIST)
                } else {
                    Toast.makeText(context, "Product update failed", Toast.LENGTH_LONG).show()
                }
            }
    }

//    fun updateproducts(
//        context: Context,
//        navController: NavController,
//        name: String,
//        region: String,
//        price: String, // Changed to String
//        description: String,
//        productId: String
//    ) {
//        val databaseReference = FirebaseDatabase.getInstance().getReference("Products").child(productId) // Corrected path
//        val updatedProduct = Product(
//            name = name,  // Added name
//            region = region, // Added region
//            description = description, // Added description
//            price = price, // Changed to String
//            imageUrl = "", // Added imageUrl
//            id = productId // Added id
//        )
//
//        databaseReference.setValue(updatedProduct)
//            .addOnCompleteListener { task ->
//                if (task.isSuccessful) {
//                    Toast.makeText(context, "Product Updated Successfully", Toast.LENGTH_LONG).show()
//                    navController.navigate(ROUTE_PRODUCT_LIST)
//                } else {
//                    Toast.makeText(context, "Product update failed", Toast.LENGTH_LONG).show()
//                }
//            }
//
//    }

    fun deleteProduct(
        context: Context,
        productId: String,
        navController: NavController
    ) {
        AlertDialog.Builder(context)
            .setTitle("Delete Product")
            .setMessage("Are you sure you want to delete this product?")
            .setPositiveButton("Yes") { _, _ ->
                val databaseReference = FirebaseDatabase.getInstance().getReference("Products").child(productId)  // Corrected path
                databaseReference.removeValue().addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        Toast.makeText(context, "Product deleted Successfully", Toast.LENGTH_LONG).show()
                        navController.navigate(ROUTE_PRODUCT_LIST) // Navigate after successful deletion
                    } else {
                        Toast.makeText(context, "product not deleted", Toast.LENGTH_LONG).show()
                        fetchProducts()
                    }
                }
            }
            .setNegativeButton("No") { dialog, _ ->
                dialog.dismiss()
            }
            .show()
    }
}

