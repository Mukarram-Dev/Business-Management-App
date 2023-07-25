package com.mukarram.businessmanagementapp.DatabaseApp.UseCases.UseCaseProductBill


import com.mukarram.businessmanagementapp.DatabaseApp.DataClasses.ProductBill


import com.mukarram.businessmanagementapp.DatabaseApp.repositories.ProductBillRepository

data class AddProductBill(
    private val productBillRepository: ProductBillRepository
) {


    suspend operator fun invoke(productBill: ProductBill) {
        productBillRepository.insertProductBill(productBill)
    }
}
