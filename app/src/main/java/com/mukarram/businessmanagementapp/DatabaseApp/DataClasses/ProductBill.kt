package com.mukarram.businessmanagementapp.DatabaseApp.DataClasses

import androidx.annotation.NonNull
import androidx.room.Entity
import androidx.room.ForeignKey
import com.mukarram.businessmanagementapp.DatabaseApp.DataClasses.Bill
import com.mukarram.businessmanagementapp.DatabaseApp.DataClasses.Product


@Entity(
    tableName = "productBill",
    primaryKeys = ["productId", "billId"],
    foreignKeys = [
        ForeignKey(entity = Product::class, parentColumns = ["productId"], childColumns = ["productId"]),
        ForeignKey(
            entity = Bill::class,
            parentColumns = ["billId"],
            childColumns = ["billId"],
            onDelete = ForeignKey.CASCADE
        ) // Set the onDelete behavior as per your requirement
    ]
)
data class ProductBill(
    @NonNull val productId: Long,
    @NonNull val billId: Long,
    val salePrice: Double?,
    val saleQuantity: Int?,
    val totalBill: Double
) {
    constructor(
        productId: Long,
        billId: Long?,
        salePrice: Double?,
        saleQuantity: Int?,
        totalBill: Double
    ) : this(
        productId = productId,
        billId = billId?:0L, // Provide a default value (0L) if billId is null
        salePrice = salePrice,
        saleQuantity = saleQuantity,
        totalBill = totalBill
    )
}
