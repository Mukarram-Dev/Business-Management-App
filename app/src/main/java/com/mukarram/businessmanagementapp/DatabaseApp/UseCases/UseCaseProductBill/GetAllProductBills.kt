package com.mukarram.businessmanagementapp.DatabaseApp.UseCases.UseCaseProductBill

import com.mukarram.businessmanagementapp.DatabaseApp.DataClasses.Bill
import com.mukarram.businessmanagementapp.DatabaseApp.DataClasses.ProductBill
import com.mukarram.businessmanagementapp.DatabaseApp.repositories.ProductBillRepository
import kotlinx.coroutines.flow.Flow

data class GetAllProductBills(
    private val productBillRepository: ProductBillRepository
) {
    suspend fun execute(): Flow<List<ProductBill>> {
        return productBillRepository.getProductBill()
    }

}
