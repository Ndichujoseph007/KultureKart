package com.ndichu.kulturekart.data


import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.ndichu.kulturekart.model.CartItem
import com.ndichu.kulturekart.model.Product // Assuming you have this Product data class
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

class CartViewModel : ViewModel() {
    private val dbRef = FirebaseDatabase.getInstance().getReference("carts")
    private val auth = FirebaseAuth.getInstance()

    // Helper to get current user ID
    private val currentUserId: String?
        get() = auth.currentUser?.uid

    // THE CRUCIAL CHANGE: Use MutableStateFlow for internal state
    private val _cartItems = MutableStateFlow<List<CartItem>>(emptyList())
    // Publicly expose as an immutable StateFlow for UI observation
    val cartItems: StateFlow<List<CartItem>> = _cartItems.asStateFlow()

    init {
        // Start listening for cart changes immediately
        fetchCartItems()
    }

    /**
     * Sets up a real-time listener for the current user's cart in Firebase.
     * Updates [_cartItems] whenever data changes in the database.
     */
    private fun fetchCartItems() {
        val uid = currentUserId
        if (uid == null) {
            _cartItems.value = emptyList() // Clear cart if no user is logged in
            Log.w("CartViewModel", "No user logged in. Cart will be empty.")
            return
        }

        dbRef.child(uid).addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val items = mutableListOf<CartItem>()
                snapshot.children.forEach { itemSnapshot ->
                    val item = itemSnapshot.getValue(CartItem::class.java)
                    if (item != null) {
                        items.add(item)
                    } else {
                        Log.e("CartViewModel", "Failed to parse CartItem from snapshot: ${itemSnapshot.key} - ${itemSnapshot.value}")
                    }
                }
                // Update the StateFlow, which triggers recomposition in the UI
                _cartItems.value = items
                Log.d("CartViewModel", "Cart data updated. Total items: ${items.size}")
            }

            override fun onCancelled(error: DatabaseError) {
                Log.e("CartViewModel", "Firebase cart listener cancelled: ${error.message}", error.toException())
                _cartItems.value = emptyList() // Clear on error
            }
        })
    }

    /**
     * Adds a product to the cart or increments its quantity if it already exists.
     * @param product The product to add.
     * @param quantity The amount to add (defaults to 1).
     * @param context Context for showing Toast messages.
     */
    fun addToCart(product: Product, quantity: Int = 1, context: Context) {
        val uid = currentUserId
        if (uid == null) {
            Toast.makeText(context, "Please log in to add items to cart.", Toast.LENGTH_SHORT).show()
            return
        }

        viewModelScope.launch {
            try {
                // Find if the product already exists in the local StateFlow value
                val existingCartItem = _cartItems.value.firstOrNull { it.productId == product.id }

                if (existingCartItem != null) {
                    // Item exists, update its quantity in Firebase
                    val newQuantity = existingCartItem.quantity + quantity
                    dbRef.child(uid).child(existingCartItem.id).child("quantity").setValue(newQuantity).await()
                    Toast.makeText(context, "${product.name} quantity updated!", Toast.LENGTH_SHORT).show()
                } else {
                    // Item does not exist, create a new CartItem and add to Firebase
                    val cartItemId = dbRef.child(uid).push().key // Generate a unique ID for the cart item
                    if (cartItemId == null) {
                        Toast.makeText(context, "Failed to generate cart item ID.", Toast.LENGTH_SHORT).show()
                        return@launch
                    }

                    val item = CartItem(
                        id = cartItemId,
                        productId = product.id,
                        name = product.name,
                        price = product.price.toDoubleOrNull() ?: 0.0,// Ensure product.price is Double here
                        imageUrl = product.imageUrl,
                        quantity = quantity,
                        buyerId = uid
                    )
                    dbRef.child(uid).child(cartItemId).setValue(item).await()
                    Toast.makeText(context, "${product.name} added to cart!", Toast.LENGTH_SHORT).show()
                }
                // The fetchCartItems listener will automatically pick up this change and update _cartItems.
            } catch (e: Exception) {
                Log.e("CartViewModel", "Error adding product to cart: ${e.message}", e)
                Toast.makeText(context, "Failed to add to cart: ${e.message}", Toast.LENGTH_LONG).show()
            }
        }
    }

    /**
     * Removes a specific item from the cart in Firebase.
     * @param cartItemId The unique ID of the CartItem to remove.
     */
    fun removeFromCart(cartItemId: String) {
        val uid = currentUserId ?: return
        dbRef.child(uid).child(cartItemId).removeValue()
            .addOnSuccessListener { Log.d("CartViewModel", "Item $cartItemId removed successfully.") }
            .addOnFailureListener { e -> Log.e("CartViewModel", "Failed to remove item $cartItemId: ${e.message}", e) }
    }

    /**
     * Increments the quantity of a specific cart item in Firebase.
     * @param cartItemId The unique ID of the CartItem to increment.
     */
    fun incrementQuantity(cartItemId: String) {
        val uid = currentUserId ?: return
        // Find the current quantity from the local StateFlow value
        val currentItem = _cartItems.value.firstOrNull { it.id == cartItemId }
        if (currentItem != null) {
            val newQuantity = currentItem.quantity + 1
            dbRef.child(uid).child(cartItemId).child("quantity").setValue(newQuantity)
                .addOnSuccessListener { Log.d("CartViewModel", "Incremented quantity for $cartItemId to $newQuantity") }
                .addOnFailureListener { e -> Log.e("CartViewModel", "Failed to increment quantity for $cartItemId: ${e.message}", e) }
        }
    }

    /**
     * Decrements the quantity of a specific cart item in Firebase.
     * If quantity becomes 1 and is decremented, the item is removed from the cart.
     * @param cartItemId The unique ID of the CartItem to decrement.
     */
    fun decrementQuantity(cartItemId: String) {
        val uid = currentUserId ?: return
        // Find the current quantity from the local StateFlow value
        val currentItem = _cartItems.value.firstOrNull { it.id == cartItemId }
        if (currentItem != null) {
            if (currentItem.quantity > 1) {
                val newQuantity = currentItem.quantity - 1
                dbRef.child(uid).child(cartItemId).child("quantity").setValue(newQuantity)
                    .addOnSuccessListener { Log.d("CartViewModel", "Decremented quantity for $cartItemId to $newQuantity") }
                    .addOnFailureListener { e -> Log.e("CartViewModel", "Failed to decrement quantity for $cartItemId: ${e.message}", e) }
            } else {
                // If quantity is 1 and decrement is called, remove the item entirely
                removeFromCart(cartItemId)
            }
        }
    }

    /**
     * Clears all items from the current user's cart in Firebase.
     * @param context Context for showing Toast messages.
     */
    fun clearCart(context: Context) {
        val uid = currentUserId
        if (uid == null) {
            Toast.makeText(context, "No user logged in to clear cart.", Toast.LENGTH_SHORT).show()
            return
        }

        viewModelScope.launch {
            try {
                dbRef.child(uid).removeValue().await()
                Toast.makeText(context, "Cart cleared successfully!", Toast.LENGTH_SHORT).show()
                Log.d("CartViewModel", "All items removed from cart for user $uid.")
            } catch (e: Exception) {
                Log.e("CartViewModel", "Error clearing cart: ${e.message}", e)
                Toast.makeText(context, "Failed to clear cart: ${e.message}", Toast.LENGTH_LONG).show()
            }
        }
    }

    /**
     * Simulates a checkout process by simply clearing the cart.
     * In a real app, this would involve creating an order, handling payments, etc.
     * @param context Context for showing Toast messages.
     * @param onComplete Callback indicating whether the simulated checkout was successful.
     */
    fun checkout(context: Context, onComplete: (Boolean) -> Unit) {
        val uid = currentUserId
        if (uid == null) {
            Toast.makeText(context, "Please log in to checkout.", Toast.LENGTH_SHORT).show()
            onComplete(false)
            return
        }

        viewModelScope.launch {
            try {
                // In a real app, you'd move items to an 'orders' collection, handle payment, etc.
                // For this example, we just simulate success and clear the cart.
                clearCart(context) // This will trigger the cart to be empty
                Log.d("CartViewModel", "Checkout process simulated. Cart cleared for user $uid.")
                onComplete(true) // Report successful simulation
            } catch (e: Exception) {
                Log.e("CartViewModel", "Error during simulated checkout: ${e.message}", e)
                Toast.makeText(context, "Checkout failed: ${e.message}", Toast.LENGTH_LONG).show()
                onComplete(false) // Report failure
            }
        }
    }
}