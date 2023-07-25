package com.mukarram.businessmanagementapp.DatabaseApp.ConvertersClass

// File: Converter.kt

import androidx.room.TypeConverter

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.mukarram.businessmanagementapp.DatabaseApp.DataClasses.ProductEntry

class Converter {
    private val gson = Gson()

    @TypeConverter
    fun fromProductEntryList(productEntries: List<ProductEntry>): String {
        return gson.toJson(productEntries)
    }

    @TypeConverter
    fun toProductEntryList(json: String): List<ProductEntry> {
        val type = object : TypeToken<List<ProductEntry>>() {}.type
        return gson.fromJson(json, type)
    }
}
