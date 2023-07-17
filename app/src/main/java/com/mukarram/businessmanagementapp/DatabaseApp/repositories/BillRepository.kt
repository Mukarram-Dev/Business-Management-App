package com.mukarram.businessmanagementapp.DatabaseApp.repositories

import com.mukarram.businessmanagementapp.DatabaseApp.DaoFiles.BillDao
import com.mukarram.businessmanagementapp.DatabaseApp.DataClasses.Bill
import kotlinx.coroutines.flow.Flow

interface BillRepository {

    fun getAllBills(): Flow<List<Bill>>

    suspend  fun getBillById(Id: Long?) : Bill ?

    suspend fun insertBill(bill: Bill) : Long


    // Add other necessary repository methods
}