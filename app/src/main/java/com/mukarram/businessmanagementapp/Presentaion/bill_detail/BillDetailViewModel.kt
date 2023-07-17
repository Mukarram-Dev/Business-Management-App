package com.mukarram.businessmanagementapp.Presentaion.bill_detail

import android.util.Log
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
class BillDetailViewModel @Inject constructor(
    private val customerUseCases: CustomerUseCases,
    private val billUseCase: BillUseCase,
    private val productUseCase: ProductUseCase,
    private val productBillUseCase: ProductBillUseCase,

    ) : ViewModel() {
    private val _billDetailsState = MutableStateFlow<GetBillDetails?>(null)
    val billDetailsState: StateFlow<GetBillDetails?> = _billDetailsState


    suspend fun getBillDetailsById(billId: Long) {

        viewModelScope.launch(Dispatchers.IO) {
            // Fetch the bill details by ID
            val bill = billUseCase.getBillsById(billId)
            val customer = bill?.let { customerUseCases.getCustomerById(it.customerId) }
            val productBill = bill?.let { productBillUseCase.getProductBillById(it.id!!) }
            val products= withContext(Dispatchers.IO){productUseCase.getProductById(productBill?.productId!!)}
            // Update the StateFlow with the fetched details
            if (customer != null && productBill != null) {
                _billDetailsState.value= GetBillDetails(
                    customerName = customer.name,
                    customerAdress = customer.address,
                    customerPhone = customer.phone.toString(),
                    currentBillId = billId,
                    purchaseDate = bill.date,
                    productName = products?.name?:"Prod",
                    saleQty = productBill.saleQuantity ?: 0,
                    salePrice = productBill.salePrice ?: 0.0,
                    totalBill=productBill.totalBill
                )
                // Log the details to verify if they are fetched correctly
                Log.d("BillDetailViewModel", "Customer Name: ${customer.name}")
                Log.d("BillDetailViewModel", "Customer Phone: ${customer.phone}")
                Log.d("BillDetailViewModel", "Customer Address: ${customer.address}")
                Log.d("BillDetailViewModel", "Purchase Date: ${bill.date}")

            }



        }
    }
}