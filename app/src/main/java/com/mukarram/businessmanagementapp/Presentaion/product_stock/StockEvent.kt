package com.mukarram.businessmanagementapp.Presentaion.product_stock

import com.mukarram.businessmanagementapp.DatabaseApp.DataClasses.Product
import com.mukarram.businessmanagementapp.util.ProductOrder

sealed class StockEvent {
    data class DeleteProduct(val product: Product) : StockEvent()
    data class getProduct(val product: Product) : StockEvent()
    data class Order(val productOrder: ProductOrder) : StockEvent()


}
