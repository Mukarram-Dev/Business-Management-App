package com.mukarram.businessmanagementapp.Presentaion.bill_detail

data class GetBillDetails(
    val customerName: String,
    val customerAdress: String,
    val customerPhone: String,
    val productName: String,
    val saleQty: Int,
    val salePrice: Double,
    val purchaseDate: String,
    val currentBillId: Long?,
    val totalBill: Double,
)