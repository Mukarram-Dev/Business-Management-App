package com.mukarram.businessmanagementapp.DatabaseApp.UseCases.UseCaseProductSale

import com.mukarram.businessmanagementapp.DatabaseApp.DataClasses.ProductSale
import com.mukarram.businessmanagementapp.DatabaseApp.repositories.ProductSaleRepository
import kotlinx.coroutines.flow.Flow

data class GetProductSalesById
    (
    private val productSaleRepository: ProductSaleRepository
) {
        suspend operator fun invoke(productId:Int) : ProductSale? {
           return productSaleRepository.getProductSalesByProductId(productId)
        }
}
