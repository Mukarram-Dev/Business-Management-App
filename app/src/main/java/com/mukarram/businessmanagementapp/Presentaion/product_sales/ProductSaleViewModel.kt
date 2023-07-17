package com.mukarram.businessmanagementapp.Presentaion.product_sales

import android.util.Log
import androidx.lifecycle.ViewModel
import com.mukarram.businessmanagementapp.DatabaseApp.UseCases.UseCaseBill.BillUseCase
import com.mukarram.businessmanagementapp.DatabaseApp.UseCases.UseCaseCustomer.CustomerUseCases
import com.mukarram.businessmanagementapp.DatabaseApp.UseCases.UseCaseProduct.ProductUseCase
import com.mukarram.businessmanagementapp.DatabaseApp.UseCases.UseCaseProductBill.ProductBillUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class ProductSaleViewModel @Inject constructor(
    private val customerUseCases: CustomerUseCases,
    private val billUseCase: BillUseCase,
    private val productUseCase: ProductUseCase,
    private val productBillUseCase: ProductBillUseCase,
) : ViewModel(){
    private val _SaleDetailsState = MutableStateFlow(emptyList<SaleDetailsModel>())
    val saleDetailState: StateFlow<List<SaleDetailsModel>> = _SaleDetailsState

    suspend fun fetchSaleDetails() {
        val bills = withContext(Dispatchers.IO) { billUseCase.getAllBills.execute() }


        val saleDetailsList = mutableListOf<SaleDetailsModel>()

        bills.collect { billList ->
            billList.forEach { bill ->
                val customer =
                    withContext(Dispatchers.IO) { customerUseCases.getCustomerById(bill.customerId) }
                val productBill =
                    withContext(Dispatchers.IO) { bill.id?.let { productBillUseCase.getProductBillById(it) } }
                val products= withContext(Dispatchers.IO){productUseCase.getProductById(productBill?.productId!!)}

                if (customer != null && products!=null) {
                    saleDetailsList.add(
                        SaleDetailsModel(
                            customerName = customer.name,
                            totalBill = productBill?.totalBill ?: 0.0,
                            purchaseDate = bill.date,
                            productName = products.name,
                            productType=products.product_type,
                            saleQty = productBill?.saleQuantity?:0,
                            salePrice = productBill?.salePrice?:0.0

                        )
                    )
                    Log.e("billId",bill.id.toString())
                }
            }

            // Update the StateFlow with the collected bill details list
            _SaleDetailsState.value = saleDetailsList
        }
    }

}