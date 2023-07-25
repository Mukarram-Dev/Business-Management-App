package com.mukarram.businessmanagementapp.Presentaion.product_sales

import android.util.Log
import androidx.lifecycle.ViewModel
import com.mukarram.businessmanagementapp.DatabaseApp.DataClasses.ProductEntry
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
                val customer = withContext(Dispatchers.IO) { customerUseCases.getCustomerById(bill.customerId) }
                val productBill = withContext(Dispatchers.IO) { bill.id?.let { productBillUseCase.getProductBillById(it) } }
                val entriesList = mutableListOf<ProductEntry>() // Create a new list for each iteration

                val entries = productBill?.productEntries?.map { entry ->
                    ProductEntry(
                        productId = entry.productId,
                        salePrice = entry.salePrice,
                        saleQuantity = entry.saleQuantity
                    )
                }

                if (entries != null) {
                    entriesList.addAll(entries)
                }

                entriesList.forEach {
                    val products = withContext(Dispatchers.IO) { productUseCase.getProductById(it.productId) }
                    Log.e("product get:", products?.name ?: "null")
                    if (customer != null && products != null) {
                        saleDetailsList.add(
                            SaleDetailsModel(
                                customerName = customer.name,
                                totalBill = productBill?.totalBill ?: 0.0,
                                purchaseDate = bill.date,
                                productName = products.name,
                                productType = products.product_type,
                                saleQty = it.saleQuantity,
                                salePrice = it.salePrice
                            )
                        )
                    }
                }

            }
            _SaleDetailsState.value = saleDetailsList
        }



    }


}