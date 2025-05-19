package com.ndichu.kulturekart.data

import android.app.AlertDialog
import android.content.Context
import android.net.Uri
import android.util.Log
import android.widget.Toast
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.storage.FirebaseStorage
import com.ndichu.kulturekart.model.Product
import com.ndichu.kulturekart.navigation.ROUTE_PRODUCT_LIST
import com.ndichu.kulturekart.navigation.ROUTE_SELLER_HOME
import com.ndichu.kulturekart.network.ImgurService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.flow
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
import java.util.UUID

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
//    private val userRole = MutableStateFlow<String>("buyer")

    private val _userRole = MutableStateFlow("")
    val userRole: StateFlow<String> = _userRole








    fun fetchUserRole() {
        val uid = FirebaseAuth.getInstance().currentUser?.uid ?: return
        val userRef = FirebaseDatabase.getInstance().reference.child("users").child(uid)

        userRef.child("role").addListenerForSingleValueEvent(object : ValueEventListener {
override fun onDataChange(snapshot: DataSnapshot) {
    val role = snapshot.getValue(String::class.java) ?: "buyer"
    _userRole.value = role
}


            override fun onCancelled(error: DatabaseError) {
                _isLoading.value = false // ✅ Stop loading on error
                _uploadError.value = error.message
                // Handle error
            }
        })
    }
// default


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
                    val sellerId = FirebaseAuth.getInstance().currentUser?.uid////today

                    val productId = database.push().key ?: ""
                    val product = Product(
                        name = name,  // Added name
                        region = region, // Added region
                        description = description, // Added description
                        price = price, // Changed to String
                        imageUrl = imageUrl,
                        id = productId,
                        sellerId = sellerId ?: ""//today
                    )

                    database.child(productId).setValue(product)
                        .addOnSuccessListener {
                            viewModelScope.launch {
                                withContext(Dispatchers.Main) {
                                    Toast.makeText(context, "Product saved successfully", Toast.LENGTH_SHORT).show()
                                    loadSellerProducts()
                                    navController.navigate(ROUTE_SELLER_HOME)
                                }
                            }
                        }.addOnFailureListener {
                            viewModelScope.launch {
                                withContext(Dispatchers.Main) {
                                    Toast.makeText(context, "Failed to save product", Toast.LENGTH_SHORT).show()
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





    ////////today
    private val _sellerProducts = MutableStateFlow<List<Product>>(emptyList())
    val sellerProducts: StateFlow<List<Product>> = _sellerProducts

    fun loadSellerProducts() {
        val sellerId = FirebaseAuth.getInstance().currentUser?.uid ?: return
        val productsRef = FirebaseDatabase.getInstance().getReference("Products")

        productsRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val productList = mutableListOf<Product>()
                for (productSnap in snapshot.children) {
                    val product = productSnap.getValue(Product::class.java)
                    if (product?.sellerId == sellerId) {
                        productList.add(product)
                    }
                }
                _sellerProducts.value = productList
            }

            override fun onCancelled(error: DatabaseError) {
                Log.e("Firebase", "Failed to load seller products: ${error.message}")
            }
        })
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

//    fun updateproducts(
//        context: Context,
//        navController: NavController,
//        name: String,
//        region: String,
//        price: String,
//        description: String,
//        productId: String,
//        newImageUri: Uri? = null // New param for new image
//    ) {
//        val databaseReference = FirebaseDatabase.getInstance()
//            .getReference("Products").child(productId)
//
//        // Helper to update product data in Firebase Realtime Database
//        fun updateProductInDb(imageUrl: String) {
//            val updatedProduct = Product(
//                id = productId,
//                name = name,
//                region = region,
//                description = description,
//                price = price,
//                imageUrl = imageUrl
//            )
//
//            databaseReference.setValue(updatedProduct)
//                .addOnCompleteListener { task ->
//                    if (task.isSuccessful) {
//                        Toast.makeText(context, "Product Updated Successfully", Toast.LENGTH_LONG).show()
//                        loadSellerProducts()
//                        navController.navigate(ROUTE_PRODUCT_LIST)
//                    } else {
//                        Toast.makeText(context, "Product update failed", Toast.LENGTH_LONG).show()
//                    }
//                }
//        }
//
//        if (newImageUri != null) {
//            // Upload new image first
//            val storageRef = FirebaseStorage.getInstance()
//                .reference.child("product_images/$productId.jpg")
//
//            val uploadTask = storageRef.putFile(newImageUri)
//            uploadTask.addOnSuccessListener {
//                // Get the download URL after upload
//                storageRef.downloadUrl.addOnSuccessListener { uri ->
//                    updateProductInDb(uri.toString())
//                }.addOnFailureListener {
//                    Toast.makeText(context, "Failed to retrieve image URL", Toast.LENGTH_LONG).show()
//                }
//            }.addOnFailureListener {
//                Toast.makeText(context, "Image upload failed", Toast.LENGTH_LONG).show()
//            }
//        } else {
//            // No new image, so fetch current image URL and update product
//            // Assuming _products is your cached product list in ViewModel, otherwise fetch from DB
//            val currentImageUrl = _products.value.find { it.id == productId }?.imageUrl ?: ""
//            updateProductInDb(currentImageUrl)
//        }
//    }


//
//    fun deleteProduct(
//        context: Context,
//        productId: String,
//        navController: NavController
//    ) {
//        AlertDialog.Builder(context)
//            .setTitle("Delete Product")
//            .setMessage("Are you sure you want to delete this product?")
//            .setPositiveButton("Yes") { _, _ ->
//                val databaseReference = FirebaseDatabase.getInstance().getReference("Products").child(productId)  // Corrected path
//                databaseReference.removeValue().addOnCompleteListener { task ->
//                    if (task.isSuccessful) {
//                        Toast.makeText(context, "Product deleted Successfully", Toast.LENGTH_LONG).show()
//                        loadSellerProducts()
//                        navController.navigate(ROUTE_PRODUCT_LIST) // Navigate after successful deletion
//                    } else {
//                        Toast.makeText(context, "product not deleted", Toast.LENGTH_LONG).show()
//                        fetchProducts()
//                    }
//                }
//            }
//            .setNegativeButton("No") { dialog, _ ->
//                dialog.dismiss()
//            }
//            .show()
//    }

    fun getProductById(productId: String, onResult: (Product?) -> Unit) {
        database.child("products").child(productId)
            .addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    val product = snapshot.getValue(Product::class.java)
                    onResult(product)
                }

                override fun onCancelled(error: DatabaseError) {
                    onResult(null)
                }
            })
    }

    fun updateProductWithImage(
        context: Context,
        navController: NavController,
        productId: String,
        name: String,
        price: String,
        region: String,
        description: String,
        newImageUri: Uri?,
        onComplete: (Boolean) -> Unit
    ) {
        val updates = mapOf(
            "name" to name,
            "price" to price,
            "region" to region,
            "description" to description
        )

        if (newImageUri != null) {
            val storageRef = FirebaseStorage.getInstance().reference
                .child("product_images/${UUID.randomUUID()}")
            storageRef.putFile(newImageUri).addOnSuccessListener {
                storageRef.downloadUrl.addOnSuccessListener { uri ->
                    val updatedWithImage = updates + ("imageUrl" to uri.toString())
                    database.child("products").child(productId).updateChildren(updatedWithImage)
                        .addOnCompleteListener { task -> onComplete(task.isSuccessful) }
                }
            }.addOnFailureListener {
                onComplete(false)
            }
        } else {
            database.child("products").child(productId).updateChildren(updates)
                .addOnCompleteListener { task -> onComplete(task.isSuccessful) }
        }
    }



    private var currentProductToDelete: Product? = null
    private val _showDeleteDialog = MutableStateFlow(false)
    val showDeleteDialog: StateFlow<Boolean> = _showDeleteDialog





    fun confirmDeleteProduct(product: Product) {
        currentProductToDelete = product
        _showDeleteDialog.value = true
    }

    fun dismissDeleteDialog() {
        _showDeleteDialog.value = false
        currentProductToDelete = null
    }

    fun performDeleteProduct(onResult: (Boolean) -> Unit) {
        val product = currentProductToDelete ?: return

        val storageRef = FirebaseStorage.getInstance().getReferenceFromUrl(product.imageUrl)

        storageRef.delete().addOnSuccessListener {
            database.child(product.id).removeValue().addOnCompleteListener { task ->
                _showDeleteDialog.value = false
                currentProductToDelete = null
                if (task.isSuccessful) {
                    loadSellerProducts()
                    onResult(true)
                } else {
                    onResult(false)
                }
            }
        }.addOnFailureListener {
            _showDeleteDialog.value = false
            currentProductToDelete = null
            onResult(false)
        }
    }



    fun deleteProduct(context: Context,productId: String,
                      navController: NavController){
        AlertDialog.Builder(context)
            .setTitle("Delete Product")
            .setMessage("Are you sure you want to delete this product?")
            .setPositiveButton("Yes"){ _, _ ->
                val databaseReference = FirebaseDatabase.getInstance()
                    .getReference("Products/$productId")
                databaseReference.removeValue().addOnCompleteListener {
                        task ->
                    if (task.isSuccessful){

                        Toast.makeText(context,"Product deleted Successfully",Toast.LENGTH_LONG).show()
                    }else{
                        Toast.makeText(context,"Product not deleted",Toast.LENGTH_LONG).show()
                    }
                }
            }
            .setNegativeButton("No"){ dialog, _ ->
                dialog.dismiss()
            }
            .show()
    }


    fun updateProductWithImage(
        context: Context,
        navController: NavController,
        productId: String,
        name: String,
        price: String,
        region: String,
        description: String,
        newImageUri: Uri?
    ) {
        val databaseRef = FirebaseDatabase.getInstance().getReference("products").child(productId)

        if (newImageUri != null) {
            val storageRef = FirebaseStorage.getInstance().reference
                .child("product_images/$productId.jpg")

            storageRef.putFile(newImageUri)
                .addOnSuccessListener {
                    storageRef.downloadUrl.addOnSuccessListener { uri ->
                        val updatedProduct = Product(
                            id = productId,
                            name = name,
                            price = price,
                            region = region,
                            description = description,
                            imageUrl = uri.toString()
                        )
                        databaseRef.setValue(updatedProduct).addOnSuccessListener {
                            Toast.makeText(context, "Product updated", Toast.LENGTH_SHORT).show()
                            navController.popBackStack() // Go back
                        }
                    }
                }
                .addOnFailureListener {
                    Toast.makeText(context, "Failed to upload image", Toast.LENGTH_SHORT).show()
                }
        } else {
            // No new image, update other fields only
            databaseRef.child("name").setValue(name)
            databaseRef.child("price").setValue(price)
            databaseRef.child("region").setValue(region)
            databaseRef.child("description").setValue(description)
                .addOnSuccessListener {
                    Toast.makeText(context, "Product updated", Toast.LENGTH_SHORT).show()
                    navController.popBackStack()
                }
        }
    }




    fun addToCart(product: Product) {
        val userId = FirebaseAuth.getInstance().currentUser?.uid ?: return
        val cartRef = FirebaseDatabase.getInstance().reference
            .child("cart")
            .child(userId)
            .child(product.id)

        cartRef.setValue(product)
    }

}

