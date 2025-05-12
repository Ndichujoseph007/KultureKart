package com.ndichu.kulturekart.data


import androidx.lifecycle.ViewModel
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.ndichu.kulturekart.model.Product
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.tasks.await
import com.google.firebase.auth.FirebaseAuth


class ProductViewModel @Inject constructor(): ViewModel() {
    val database = FirebaseDatabase.getInstance().reference

    private val auth: FirebaseAuth = FirebaseAuth.getInstance()

    val currentUser = auth.currentUser

    private val dbRef = FirebaseDatabase.getInstance().getReference("products")

    private val _products = MutableStateFlow<List<Product>>(emptyList())
    val products: StateFlow<List<Product>> = _products

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error

    init {
        fetchProducts()
    }

    private fun fetchProducts() {
        _isLoading.value = true
        dbRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val tempList = mutableListOf<Product>()
                for (item in snapshot.children) {
                    val product = item.getValue(Product::class.java)
                    product?.let { tempList.add(it) }
                }
                _products.value = tempList
                _isLoading.value = false
            }

            override fun onCancelled(error: DatabaseError) {
                _error.value = error.message
                _isLoading.value = false
            }
        })
    }

    fun uploadProduct(product: Product, onComplete: (Boolean) -> Unit) {
        val sellerId = auth.currentUser?.uid ?: return onComplete(false)
        val id = dbRef.push().key ?: return onComplete(false)
        val productWithId = product.copy( id = id ,sellerId = sellerId)

        dbRef.child(id).setValue(productWithId)
            .addOnSuccessListener { onComplete(true) }
            .addOnFailureListener { onComplete(false) }
    }



    fun getProductById(productId: String): Flow<Product?> = flow {
        val productRef = database.child("products").child(productId)
        val snapshot = productRef.get().await()
        val product = snapshot.getValue(Product::class.java)
        emit(product)
    }.flowOn(Dispatchers.IO)



    fun deleteProduct(productId: String) {
        _isLoading.value = true
        val userId = auth.currentUser?.uid ?: return

        database.child(productId).get().addOnSuccessListener { snapshot ->
            val productSellerId = snapshot.child("sellerId").getValue(String::class.java)
            if (productSellerId == userId) {

                database.child("products").child(productId).removeValue()
                    .addOnSuccessListener {
                        _isLoading.value = false
                        // Optionally, refresh the products list
                        fetchProducts()
                    }
                    .addOnFailureListener {
                        _isLoading.value = false
                        _error.value = it.message
                    }
            } else {
                _isLoading.value = false
                _error.value = "Unauthorized: You can only delete your own products."
            }
        }.addOnFailureListener {
            _isLoading.value = false
            _error.value = it.message
        }
    }


    fun editProduct(updatedProduct: Product, onComplete: (Boolean) -> Unit) {
        val productId = updatedProduct.id.ifEmpty {
            _error.value = "Product ID is missing"
            onComplete(false)
            return
        }

        database.child("products").child(productId).setValue(updatedProduct)
            .addOnSuccessListener {
                fetchProducts() // refresh the list
                onComplete(true)
            }
            .addOnFailureListener {
                _error.value = it.message
                onComplete(false)
            }
    }




//    fun getProductById(id: String): Product? {
//        return _products.value.value.find { it.id == id } // Access current value of StateFlow
//    }
}