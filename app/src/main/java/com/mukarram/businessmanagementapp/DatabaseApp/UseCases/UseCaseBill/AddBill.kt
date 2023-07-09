package com.mukarram.businessmanagementapp.DatabaseApp.UseCases

import com.mukarram.businessmanagementapp.DatabaseApp.DataClasses.Bill
import com.mukarram.businessmanagementapp.DatabaseApp.DataClasses.InvalidBillException
import com.mukarram.businessmanagementapp.DatabaseApp.DataClasses.InvalidProductException
import com.mukarram.businessmanagementapp.DatabaseApp.DataClasses.Product
import com.mukarram.businessmanagementapp.DatabaseApp.repositories.BillRepository
import com.mukarram.businessmanagementapp.DatabaseApp.repositories.ProductRepository

class AddBill (
    private val repository: BillRepository
)
{
    @Throws(InvalidBillException::class)
    suspend operator fun invoke(bill: Bill){

        if (bill.date.isBlank()){
            throw InvalidBillException("Date Can't be Empty")
        }

        repository.insertBill(bill)

    }
}