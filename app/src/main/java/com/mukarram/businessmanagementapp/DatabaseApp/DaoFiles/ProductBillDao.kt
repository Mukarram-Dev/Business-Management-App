package com.mukarram.businessmanagementapp.DatabaseApp.DaoFiles

import com.mukarram.businessmanagementapp.DatabaseApp.DataClasses.ProductBill
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query


import kotlinx.coroutines.flow.Flow
@Dao
interface ProductBillDao {
    @Query("SELECT * FROM productBill")
    fun getAllProductBill(): Flow<List<ProductBill>>

    @Insert
    suspend fun insertProductBill(productBill: ProductBill)

    @Query("SELECT * FROM productBill WHERE billId = :billId")
    suspend fun getProductBillById(billId: Long): ProductBill
}