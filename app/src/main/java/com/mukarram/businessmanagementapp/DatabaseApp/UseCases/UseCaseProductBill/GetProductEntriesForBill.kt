package com.mukarram.businessmanagementapp.DatabaseApp.UseCases.UseCaseProductBill

import com.mukarram.businessmanagementapp.DatabaseApp.DataClasses.ProductBill
import com.mukarram.businessmanagementapp.DatabaseApp.repositories.ProductBillRepository

data class GetProductEntriesForBill(
    private val productBillRepository: ProductBillRepository
) {
    suspend operator fun invoke(billId: Long): List<ProductBill> {
        return productBillRepository.getProductEntriesForBill(billId)
    }
}

