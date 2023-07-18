package com.mukarram.businessmanagementapp.Presentaion.product_sales

data class SaleDetailsModel(
    val customerName: String,
    val productName: String,
    val saleQty: Int,
    val salePrice: Double,
    val purchaseDate: String,
    val totalBill: Double,
    val productType: String,
    val purchasePrie: Double,
)
