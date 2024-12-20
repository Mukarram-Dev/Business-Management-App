package com.mukarram.businessmanagementapp.DatabaseApp.UseCases.UseCaseCustomer

import com.mukarram.businessmanagementapp.DatabaseApp.DataClasses.Customer
import com.mukarram.businessmanagementapp.DatabaseApp.repositories.CustomerRepository

class GetCustomerById(
    private val  repository: CustomerRepository
) {
    suspend operator fun invoke(customerId: Long?): Customer? {

        return repository.getCustomerById(customerId)
    }
}