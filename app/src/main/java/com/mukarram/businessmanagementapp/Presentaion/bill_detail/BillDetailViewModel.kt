package com.mukarram.businessmanagementapp.Presentaion.bill_detail

import android.content.Intent
import android.graphics.Color
import android.graphics.Paint
import android.graphics.pdf.PdfDocument
import android.os.Environment
import android.util.Log
import android.widget.Toast
import androidx.compose.runtime.*
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.sp
import androidx.core.content.FileProvider
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mukarram.businessmanagementapp.BuildConfig
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
import java.io.File
import java.io.FileOutputStream
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

    private val _generatedPdfFile = mutableStateOf<File?>(null)
    val generatedPdfFile: State<File?> = _generatedPdfFile


    suspend fun getBillDetailsById(billId: Long) {

        viewModelScope.launch(Dispatchers.IO) {
            // Fetch the bill details by ID
            val bill = billUseCase.getBillsById(billId)
            val customer = bill?.let { customerUseCases.getCustomerById(it.customerId) }
            val productBill = bill?.let { productBillUseCase.getProductBillById(it.id!!) }
            val products =
                withContext(Dispatchers.IO) { productUseCase.getProductById(productBill?.productId!!) }
            // Update the StateFlow with the fetched details
            if (customer != null && productBill != null) {
                _billDetailsState.value = GetBillDetails(
                    customerName = customer.name,
                    customerAdress = customer.address,
                    customerPhone = customer.phone.toString(),
                    currentBillId = billId,
                    purchaseDate = bill.date,
                    productName = products?.name ?: "Prod",
                    saleQty = productBill.saleQuantity ?: 0,
                    salePrice = productBill.salePrice ?: 0.0,
                    totalBill = productBill.totalBill
                )


            }


        }
    }


    fun generateAndSavePDF(
        billDetails: GetBillDetails,
        onPdfGenerated: (Boolean) -> Unit

    ): File? {
        val pdfDocument = PdfDocument()
        val pageInfo = PdfDocument.PageInfo.Builder(595, 842, 1).create()
        val page = pdfDocument.startPage(pageInfo)
        val canvas = page.canvas
        val paint = Paint().apply {
            color = Color.BLACK
            textSize = 12.sp.value
        }

        val lines = mutableListOf<String>()
        lines.apply {
            add("Bill Details:")
            add("Customer Name: ${billDetails.customerName}")
            add("Customer Phone: ${billDetails.customerPhone}")
            add("Customer Address: ${billDetails.customerAdress}")
            add("Purchase Date: ${billDetails.purchaseDate}")
            add("")
            add("Products:")
            add("Product\t\t\t\t\tQty\t\t\t\t\t\tPrice\t\t\t\t\tAmount")
            add("${billDetails.productName}\t\t\t\t${billDetails.saleQty}\t\t\t\t${billDetails.salePrice}" +
                    "\t\t\t\t${billDetails.saleQty * billDetails.salePrice}")
            add("")
            add("Total Amount: ${billDetails.totalBill}")
        }

        var y = 50f
        for (line in lines) {
            canvas.drawText(line, 50f, y, paint)
            y += 25f // Move to the next line
        }

        pdfDocument.finishPage(page)
        val directory = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)
        val file = File(directory, "bill_${billDetails.customerName}.pdf")
        file.createNewFile()
        val outputStream = FileOutputStream(file)
        pdfDocument.writeTo(outputStream)
        pdfDocument.close()

        _generatedPdfFile.value = file
        onPdfGenerated(true)
        return generatedPdfFile.value

    }



}