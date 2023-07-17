package com.mukarram.businessmanagementapp.DatabaseApp.repositories

import com.mukarram.businessmanagementapp.DatabaseApp.DataClasses.ProductBill


import kotlinx.coroutines.flow.Flow

interface ProductBillRepository {
    fun getProductBill() : Flow<List<ProductBill>>
    suspend fun insertProductBill(productBill: ProductBill)
    suspend fun getProductBillById(productBillId: Long) : ProductBill?




}
