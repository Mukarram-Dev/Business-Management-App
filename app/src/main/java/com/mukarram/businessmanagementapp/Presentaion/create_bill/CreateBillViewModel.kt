package com.mukarram.businessmanagementapp.Presentaion.create_bill

import android.util.Log
import com.mukarram.businessmanagementapp.DatabaseApp.DataClasses.ProductBill
import androidx.compose.runtime.*
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
    private val savedStateHandle: SavedStateHandle,
) : ViewModel(), (Product, Int, Double) -> Unit {

    // Customer fields
    private val _customerName = mutableStateOf(CreateBillFieldStates(hint = "Customer Name..."))
    val customerName: State<CreateBillFieldStates> = _customerName

    private val _customerAddress =
        mutableStateOf(CreateBillFieldStates(hint = "Customer Address..."))
    val customerAddress: State<CreateBillFieldStates> = _customerAddress

    private val _customerPhone = mutableStateOf(CreateBillFieldStates(hint = "Customer Phone..."))
    val customerPhone: State<CreateBillFieldStates> = _customerPhone


    // Other fields
    val billDate = mutableStateOf("")

    val profitState: MutableState<Double?> = mutableStateOf(null)
    private val _eventFlow = MutableSharedFlow<BillUiEvent>()

    val eventFlow = _eventFlow.asSharedFlow()
    private var currentBillId: Long? = null

    private var currentCustomerId: Long? = null

    private val selectedProducts = ProductEntryManager.productEntries



    val totalBill = mutableStateOf(0.0)





    // Function to save the bill and associated product entries
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

                // Save the product entries with the associated billId

                selectedProducts.forEach { productEntry ->
                    calculateTotalBill(selectedProducts, totalBill)
                    val productBill = ProductBill(
                        billId = currentBillId ?: 0L,
                        totalBill = totalBill.value,
                        productEntries = listOf(productEntry)
                    )
                    productBillUseCase.addProductBill(productBill)
                }



                selectedProducts.forEach { productEntry ->

                    updateProductDetails(
                        productEntry.productId,
                        productEntry.saleQuantity,
                        productEntry.salePrice,

                        )
                }
                ProductEntryManager.clearProductEntry()


                // Emit a success event
                _eventFlow.emit(BillUiEvent.ShowSnackbar("Bill saved successfully"))
            } catch (e: Exception) {
                // Handle any exceptions and emit an error event if needed
                _eventFlow.emit(BillUiEvent.ShowSnackbar("Error saving bill"))
            }
        }
    }

    private fun calculateTotalBill(
        selectedProducts: List<ProductEntry>,
        totalBill: MutableState<Double>,
    ) {
        if (selectedProducts.isNotEmpty()) {
            selectedProducts.forEachIndexed { index, productEntry ->

                totalBill.value += selectedProducts[index].saleQuantity * selectedProducts[index].salePrice

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
            is CreateBillEvent.SelectedBillDate -> {
                billDate.value = event.value
            }


            is CreateBillEvent.TotalBill -> {
                totalBill.value = event.value.toDouble()
            }

        }
    }


    private suspend fun updateProductDetails(
        productId: Long,
        newSales: Int,
        salePrice: Double,

        ) {

        viewModelScope.launch {
            val product = productUseCase.getProductById(productId)
            product?.let {

                val totalProduct = product.quantity
                val remaining = mutableStateOf<Int>(0)
                val addSales = mutableStateOf<Int>(0)
                if (totalProduct > newSales && totalProduct != 0 && product.product_remaining != 0) {
                    remaining.value = product.product_remaining - newSales
                    addSales.value = product.saleQty + newSales


                } else {
                    _eventFlow.emit(BillUiEvent.ShowSnackbar("greater than existing"))
                }



                val updatedProduct = it.copy(
                    product_remaining = remaining.value,
                    saleQty = addSales.value,

                    )
                productUseCase.updateProduct(updatedProduct)
            }
        }


    }

    suspend fun getAllEntries(selectedProducts: List<ProductEntry>): List<SelectedListDetail> {
        return selectedProducts.map { productEntry ->
            val product = productUseCase.getProductById(productEntry.productId)
            product?.let {
                SelectedListDetail(
                    prodName = product.name,
                    saleQty = productEntry.saleQuantity,
                    salePrice = productEntry.salePrice,
                    total = productEntry.saleQuantity * productEntry.salePrice
                )
            }
        }.filterNotNull() // Filter out null values if any
    }


    sealed class BillUiEvent {
        data class ShowSnackbar(val message: String) : BillUiEvent()
        object SaveBill : BillUiEvent()
    }


    override fun invoke(p1: Product, p2: Int, p3: Double) {
        TODO("Not yet implemented")
    }

}
data class SelectedListDetail(
    val prodName : String,
    val saleQty : Int,
    val salePrice : Double,
    val total   : Double
)
