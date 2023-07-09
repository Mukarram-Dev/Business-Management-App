package com.mukarram.businessmanagementapp.DatabaseApp.UseCases.UseCaseProductSale

import com.mukarram.businessmanagementapp.DatabaseApp.DataClasses.ProductSale
import com.mukarram.businessmanagementapp.DatabaseApp.repositories.ProductSaleRepository

data class AddProductSales(
    private val productSaleRepository: ProductSaleRepository
) {
    suspend operator fun invoke(productSales: ProductSale){
        return productSaleRepository.insertProductSale(productSales)
    }
}
