package com.mukarram.businessmanagementapp.DatabaseApp.UseCases.UseCaseCustomer

data class CustomerUseCases (

    val addCustomer:AddCustomer,
    val getCustomerById: GetCustomerById,
    val getAllCustomers : GetAllCustomers,
)