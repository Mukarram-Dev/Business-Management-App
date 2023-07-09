package com.mukarram.businessmanagementapp.DatabaseApp.DaoFiles

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.mukarram.businessmanagementapp.DatabaseApp.DataClasses.Product
import com.mukarram.businessmanagementapp.DatabaseApp.DataClasses.ProductSale
import com.mukarram.businessmanagementapp.NavigationClasses.Screen
import kotlinx.coroutines.flow.Flow
@Dao
interface ProductSaleDao {
    @Query("SELECT * FROM productSales")
    fun getAllProductSales(): Flow<List<ProductSale>>

    @Insert
    suspend fun insertProductSale(productSale: ProductSale)

    @Query("SELECT * FROM productSales WHERE productSaleId = :productSaleId")
    fun getProductSalesByProductId(productSaleId: Int):ProductSale
}