package com.mukarram.businessmanagementapp.DatabaseApp.DataClasses

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "products")
data class Product(

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "productId")
    val id: Long? = 0,
    @ColumnInfo(name = "product_name")
    val name: String,
    @ColumnInfo(name = "product_price")
    val price: Double,
    @ColumnInfo(name = "product_quantity")
    val quantity: Int,
    @ColumnInfo(name = "product_sales")
    val saleQty: Int,
    @ColumnInfo(name = "product_type")
    val product_type: String,
    @ColumnInfo(name = "product_remaining")
    var product_remaining: Int


)

class InvalidProductException(message: String): Exception(message)
