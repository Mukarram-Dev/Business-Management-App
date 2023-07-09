package com.mukarram.businessmanagementapp.DatabaseApp.UseCases.UseCaseBill

import com.mukarram.businessmanagementapp.DatabaseApp.DataClasses.Bill
import com.mukarram.businessmanagementapp.DatabaseApp.repositories.BillRepository

class GetBillsByNo(
    private val repository: BillRepository
) {
        suspend operator fun invoke(billNo: Long):Bill? {

        return repository.getBillByNo(billNo)
    }
}

