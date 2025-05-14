package com.ndichu.kulturekart.data

import android.net.Uri
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.storage.FirebaseStorage
import com.ndichu.kulturekart.model.Product
import kotlinx.coroutines.flow.MutableStateFlow
import java.util.UUID


class ProductViewModel : ViewModel() {

    private val dbRef = FirebaseDatabase.getInstance().getReference("products")
    private val storageRef = FirebaseStorage.getInstance().reference.child("product_images")

    val productList = MutableStateFlow<List<Product>>(emptyList())
        private set

    var isLoading = mutableStateOf(false)
    var uploadError = mutableStateOf<String?>(null)

    init {
        fetchProducts()
    }

    fun addProduct(product: Product, imageUri: Uri?, onSuccess: () -> Unit) {
        isLoading.value = true

        if (imageUri != null) {
            val imageRef = storageRef.child("${UUID.randomUUID()}.jpg")
            imageRef.putFile(imageUri)
                .addOnSuccessListener {
                    imageRef.downloadUrl.addOnSuccessListener { uri ->
                        val productId = dbRef.push().key ?: return@addOnSuccessListener
                        val newProduct = product.copy(id = productId, imageUrl = uri.toString())
                        dbRef.child(productId).setValue(newProduct)
                            .addOnSuccessListener {
                                onSuccess()
                                isLoading.value = false
                            }
                            .addOnFailureListener {
                                uploadError.value = it.message
                                isLoading.value = false
                            }
                    }
                }
                .addOnFailureListener {
                    uploadError.value = it.message
                    isLoading.value = false
                }
        }
    }

    fun fetchProducts() {
        dbRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val products = mutableListOf<Product>()
                for (child in snapshot.children) {
                    val product = child.getValue(Product::class.java)
                    product?.let { products.add(it) }
                }
                productList.value = products
            }

            override fun onCancelled(error: DatabaseError) {
                uploadError.value = error.message
            }
        })
    }


    fun updateProduct(updatedProduct: Product, onComplete: () -> Unit) {
        dbRef.child(updatedProduct.id).setValue(updatedProduct)
            .addOnSuccessListener { onComplete() }
            .addOnFailureListener { uploadError.value = it.message }
    }

    fun deleteProduct(productId: String, onComplete: () -> Unit) {
        dbRef.child(productId).removeValue()
            .addOnSuccessListener { onComplete() }
            .addOnFailureListener { uploadError.value = it.message }
    }
}