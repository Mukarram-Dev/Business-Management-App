package com.mukarram.businessmanagementapp.Presentaion.view_billScreen

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mukarram.businessmanagementapp.DatabaseApp.UseCases.UseCaseBill.BillUseCase
import com.mukarram.businessmanagementapp.DatabaseApp.UseCases.UseCaseCustomer.CustomerUseCases
import com.mukarram.businessmanagementapp.DatabaseApp.UseCases.UseCaseProduct.ProductUseCase
import com.mukarram.businessmanagementapp.DatabaseApp.UseCases.UseCaseProductBill.ProductBillUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class BillScreenViewModel @Inject constructor(
    private val customerUseCases: CustomerUseCases,
    private val billUseCase: BillUseCase,
    private val productUseCase: ProductUseCase,
    private val productBillUseCase: ProductBillUseCase,
) : ViewModel() {

    // Use MutableStateFlow for billDetailsState
    private val _billDetailsState = MutableStateFlow(emptyList<ViewBillDetails>())
    val billDetailsState: StateFlow<List<ViewBillDetails>> = _billDetailsState

    private val _selectedBillId = mutableStateOf<Long?>(null)
    val selectedBillId: State<Long?> = _selectedBillId

    fun setSelectedBillId(billId: Long) {
        _selectedBillId.value = billId
    }

    // Function to fetch bill details and update the StateFlow
    fun fetchBillDetails() {
        viewModelScope.launch {
            val bills = withContext(Dispatchers.IO) { billUseCase.getAllBills.execute() }

            val billDetailsList = mutableListOf<ViewBillDetails>()

            bills.collect { billList ->
                Log.e("Bill Count", "${billList.size}")
                billList.forEach { bill ->
                    val customer = withContext(Dispatchers.IO) { customerUseCases.getCustomerById(bill.customerId) }
                    val productBill = withContext(Dispatchers.IO) {
                        bill.id?.let { productBillUseCase.getProductBillById(it) }
                    }

                    if (customer != null) {
                        billDetailsList.add(
                            ViewBillDetails(
                                customerName = customer.name,
                                purchaseDate = bill.date,
                                totalBill = productBill?.totalBill ?: 0.0,
                                currentBillId = bill.id
                            )
                        )
                    }
                }

                // Update the StateFlow with the collected bill details list
                _billDetailsState.value = billDetailsList
            }
        }
    }
}
