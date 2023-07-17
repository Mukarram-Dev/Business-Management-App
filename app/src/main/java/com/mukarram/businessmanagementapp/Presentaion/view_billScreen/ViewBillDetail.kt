package com.mukarram.businessmanagementapp.Presentaion.view_billScreen

data class ViewBillDetails(
    val customerName: String,
    val purchaseDate: String,
    val totalBill: Double,
    val currentBillId: Long?,
)