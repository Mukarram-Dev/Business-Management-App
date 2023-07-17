package com.mukarram.businessmanagementapp.DatabaseApp.repositories

import com.mukarram.businessmanagementapp.DatabaseApp.DaoFiles.CustomerDao
import com.mukarram.businessmanagementapp.DatabaseApp.DataClasses.Customer
import kotlinx.coroutines.flow.Flow

class CustomRepositoryImpl(
  private  val customerDao: CustomerDao
) : CustomerRepository{
    override fun getAllCustomer(): Flow<List<Customer>> {
        return  customerDao.getAllCustomers()
    }

    override suspend fun getCustomerById(id: Long?): Customer? {
        return  customerDao.geCustomerById(id)
    }

    override suspend fun insertCustomer(customer: Customer) : Long {
        return customerDao.insertCustomer(customer)
    }
}