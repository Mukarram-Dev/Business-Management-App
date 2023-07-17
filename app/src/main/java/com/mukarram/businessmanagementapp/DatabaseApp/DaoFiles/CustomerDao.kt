package com.mukarram.businessmanagementapp.DatabaseApp.DaoFiles

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.mukarram.businessmanagementapp.DatabaseApp.DataClasses.Customer
import kotlinx.coroutines.flow.Flow

@Dao
interface CustomerDao {
    @Query("SELECT * FROM customers")
    fun getAllCustomers(): Flow<List<Customer>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCustomer(customer: Customer) : Long

    @Query("SELECT * FROM customers WHERE customerId= :id")
    suspend fun geCustomerById(id: Long?): Customer?
    // Add other necessary queries for CRUD operations
}
