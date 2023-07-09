package com.mukarram.businessmanagementapp.DatabaseApp.UseCases

import com.mukarram.businessmanagementapp.DatabaseApp.DataClasses.Product
import com.mukarram.businessmanagementapp.DatabaseApp.repositories.ProductRepository

class DeleteProduct(
    private val repository: ProductRepository
)
{
    suspend operator fun invoke (product: Product) {
    return  repository.deleteProduct(product)
}

}