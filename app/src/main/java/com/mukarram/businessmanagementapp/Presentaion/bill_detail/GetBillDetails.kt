package com.mukarram.businessmanagementapp.Presentaion.bill_detail

import com.mukarram.businessmanagementapp.DatabaseApp.DataClasses.Product
import com.mukarram.businessmanagementapp.DatabaseApp.DataClasses.ProductBill
import com.mukarram.businessmanagementapp.DatabaseApp.DataClasses.ProductEntry

data class GetBillDetails(
    val customerName: String,
    val customerAdress: String,
    val customerPhone: String,
    val productEntries: List<ProductEntry>,
    val purchaseDate: String,
    val currentBillId: Long?,
    val totalBill: Double,
    val products: List<Product?>,
)