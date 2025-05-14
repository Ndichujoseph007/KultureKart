//package com.ndichu.kulturekart.model
//
//import androidx.room.Dao
//import androidx.room.Delete
//import androidx.room.Insert
//import androidx.room.Query
//import kotlinx.coroutines.flow.Flow
//
//
//@Dao
//interface ProductDao {
//
//    @Insert
//    suspend fun insertProduct(product: ProductEntity)
//
//    @Insert
//    suspend fun insertProducts(products: List< ProductEntity>)  // Correct way to insert multiple products
//
//    @Query("SELECT * FROM products")
//    fun getAllProducts(): Flow<List< ProductEntity>>  // Correct return type
//
//    @Delete
//    suspend fun deleteProduct(product:  ProductEntity)
//}