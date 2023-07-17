package com.mukarram.businessmanagementapp.DatabaseApp.DataClasses

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "customers")
data class Customer(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "customerId")
    val id: Long?,
    @ColumnInfo(name = "customer_name")
    val name: String,
    @ColumnInfo(name = "customer_address")
    val address: String,
    @ColumnInfo(name = "customer_phone")
    val phone: Long,
    // Add other relevant fields for customer information
)

class InvalidCustomerException(message: String): Exception(message)