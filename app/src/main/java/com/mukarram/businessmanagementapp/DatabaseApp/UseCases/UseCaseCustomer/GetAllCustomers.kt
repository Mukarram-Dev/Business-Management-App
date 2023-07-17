package com.mukarram.businessmanagementapp.DatabaseApp.UseCases.UseCaseCustomer


import com.mukarram.businessmanagementapp.DatabaseApp.DataClasses.Customer

import com.mukarram.businessmanagementapp.DatabaseApp.repositories.CustomerRepository
import kotlinx.coroutines.flow.Flow


class GetAllCustomers(
    private val repository: CustomerRepository,
) {
        suspend fun execute(): Flow<List<Customer>> {
        return repository.getAllCustomer()
    }

}
