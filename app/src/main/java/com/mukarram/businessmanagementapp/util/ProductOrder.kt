package com.mukarram.businessmanagementapp.util

sealed class ProductOrder(val orderType: OrderType) {
    class ProductName(orderType: OrderType): ProductOrder(orderType)
    class Date(orderType: OrderType): ProductOrder(orderType)


    fun copy(orderType: OrderType): ProductOrder {
        return when(this) {
            is ProductName -> ProductName(orderType)
            is Date -> Date(orderType)

        }
    }
}