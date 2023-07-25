package com.mukarram.businessmanagementapp.DatabaseApp.DaoFiles

import androidx.room.*
import com.mukarram.businessmanagementapp.DatabaseApp.DataClasses.ProductBill
import com.mukarram.businessmanagementapp.DatabaseApp.DataClasses.ProductEntry
import kotlinx.coroutines.flow.Flow

@Dao
interface ProductBillDao {


    @Query("SELECT * FROM productBill")
    fun getAllProductBill(): Flow<List<ProductBill>>

    @Insert
    suspend fun insertProductBill(productBill: ProductBill)


    @Query("SELECT * FROM productBill WHERE billId = :billId")
    suspend fun getProductBillById(billId: Long): ProductBill

    @Query("SELECT * FROM productBill WHERE billId = :billId")
    suspend fun getProductEntriesForBill(billId: Long): List<ProductBill>
}

