package com.mukarram.businessmanagementapp.DatabaseApp.repositories

import com.mukarram.businessmanagementapp.DatabaseApp.DataClasses.ProductSale
import kotlinx.coroutines.flow.Flow

interface ProductSaleRepository {
    fun getProduct() : Flow<List<ProductSale>>
    suspend fun insertProductSale(productSale: ProductSale)
    suspend fun getProductSalesByProductId(productSaleId: Int) : ProductSale?




}
