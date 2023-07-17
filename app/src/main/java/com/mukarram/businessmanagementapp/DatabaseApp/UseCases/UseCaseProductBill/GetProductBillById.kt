package com.mukarram.businessmanagementapp.DatabaseApp.UseCases.UseCaseProductBill

import com.mukarram.businessmanagementapp.DatabaseApp.DataClasses.ProductBill


import com.mukarram.businessmanagementapp.DatabaseApp.repositories.ProductBillRepository

data class GetProductBillById
    (
    private val productBillRepository: ProductBillRepository
) {
        suspend operator fun invoke(productId: Long) : ProductBill? {
           return productBillRepository.getProductBillById(productId)
        }
}
