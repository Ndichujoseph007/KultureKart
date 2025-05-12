package com.ndichu.kulturekart.model

//data class Product(
//    val id: String = "",
//    val name: String = "",
//    val description: String = "",
//    val region: String = "",
//    val price: Double = 0.0,
//    val imageUrl: String = ""
//)


data class Product(
    val name: String = "",
    val description: String = "",
    val region: String = "",
    val imageUrl: String = "",
    val price: Double = 0.0,
    val sellerId: String="",
    val id: String=""
)
