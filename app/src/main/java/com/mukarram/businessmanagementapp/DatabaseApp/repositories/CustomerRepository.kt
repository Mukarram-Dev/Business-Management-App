package com.mukarram.businessmanagementapp.DatabaseApp.repositories

import com.mukarram.businessmanagementapp.DatabaseApp.DaoFiles.CustomerDao
import com.mukarram.businessmanagementapp.DatabaseApp.DataClasses.Customer
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.flow.Flow
import kotlin.coroutines.CoroutineContext

interface CustomerRepository {

    fun getAllCustomer(): Flow<List<Customer>>

    suspend fun getCustomerById(id: Long?): Customer?

    suspend fun insertCustomer(customer: Customer) : Long


    // Add other necessary repository methods
}
