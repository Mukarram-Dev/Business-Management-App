package com.mukarram.businessmanagementapp.DatabaseApp.repositories

import com.mukarram.businessmanagementapp.DatabaseApp.DataClasses.ProductBill
import com.mukarram.businessmanagementapp.DatabaseApp.DaoFiles.ProductBillDao


import kotlinx.coroutines.flow.Flow

class ProductBillReposImpl(val productBillDao: ProductBillDao)
    : ProductBillRepository
{
    override fun getProductBill(): Flow<List<ProductBill>> {
        return productBillDao.getAllProductBill()
    }

    override suspend fun insertProductBill(productBill: ProductBill) {
        return productBillDao.insertProductBill(productBill)
    }

    override suspend fun getProductBillById(productBillId: Long): ProductBill {
        return productBillDao.getProductBillById(productBillId)
    }
}