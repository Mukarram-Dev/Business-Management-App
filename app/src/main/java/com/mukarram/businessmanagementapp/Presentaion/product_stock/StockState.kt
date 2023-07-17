package com.mukarram.businessmanagementapp.Presentaion.product_stock

import com.mukarram.businessmanagementapp.DatabaseApp.DataClasses.Product
import com.mukarram.businessmanagementapp.util.OrderType
import com.mukarram.businessmanagementapp.util.ProductOrder

data class StockState(
    val product: List<Product> = emptyList(),
    val productOrder: ProductOrder = ProductOrder.Date(OrderType.Descending),
)
