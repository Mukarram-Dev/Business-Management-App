package com.mukarram.businessmanagementapp.DatabaseApp.UseCases.UseCaseProduct

import com.mukarram.businessmanagementapp.DatabaseApp.DataClasses.InvalidProductException
import com.mukarram.businessmanagementapp.DatabaseApp.DataClasses.Product
import com.mukarram.businessmanagementapp.DatabaseApp.repositories.ProductRepository

class AddProduct (
    private val repository: ProductRepository
        )
{
    @Throws(InvalidProductException::class)
    suspend operator fun invoke(product : Product){
        if (product.name.isBlank()){
            throw InvalidProductException("Product Name Can't be Empty")
        }

        if (product.product_type.isBlank()){
            throw InvalidProductException("Product Type Can't be Empty")
        }

            repository.insertProduct(product)
    }
}