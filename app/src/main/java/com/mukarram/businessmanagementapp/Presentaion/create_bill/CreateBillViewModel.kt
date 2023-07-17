package com.mukarram.businessmanagementapp.Presentaion.create_bill

import com.mukarram.businessmanagementapp.DatabaseApp.DataClasses.ProductBill
import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mukarram.businessmanagementapp.DatabaseApp.DataClasses.*
import com.mukarram.businessmanagementapp.DatabaseApp.UseCases.UseCaseBill.BillUseCase
import com.mukarram.businessmanagementapp.DatabaseApp.UseCases.UseCaseCustomer.CustomerUseCases
import com.mukarram.businessmanagementapp.DatabaseApp.UseCases.UseCaseProduct.ProductUseCase
import com.mukarram.businessmanagementapp.DatabaseApp.UseCases.UseCaseProductBill.ProductBillUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CreateBillViewModel @Inject constructor(
    private val billUseCase: BillUseCase,
    private val customerUseCases: CustomerUseCases,
    private val productBillUseCase: ProductBillUseCase,
    private val productUseCase: ProductUseCase,
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {

    // Customer fields
    private val _customerName = mutableStateOf(CreateBillFieldStates(hint = "Customer Name..."))
    val customerName: State<CreateBillFieldStates> = _customerName

    private val _customerAddress =
        mutableStateOf(CreateBillFieldStates(hint = "Customer Address..."))
    val customerAddress: State<CreateBillFieldStates> = _customerAddress

    private val _customerPhone = mutableStateOf(CreateBillFieldStates(hint = "Customer Phone..."))
    val customerPhone: State<CreateBillFieldStates> = _customerPhone

    // Product fields
    private val _productQty = mutableStateOf(CreateBillFieldStates(hint = "ProductQty."))
    val productQty: State<CreateBillFieldStates> = _productQty

    private val _salePrice = mutableStateOf(CreateBillFieldStates(hint = "Sale Price..."))
    val salePrice: State<CreateBillFieldStates> = _salePrice

    // Other fields
    val billDate = mutableStateOf("")
        //product details
    var selectedProduct = mutableStateOf("")
    var selectedProductId = mutableStateOf("")




    val totalBill = mutableStateOf(0.0)

    private val _eventFlow = MutableSharedFlow<BillUiEvent>()
    val eventFlow = _eventFlow.asSharedFlow()


    private var currentBillId: Long? = null
    private var currentProductSaleId: Long? = null
    private var currentCustomerId: Long? = null


    fun saveBillData() {
        viewModelScope.launch {
            try {
                // Save the customer data first
                val customer = Customer(
                    id = currentCustomerId,
                    name = customerName.value.text.toString(),
                    address = customerAddress.value.text.toString(),
                    phone = customerPhone.value.text!!.toLong()
                )
                currentCustomerId = customerUseCases.addCustomer(customer)

                // Save the bill with the corresponding customer ID
                val bill = Bill(
                    id = currentBillId,
                    customerId = currentCustomerId,
                    date = billDate.value
                )
                currentBillId = billUseCase.addBill(bill)

                // Save the product bill with the appropriate bill and product IDs
                val productId = selectedProductId.value.toLong()
                val productBill = ProductBill(
                    productId = productId,
                    billId = currentBillId,
                    salePrice = salePrice.value.text?.toDouble(),
                    saleQuantity = productQty.value.text?.toInt(),
                    totalBill = totalBill.value
                )
                productBillUseCase.addProductBill(productBill)

                // Emit a success event
                _eventFlow.emit(BillUiEvent.ShowSnackbar("Bill saved successfully"))

                updateProductDetails(selectedProductId.value.toLong(),productQty.value.text?.toInt()!!)
            } catch (e: Exception) {
                // Handle any exceptions and emit an error event if needed
                _eventFlow.emit(BillUiEvent.ShowSnackbar("Error saving bill"))
                Log.e("database Error", e.message.toString())
            }
        }
    }


    init {
        // Load customer and product details from SavedStateHandle if available
        loadCustomerData()
        loadBillData()



        savedStateHandle.get<Long>("billId")?.let { billId ->

            if (billId != -1L) {
                viewModelScope.launch {

                    billUseCase.getBillsById(billId)?.also { bill ->
                        currentBillId = bill.id
                        billDate.value = bill.date
                        currentCustomerId = bill.customerId

                        productBillUseCase.getProductBillById(billId)
                            ?.also { productBill: ProductBill ->
                                currentProductSaleId = selectedProductId.value.toLong()
                                _productQty.value =
                                    _productQty.value.copy(text = productBill.saleQuantity.toString())
                                _salePrice.value =
                                    _salePrice.value.copy(text = productBill.salePrice.toString())
                                selectedProductId.value = productBill.productId.toString()
                            }


                    }
                }

            } else {
                currentBillId = null
            }


        }
    }

    private fun loadBillData() {
        viewModelScope.launch(Dispatchers.IO) {
            val billId = savedStateHandle.get<Long>("billId") ?: return@launch

            if (billId != -1L) {
                billUseCase.getBillsById(billId)?.also { bill ->
                    currentBillId = bill.id
                    billDate.value = bill.date
                    currentCustomerId = bill.customerId

                    productBillUseCase.getProductBillById(billId)?.also { productBill: ProductBill ->
                        currentProductSaleId = selectedProductId.value.toLong()
                        _productQty.value =
                            _productQty.value.copy(text = productBill.saleQuantity.toString())
                        _salePrice.value =
                            _salePrice.value.copy(text = productBill.salePrice.toString())
                        selectedProductId.value = productBill.productId.toString()
                    }
                }
            } else {
                currentBillId = null
            }
        }
    }

    private fun loadCustomerData() {
        viewModelScope.launch(Dispatchers.IO) {
            val customerId = savedStateHandle.get<Long>("customerId") ?: return@launch

            if (customerId != -1L) {
                customerUseCases.getCustomerById(customerId)?.also { customer ->
                    currentCustomerId = customer.id
                    _customerName.value = _customerName.value.copy(text = customer.name)
                    _customerPhone.value =
                        _customerPhone.value.copy(text = customer.phone.toString())
                    _customerAddress.value =
                        _customerAddress.value.copy(text = customer.address)
                }
            }
        }
    }


    fun onEvent(event: CreateBillEvent) {
        when (event) {
            is CreateBillEvent.EnteredCustAddress -> {
                _customerAddress.value = _customerAddress.value.copy(text = event.value)
            }
            is CreateBillEvent.EnteredCustName -> {
                _customerName.value = _customerName.value.copy(text = event.value)
            }
            is CreateBillEvent.EnteredCustPhone -> {
                _customerPhone.value = _customerPhone.value.copy(text = event.value)
            }
            is CreateBillEvent.EnteredSalePrice -> {
                _salePrice.value = _salePrice.value.copy(text = event.value)
            }
            is CreateBillEvent.SelectedBillDate -> {
                billDate.value = event.value
            }
            is CreateBillEvent.EnteredProdQty -> {
                _productQty.value = _productQty.value.copy(text = event.value)
            }
            is CreateBillEvent.SelectedProduct -> {
                selectedProduct.value = event.value
            }
            is CreateBillEvent.SelectedProductId -> {
                selectedProductId.value = event.value
            }
            is CreateBillEvent.TotalBill -> {
                totalBill.value = event.value.toDouble()
            }

        }
    }


    private suspend fun updateProductDetails(productId: Long, newSales: Int) {
        val product = productUseCase.getProductById(productId)
        product?.let {
            val totalProduct=product.quantity
            val remaining= mutableStateOf<Int>(0)
            if(totalProduct>newSales && totalProduct!=0 && product.product_remaining!=0){
                remaining.value=product.product_remaining-newSales
            }
            else {
                _eventFlow.emit(BillUiEvent.ShowSnackbar("greater than existing"))
            }
            val addSales=product.saleQty + newSales

            val updatedProduct = it.copy(
               product_remaining = remaining.value,
               saleQty = addSales,

            )
            productUseCase.updateProduct(updatedProduct)
        }
    }








    sealed class BillUiEvent {
        data class ShowSnackbar(val message: String) : BillUiEvent()
        object SaveBill : BillUiEvent()
    }



}
