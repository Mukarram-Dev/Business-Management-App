package com.mukarram.businessmanagementapp.DatabaseApp.UseCases.UseCaseProduct

data class ProductUseCase(

    val getAllProducts: GetAllProducts,
    val addProduct: AddProduct,
    val getProductById: GetProductById,
    val deleteProduct: DeleteProduct,
    val updateProduct: UpdateProduct


            )
