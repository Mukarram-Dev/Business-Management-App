package com.mukarram.businessmanagementapp.DatabaseApp.DataClasses

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(
    primaryKeys = ["productId", "billId"],
    foreignKeys = [
        ForeignKey(entity = Product::class, parentColumns = ["id"], childColumns = ["productId"]),
        ForeignKey(entity = Bill::class, parentColumns = ["id"], childColumns = ["billId"])
    ]
)
data class ProductBill(
    val productId: Long,
    val billId: Long,
    val salePrice: Double,
    val saleQuantity: Int
)
