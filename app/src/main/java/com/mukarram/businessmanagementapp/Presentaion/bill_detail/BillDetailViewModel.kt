package com.mukarram.businessmanagementapp.Presentaion.bill_detail

import android.graphics.Color
import android.graphics.Paint
import android.graphics.pdf.PdfDocument
import com.itextpdf.text.*
import com.itextpdf.text.pdf.PdfWriter
import android.os.Environment
import android.util.Log
import androidx.compose.runtime.*
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.itextpdf.text.pdf.PdfPCell
import com.itextpdf.text.pdf.PdfPTable
import com.mukarram.businessmanagementapp.DatabaseApp.DataClasses.ProductEntry
import com.mukarram.businessmanagementapp.DatabaseApp.UseCases.UseCaseBill.BillUseCase
import com.mukarram.businessmanagementapp.DatabaseApp.UseCases.UseCaseCustomer.CustomerUseCases
import com.mukarram.businessmanagementapp.DatabaseApp.UseCases.UseCaseProduct.ProductUseCase
import com.mukarram.businessmanagementapp.DatabaseApp.UseCases.UseCaseProductBill.ProductBillUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
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

            // Fetch product details asynchronously for each product entry
            var productEntries = productBill?.productEntries ?: emptyList()






            // Update the StateFlow with the fetched details
            if (customer != null && productBill != null) {
                val productBills = productBillUseCase.getProductEntriesForBill(billId)
                val allProductEntries = mutableListOf<ProductEntry>() // Create a new list to store all product entries

                productBills.forEach { bills ->
                    allProductEntries.addAll(bills.productEntries) // Add all product entries to the new list

                }
                val products=allProductEntries.map{entry->
                    async {
                        val product= productUseCase.getProductById(entry.productId)

                        product

                    }}.awaitAll()


                _billDetailsState.value = GetBillDetails(
                    customerName = customer.name,
                    customerAdress = customer.address,
                    customerPhone = customer.phone.toString(),
                    currentBillId = billId,
                    purchaseDate = bill.date,
                    productEntries = allProductEntries, // List of products with their sale quantity and sale price
                    totalBill = productBill.totalBill,
                    products =products

                )
            }
        }
    }





    fun generateAndSavePDF(
        billDetails: GetBillDetails,
        onPdfGenerated: (Boolean) -> Unit
    ): File? {
        val directory = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)
        val file = File(directory, "bill_${billDetails.customerName}.pdf")
        file.createNewFile()

        val document = Document(PageSize.A4)
        val writer = PdfWriter.getInstance(document, FileOutputStream(file))

        document.open()

        val titleFont = Font(Font.FontFamily.TIMES_ROMAN, 14f, Font.BOLD)
        val headerFont = Font(Font.FontFamily.TIMES_ROMAN, 12f, Font.BOLD)
        val cellFont = Font(Font.FontFamily.TIMES_ROMAN, 12f)

        val title = Paragraph("Bill Details", titleFont)
        title.alignment = Element.ALIGN_CENTER
        document.add(title)

        document.add(Paragraph("Customer Name: ${billDetails.customerName}", headerFont))
        document.add(Paragraph("Customer Phone: ${billDetails.customerPhone}", headerFont))
        document.add(Paragraph("Customer Address: ${billDetails.customerAdress}", headerFont))
        document.add(Paragraph("Purchase Date: ${billDetails.purchaseDate}", headerFont))

        document.add(Paragraph("\nProducts:\n\n", headerFont))

        val table = PdfPTable(4)
        table.widthPercentage = 100f
        table.setTotalWidth(floatArrayOf(2f, 1f, 1f, 1f))

        val header = arrayOf("Product", "Qty", "Price", "Amount")
        header.forEach {
            val cell = PdfPCell(Phrase(it, headerFont))
            cell.horizontalAlignment = Element.ALIGN_CENTER
            table.addCell(cell)
        }

        billDetails.productEntries.forEachIndexed { index, productEntry ->
            val productName = billDetails.products.getOrNull(index)?.name ?: "N/A"

            table.addCell(Phrase(productName, cellFont))
            table.addCell(Phrase(productEntry.saleQuantity.toString(), cellFont))
            table.addCell(Phrase(productEntry.salePrice.toString(), cellFont))
            table.addCell(Phrase((productEntry.saleQuantity * productEntry.salePrice).toString(), cellFont))
        }

        document.add(table)

        document.add(Paragraph("\nTotal Amount: ${billDetails.totalBill}", headerFont))

        document.close()

        _generatedPdfFile.value = file
        onPdfGenerated(true)
        return generatedPdfFile.value
    }




}