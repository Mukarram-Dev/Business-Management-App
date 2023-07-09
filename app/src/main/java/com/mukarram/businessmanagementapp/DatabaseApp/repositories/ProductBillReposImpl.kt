package com.mukarram.businessmanagementapp.DatabaseApp.repositories

import com.mukarram.businessmanagementapp.DatabaseApp.DaoFiles.ProductBillDao
import com.mukarram.businessmanagementapp.DatabaseApp.DataClasses.ProductSale
import kotlinx.coroutines.flow.Flow

class ProductSaleReposImpl(val productSaleDao: ProductBillDao)
    : ProductSaleRepository
{
    override fun getProduct(): Flow<List<ProductSale>> {
        return productSaleDao.getAllProductBill()
    }

    override suspend fun insertProductSale(productSale: ProductSale) {
        return productSaleDao.insertProductBill(productSale)
    }

    override suspend fun getProductSalesByProductId(productSaleId: Int):ProductSale {
        return productSaleDao.getProductBillById(productSaleId)
    }
}