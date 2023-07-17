package com.mukarram.businessmanagementapp.DatabaseApp.UseCases.UseCaseBill

import com.mukarram.businessmanagementapp.DatabaseApp.DataClasses.Bill
import com.mukarram.businessmanagementapp.DatabaseApp.repositories.BillRepository
import kotlinx.coroutines.flow.Flow

class GetAllBills(
    private val repository: BillRepository,
) {
        suspend fun execute(): Flow<List<Bill>> {
        return repository.getAllBills()
    }
}