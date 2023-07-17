package com.mukarram.businessmanagementapp.DatabaseApp.UseCases.UseCaseProduct

import com.mukarram.businessmanagementapp.DatabaseApp.DataClasses.Product
import com.mukarram.businessmanagementapp.DatabaseApp.repositories.ProductRepository
import com.mukarram.businessmanagementapp.util.OrderType
import com.mukarram.businessmanagementapp.util.ProductOrder
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map


class GetAllProducts(
    private val repository: ProductRepository
) {
    operator fun invoke(
        productOrder: ProductOrder = ProductOrder.Date(OrderType.Descending)
    ): Flow<List<Product>> {
        return repository.getProducts().map { product ->
            when (productOrder.orderType) {
                is OrderType.Ascending -> {
                    when (productOrder) {
                        is ProductOrder.ProductName -> product.sortedBy { it.name.lowercase() }
                        is ProductOrder.Date -> product.sortedBy { it.id }
                    }


                }
                is OrderType.Descending -> {
                    when (productOrder) {
                        is ProductOrder.ProductName -> product.sortedByDescending { it.name.lowercase() }
                        is ProductOrder.Date -> product.sortedByDescending { it.id }
                    }
                }
            }
        }
    }
    }