package com.mukarram.businessmanagementapp.DatabaseApp.UseCases.UseCaseCustomer

import com.mukarram.businessmanagementapp.DatabaseApp.DataClasses.Bill
import com.mukarram.businessmanagementapp.DatabaseApp.DataClasses.Customer
import com.mukarram.businessmanagementapp.DatabaseApp.DataClasses.InvalidBillException
import com.mukarram.businessmanagementapp.DatabaseApp.DataClasses.InvalidCustomerException
import com.mukarram.businessmanagementapp.DatabaseApp.repositories.CustomerRepository

class AddCustomer(
    private val repository: CustomerRepository
) {

    @Throws(InvalidCustomerException::class)
    suspend operator fun invoke(customer: Customer): Long {
        if (customer.name.isBlank()) {
            throw InvalidCustomerException("Customer Name Can't be Empty")
        }
        if (customer.address.isBlank()) {
            throw InvalidCustomerException("Customer Address Can't be Empty")
        }

        return repository.insertCustomer(customer)
    }
}
