package com.mukarram.businessmanagementapp.util

sealed class OrderType {
    object Ascending: OrderType()
    object Descending: OrderType()
}