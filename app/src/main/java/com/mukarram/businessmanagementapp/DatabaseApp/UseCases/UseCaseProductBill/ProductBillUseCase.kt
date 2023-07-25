package com.mukarram.businessmanagementapp.DatabaseApp.UseCases.UseCaseProductBill



data class ProductBillUseCase(
    val addProductBill: AddProductBill,
    val getProductBillById: GetProductBillById,
    val getProductEntriesForBill: GetProductEntriesForBill,
    val getAllProductBills : GetAllProductBills
)
