package com.mukarram.businessmanagementapp.DatabaseApp.UseCases.UseCaseProduct

import com.mukarram.businessmanagementapp.DatabaseApp.DataClasses.Product
import com.mukarram.businessmanagementapp.DatabaseApp.repositories.ProductRepository

class GetProductById(
    private val repository: ProductRepository
)
{
     suspend operator fun invoke(id: Long) : Product? {

         return repository.getProductById(id)
     }

}