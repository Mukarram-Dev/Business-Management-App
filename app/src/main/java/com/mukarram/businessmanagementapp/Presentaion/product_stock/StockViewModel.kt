package com.mukarram.businessmanagementapp.Presentaion.product_stock

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mukarram.businessmanagementapp.DatabaseApp.DataClasses.Product
import com.mukarram.businessmanagementapp.DatabaseApp.UseCases.UseCaseProduct.ProductUseCase
import com.mukarram.businessmanagementapp.util.OrderType
import com.mukarram.businessmanagementapp.util.ProductOrder
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject


@HiltViewModel
class StockViewModel @Inject constructor(
    private val useCase: ProductUseCase
) : ViewModel() {

    private val _state = mutableStateOf(StockState())
    val state: State<StockState> = _state

    private var getProductJob: Job? = null

    private var deleteProduct: Product? = null

    init {
        getProducts(ProductOrder.Date(OrderType.Descending))
    }

    fun onEvent(event: StockEvent) {
        when (event) {
            is StockEvent.getProduct -> {

            }
            is StockEvent.DeleteProduct -> {

            }
            is StockEvent.Order -> {
                if (state.value.productOrder::class == event.productOrder::class &&
                    state.value.productOrder.orderType == event.productOrder.orderType
                ) {
                    return
                }
                getProducts(event.productOrder)

            }
        }

    }

    private fun getProducts(productOrder: ProductOrder) {
        getProductJob?.cancel()
        getProductJob = useCase.getAllProducts(productOrder)
            .onEach { products ->
            _state.value = state.value.copy(
                product =products ,
                productOrder=productOrder
            )

        }
            .launchIn(viewModelScope)
    }


}