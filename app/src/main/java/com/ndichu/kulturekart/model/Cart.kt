package com.ndichu.kulturekart.model
//
//data class CartItem(
//    val id: String = "",
//    val productId: String = "",
//    val name: String = "",
//    val price: Double = 0.0,
//    val imageUrl: String = "",
//    val quantity: Int = 1,
//    val buyerId: String = ""
//)
//



data class CartItem(
    val id: String = "",        // Unique ID for this specific cart entry (generated by Firebase push())
    val productId: String = "", // The ID of the actual product
    val name: String = "",      // Product name
    val price: Double = 0.0,    // Price of the product (ensure it's Double)
    val imageUrl: String = "",  // Product image URL
    val quantity: Int = 0,      // Quantity in cart
    val buyerId: String = ""    // User ID who owns this cart item
) {
    // No-argument constructor required by Firebase Realtime Database
    constructor() : this("", "", "", 0.0, "", 0, "")

    // Function to convert CartItem to a Map, useful for storing nested objects in Firebase
    fun toMap(): Map<String, Any> {
        return mapOf(
            "id" to id,
            "productId" to productId,
            "name" to name,
            "price" to price,
            "imageUrl" to imageUrl,
            "quantity" to quantity,
            "buyerId" to buyerId
        )
    }
}