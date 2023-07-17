package com.mukarram.businessmanagementapp.DatabaseApp.repositories

import com.mukarram.businessmanagementapp.DatabaseApp.DaoFiles.ProductDao
import com.mukarram.businessmanagementapp.DatabaseApp.DataClasses.Product
import kotlinx.coroutines.flow.Flow

interface ProductRepository {

    fun getProducts() : Flow<List<Product>>

    suspend fun getProductById(id: Long) : Product?

    suspend fun insertProduct(product: Product)

    suspend fun updateProduct(product: Product)

    suspend fun deleteProduct(product: Product)
}