package com.ndichu.kulturekart.data

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.ndichu.kulturekart.model.CartItem
import com.ndichu.kulturekart.model.Product
import androidx.compose.runtime.State


class CartViewModel : ViewModel() {
    private val dbRef = FirebaseDatabase.getInstance().getReference("carts")
    private val auth = FirebaseAuth.getInstance()

    private val _cartItems = mutableStateOf<List<CartItem>>(emptyList())
    val cartItems: State<List<CartItem>> = _cartItems


    init {
        fetchCartItems()
    }

    private fun fetchCartItems() {
        val uid = auth.currentUser?.uid ?: return
        dbRef.child(uid).addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val items = snapshot.children.mapNotNull { it.getValue(CartItem::class.java) }
                _cartItems.value = items
            }

            override fun onCancelled(error: DatabaseError) {}
        })
    }

    fun addToCart(product: Product) {
        val uid = auth.currentUser?.uid ?: return
        val cartItemId = dbRef.child(uid).push().key ?: return

        val item = CartItem(
            id = cartItemId,
            productId = product.id,
            name = product.name,
            price = product.price.toDoubleOrNull() ?: 0.0,

            imageUrl = product.imageUrl,
            quantity = 1,
            buyerId = uid
        )

        dbRef.child(uid).child(cartItemId).setValue(item)
    }

    fun updateQuantity(cartItemId: String, newQuantity: Int) {
        val uid = auth.currentUser?.uid ?: return
        dbRef.child(uid).child(cartItemId).child("quantity").setValue(newQuantity)
    }

    fun removeFromCart(cartItemId: String) {
        val uid = auth.currentUser?.uid ?: return
        dbRef.child(uid).child(cartItemId).removeValue()
    }

    fun clearCart() {
        val uid = auth.currentUser?.uid ?: return
        dbRef.child(uid).removeValue()
    }
}