package com.mukarram.businessmanagementapp.DatabaseApp.UseCases.UseCaseBill

import com.mukarram.businessmanagementapp.DatabaseApp.DataClasses.Bill
import com.mukarram.businessmanagementapp.DatabaseApp.DataClasses.InvalidBillException
import com.mukarram.businessmanagementapp.DatabaseApp.repositories.BillRepository

class AddBill (
    private val repository: BillRepository
)
{
    suspend operator fun invoke(bill: Bill) : Long{

        return repository.insertBill(bill)

    }
}