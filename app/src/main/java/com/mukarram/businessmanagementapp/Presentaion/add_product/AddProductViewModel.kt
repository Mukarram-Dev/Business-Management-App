package com.mukarram.businessmanagementapp.Presentaion.add_product

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.*
import com.mukarram.businessmanagementapp.DatabaseApp.DataClasses.InvalidProductException
import com.mukarram.businessmanagementapp.DatabaseApp.DataClasses.Product
import com.mukarram.businessmanagementapp.DatabaseApp.UseCases.UseCaseProduct.ProductUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddProductViewModel @Inject constructor(
    private val productUseCase: ProductUseCase,
    savedStateHandle: SavedStateHandle,

    ) : ViewModel() {

    private val _productName = mutableStateOf(ProductTextFieldState(hint = "Enter Product Name..."))

    val productName: State<ProductTextFieldState> = _productName

    private val _productQty =
        mutableStateOf(ProductTextFieldState(hint = "Enter Product Quantity..."))

    val productQty: State<ProductTextFieldState> = _productQty

    private val _productPrice =
        mutableStateOf(ProductTextFieldState(hint = "Enter Product Price..."))

    val productPrice: State<ProductTextFieldState> = _productPrice

    val productType =   mutableStateOf("")

    private val _eventFlow = MutableSharedFlow<UiEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

    private var currentProductId: Long? = null

    init {
        savedStateHandle.get<Long?>("productId")?.let { productId ->
            if (productId != -1L) {
                viewModelScope.launch {
                    productUseCase.getProductById(productId)?.also { product ->
                        currentProductId = product.id
                        _productName.value = productName.value.copy(
                            text = product.name
                        )
                        _productPrice.value = productPrice.value.copy(
                            text = "${product.price}"
                        )
                        _productQty.value = productQty.value.copy(
                            text = "${product.quantity}"
                        )
                        productType.value=product.product_type
                    }

                }
            }
        }
    }


    fun onEvent(event: AddProductEvents) {
        when (event) {
            is AddProductEvents.EnteredProdName -> {
                _productName.value = productName.value.copy(
                    text = event.value
                )
            }
            is AddProductEvents.EnteredProdQty -> {
                _productQty.value = productQty.value.copy(
                    text = event.value
                )
            }
            is AddProductEvents.EnteredProdPrice -> {
                _productPrice.value = productPrice.value.copy(
                    text = event.value
                )
            }
            is AddProductEvents.SelectProdType -> {
                productType.value= event.value
            }

            is AddProductEvents.saveProduct -> {
                viewModelScope.launch {
                    try {
                        productUseCase.addProduct(
                            Product(
                                id = currentProductId,
                                name = productName.value.text,
                                price = productPrice.value.text.toDouble(),
                                quantity = productQty.value.text.toInt(),
                                saleQty = 0,
                                product_type = productType.value,
                                product_remaining = productQty.value.text.toInt()
                            )
                        )
                        _eventFlow.emit(UiEvent.SaveProduct)
                    } catch (e: InvalidProductException) {
                        _eventFlow.emit(
                            UiEvent.ShowSnackbar(
                                message = e.message ?: "Could not save Product"
                            )
                        )
                    }
                }
            }

        }
    }


}

sealed class UiEvent {
    data class ShowSnackbar(val message: String) : UiEvent()
    object SaveProduct : UiEvent()
}
