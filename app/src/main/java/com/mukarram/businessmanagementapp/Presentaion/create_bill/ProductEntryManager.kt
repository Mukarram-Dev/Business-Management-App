package com.mukarram.businessmanagementapp.Presentaion.create_bill

import com.mukarram.businessmanagementapp.DatabaseApp.DataClasses.ProductEntry

// ProductEntryManager.kt
object ProductEntryManager {
    private val _productEntries = mutableListOf<ProductEntry>()
    val productEntries: List<ProductEntry> get() = _productEntries

    fun addProductEntry(productEntry: ProductEntry) {
        _productEntries.add(productEntry)
    }

    fun removeProductEntry(productEntry: ProductEntry) {
        _productEntries.remove(productEntry)
    }
    fun clearProductEntry() {
        _productEntries.clear()
    }

    // Add any other methods you need for managing the list
}
