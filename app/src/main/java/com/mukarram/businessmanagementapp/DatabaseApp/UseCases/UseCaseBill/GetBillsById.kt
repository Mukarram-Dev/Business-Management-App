package com.mukarram.businessmanagementapp.DatabaseApp.UseCases.UseCaseBill

import com.mukarram.businessmanagementapp.DatabaseApp.DataClasses.Bill
import com.mukarram.businessmanagementapp.DatabaseApp.repositories.BillRepository

class GetBillsById(
    private val repository: BillRepository
) {
        suspend operator fun invoke(billNo: Long?):Bill? {

        return repository.getBillById(billNo)
    }
}

