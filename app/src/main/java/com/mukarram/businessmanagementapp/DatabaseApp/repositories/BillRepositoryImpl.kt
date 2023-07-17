package com.mukarram.businessmanagementapp.DatabaseApp.repositories

import com.mukarram.businessmanagementapp.DatabaseApp.DaoFiles.BillDao
import com.mukarram.businessmanagementapp.DatabaseApp.DataClasses.Bill
import kotlinx.coroutines.flow.Flow

class BillRepositoryImpl(
    private val billDao :BillDao
) : BillRepository{
    override fun getAllBills(): Flow<List<Bill>> {
        return billDao.getAllBills()
    }

    override suspend fun getBillById(Id: Long?): Bill? {
        return  billDao.getBillById(Id)
    }

    override suspend fun insertBill(bill: Bill) : Long {
        return billDao.insertBill(bill)
    }

}