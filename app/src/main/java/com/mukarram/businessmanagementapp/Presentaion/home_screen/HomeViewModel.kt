package com.mukarram.businessmanagementapp.Presentaion.home_screen

import android.util.Log
import android.view.View
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableDoubleStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mukarram.businessmanagementapp.DatabaseApp.UseCases.UseCaseBill.BillUseCase
import com.mukarram.businessmanagementapp.DatabaseApp.UseCases.UseCaseCustomer.CustomerUseCases
import com.mukarram.businessmanagementapp.DatabaseApp.UseCases.UseCaseProduct.ProductUseCase
import com.mukarram.businessmanagementapp.DatabaseApp.UseCases.UseCaseProductBill.ProductBillUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val billUseCase: BillUseCase,
    private val productUseCase: ProductUseCase,
    private val productBillUseCase: ProductBillUseCase
) : ViewModel() {

    // Use MutableSharedFlow to accumulate profit and loss values
    private val _profitFlow = MutableSharedFlow<Double?>(replay = 1)
    private val _lossFlow = MutableSharedFlow<Double?>(replay = 1)

    val profitState: StateFlow<Double?> = _profitFlow.stateIn(viewModelScope, SharingStarted.WhileSubscribed(), initialValue = null)
    val lossState: StateFlow<Double?> = _lossFlow.stateIn(viewModelScope, SharingStarted.WhileSubscribed(), initialValue = null)


    //for adding refresher on home
    // MutableState to act as the refresh trigger
    private val _refreshTrigger = MutableStateFlow(Unit)
    val refreshTrigger: StateFlow<Unit> = _refreshTrigger.asStateFlow()

    // Function to trigger refresh
    fun refresh() {
        viewModelScope.launch {
            _refreshTrigger.emit(Unit)
        }
    }

    init {
        viewModelScope.launch {
            getBills()
        }
    }

     suspend fun getBills() {
        val productBills = productBillUseCase.getAllProductBills.execute()
        var totalProfit = 0.0
        var totalLoss = 0.0

        productBills.collect { productBill ->
            productBill.forEach { prod ->
                val billProfit = prod.productEntries.sumByDouble { productEntry ->
                    val product = productUseCase.getProductById(productEntry.productId)
                    val costPrice = product?.price?.times(productEntry.saleQuantity)
                    val salePrice = productEntry.salePrice.times(productEntry.saleQuantity)
                    salePrice - (costPrice ?: 0.0)
                }
                if (billProfit >= 0) {
                    totalProfit += billProfit
                } else {
                    totalLoss += billProfit // Add the negative value to totalLoss
                }

                Log.e("Profit view: ", billProfit.toString())
            }
            _profitFlow.emit(totalProfit)
            _lossFlow.emit(totalLoss)
            Log.e("Total Profit view: ", totalProfit.toString())
            Log.e("Total Loss view: ", totalLoss.toString())
        }
    }
}

