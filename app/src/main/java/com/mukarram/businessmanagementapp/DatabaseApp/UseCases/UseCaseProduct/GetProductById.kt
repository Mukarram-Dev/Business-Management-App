package com.mukarram.businessmanagementapp.DatabaseApp.UseCases

import com.mukarram.businessmanagementapp.DatabaseApp.DataClasses.Product
import com.mukarram.businessmanagementapp.DatabaseApp.repositories.ProductRepository

class GetProductById(
    private val repository: ProductRepository
)
{
     suspend operator fun invoke(id :Int) : Product? {

         return repository.getProductById(id)
     }

}