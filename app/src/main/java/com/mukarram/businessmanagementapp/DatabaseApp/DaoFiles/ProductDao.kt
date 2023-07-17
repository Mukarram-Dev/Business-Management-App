package com.mukarram.businessmanagementapp.DatabaseApp.DaoFiles


import androidx.room.*
import com.mukarram.businessmanagementapp.DatabaseApp.DataClasses.Product
import kotlinx.coroutines.flow.Flow

@Dao
interface ProductDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertProduct(product: Product)

    @Delete
    suspend fun deleteProduct(product: Product)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    suspend fun updateProduct(product: Product)

    @Query("SELECT * FROM products")
    fun getAllProducts(): Flow<List<Product>>

    @Query("SELECT * FROM products WHERE productId= :id")
    suspend fun getProductById(id: Long): Product?

    // Add other necessary queries and operations
}