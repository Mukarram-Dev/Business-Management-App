package com.mukarram.businessmanagementapp.DatabaseApp


import com.mukarram.businessmanagementapp.DatabaseApp.DataClasses.ProductBill
import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

import com.mukarram.businessmanagementapp.DatabaseApp.DaoFiles.BillDao
import com.mukarram.businessmanagementapp.DatabaseApp.DaoFiles.CustomerDao
import com.mukarram.businessmanagementapp.DatabaseApp.DaoFiles.ProductDao
import com.mukarram.businessmanagementapp.DatabaseApp.DaoFiles.ProductBillDao
import com.mukarram.businessmanagementapp.DatabaseApp.DataClasses.Bill
import com.mukarram.businessmanagementapp.DatabaseApp.DataClasses.Customer
import com.mukarram.businessmanagementapp.DatabaseApp.DataClasses.Product

@Database(entities = [Product::class, Bill::class, Customer::class, ProductBill::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun productDao(): ProductDao
    abstract fun billDao(): BillDao
    abstract fun customerDao(): CustomerDao
    abstract fun productBillDao(): ProductBillDao

    companion object {
        @Volatile
        private var instance: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            return instance ?: synchronized(this) {
                val newInstance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "business_database"
                ).build()
                instance = newInstance
                newInstance
            }
        }
    }
}
