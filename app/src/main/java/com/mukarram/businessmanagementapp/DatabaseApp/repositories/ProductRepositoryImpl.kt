package com.mukarram.businessmanagementapp.DatabaseApp.repositories

import com.mukarram.businessmanagementapp.DatabaseApp.DaoFiles.ProductDao
import com.mukarram.businessmanagementapp.DatabaseApp.DataClasses.Product
import kotlinx.coroutines.flow.Flow

class ProductRepositoryImpl (
    private val dao : ProductDao) : ProductRepository
{
    override fun getProducts(): Flow<List<Product>> {
        return dao.getAllProducts()
    }

    override suspend fun getProductById(id: Long): Product? {
        return dao.getProductById(id)
    }

    override suspend fun insertProduct(product: Product) {
        return dao.insertProduct(product)
    }

    override suspend fun updateProduct(product: Product) {
        return dao.updateProduct(product)
    }

    override suspend fun deleteProduct(product: Product) {
        return dao.deleteProduct(product)
    }

}
