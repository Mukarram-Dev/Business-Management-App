package com.mukarram.businessmanagementapp.Presentaion.product_sales

import com.mukarram.businessmanagementapp.DatabaseApp.DataClasses.ProductEntry

data class SaleDetailsModel(
    val customerName: String,
    val purchaseDate: String,
    val totalBill: Double,
    val productName: String,
    val productType: String,
    val saleQty: Int,
    val salePrice: Double,


    )
