package com.mukarram.businessmanagementapp.DatabaseApp.UseCases.UseCaseProduct

import com.mukarram.businessmanagementapp.DatabaseApp.DataClasses.Product
import com.mukarram.businessmanagementapp.DatabaseApp.repositories.ProductRepository

class UpdateProduct(
    private val repository: ProductRepository
)
{

    suspend operator fun invoke(product: Product) {
        return repository.updateProduct(product)
    }


}