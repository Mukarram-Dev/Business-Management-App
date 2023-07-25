package com.mukarram.businessmanagementapp.Presentaion.create_bill

import CustomTypography
import LightColors
import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.*
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.mukarram.businessmanagementapp.CustomAppWidgets.AppCustomButton
import com.mukarram.businessmanagementapp.CustomAppWidgets.CustomTextField
import com.mukarram.businessmanagementapp.DatabaseApp.DataClasses.Product
import com.mukarram.businessmanagementapp.DatabaseApp.DataClasses.ProductEntry
import com.mukarram.businessmanagementapp.Presentaion.product_stock.StockViewModel


@Composable
fun ProductSelectionScreen(
    navController: NavHostController,
    stockViewModel: StockViewModel = hiltViewModel(),
    viewModel: CreateBillViewModel = hiltViewModel(),
) {


    val state = stockViewModel.state.value



    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Select Products") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        },
        content = {
            it
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp)
            ) {
                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.spacedBy(8.dp),
                ) {
                    items(state.product.size) { product ->

                        ProductBox(state.product, product, viewModel)

                        Divider()

                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                AppCustomButton(
                    btnText = "Add",
                    modifier = Modifier.fillMaxWidth()
                ) {

                }
            }
        }
    )
}

@Composable
fun ProductBox(product: List<Product>, index: Int, viewModel: CreateBillViewModel) {
    // Create a list to store individual states for each product item
    val productQuantities = remember { mutableStateListOf<String>() }
    val salePerUnitPrices = remember { mutableStateListOf<String>() }
    val selectedProductIds = remember { mutableStateListOf<Long?>(null) }

    val context = LocalContext.current

    // Get the states for the current product item
    val productQuantity = productQuantities.getOrNull(index) ?: ""
    val salePerUnitPrice = salePerUnitPrices.getOrNull(index) ?: ""

    // Get the selected product ID as a mutable state
    var selectedProductId by remember { mutableStateOf(selectedProductIds.getOrNull(index)) }

    val isDialogueShow = remember { mutableStateOf(false) }
    val isPresYes = remember { mutableStateOf(false) }
    val quantity = productQuantity.toIntOrNull() ?: 0
    val price = salePerUnitPrice.toDoubleOrNull() ?: 0.0

    LossAlertDialog(
        onConfirm = {
            if (ProductEntryManager.productEntries.any { it.productId == selectedProductId }) {
                Toast.makeText(context, "Entry Already Exist", Toast.LENGTH_SHORT).show()
            }
            else{
                ProductEntryManager.addProductEntry(
                    ProductEntry(productId = selectedProductId ?: 0L,
                        saleQuantity = quantity,
                        salePrice = price)
                )
                Toast.makeText(context, "Entry Added", Toast.LENGTH_SHORT).show()

            }
            isDialogueShow.value=false
        },
        onCancel = {
            isDialogueShow.value = false
        },
        isDialogueShow
    )


    // Update the list whenever the product list changes
    LaunchedEffect(key1 = product) {
        productQuantities.clear()
        salePerUnitPrices.clear()
        selectedProductIds.clear()
        repeat(product.size) {
            productQuantities.add("")
            salePerUnitPrices.add("")
            selectedProductIds.add(null)
        }
    }



    Box(

        modifier = Modifier
            .fillMaxSize()
            .padding(10.dp)

    ) {


        Column(Modifier.fillMaxSize()) {

            Row(horizontalArrangement = Arrangement.SpaceEvenly) {

                // Checkbox
                Checkbox(
                    checked = selectedProductId == product[index].id,

                    colors = CheckboxDefaults.colors(Color.Gray),
                    onCheckedChange = {
                        selectedProductId = if (it) product[index].id else null
                    }
                )

                Column() {
                    Text(
                        text = product[index].name,
                        style = CustomTypography.subtitle2.copy(color = LightColors.primary),
                        modifier = Modifier.padding(start = 8.dp)
                    )
                    Text(
                        text = "Stock: ${product[index].product_remaining}",
                        style = CustomTypography.subtitle2.copy(color = LightColors.primary),
                        modifier = Modifier.padding(start = 8.dp)
                    )
                    Spacer(modifier = Modifier.height(10.dp))
                }
            }




            Row(horizontalArrangement = Arrangement.SpaceAround,
                modifier = Modifier.fillMaxWidth()) {
                if (selectedProductId == product[index].id && product[index].product_remaining != 0) {
                    CustomTextField(
                        label = productQuantity,
                        hint = "Quantity",
                        modifier = Modifier
                            .fillMaxWidth(0.5f),
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                        imageVector = null,
                        onValueChange = { productQuantities[index] = it },
                        contentDescription = "quantityIcon"
                    )

                    CustomTextField(
                        label = salePerUnitPrice,
                        hint = "Sale Price",
                        modifier = Modifier
                            .fillMaxWidth(0.9f),
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                        imageVector = null,
                        onValueChange = { salePerUnitPrices[index] = it },
                        contentDescription = "SaleIcon"
                    )
                    Spacer(modifier = Modifier.height(10.dp))
                }
            }

            if (selectedProductId == product[index].id
                && product[index].product_remaining != 0
            ) {
                AppCustomButton(btnText = "Add Entry", modifier = Modifier.fillMaxWidth()) {

                    ValidateEntryAndSave(productQuantity,
                        salePerUnitPrice,
                        selectedProductId,
                        context,
                        productQuantities,
                        index,
                        product,
                        salePerUnitPrices,
                        isDialogueShow,
                        )
                }
            }

        }


    }
}


private fun ValidateEntryAndSave(
    productQuantity: String,
    salePerUnitPrice: String,
    selectedProductId: Long?,
    context: Context,
    productQuantities: SnapshotStateList<String>,
    index: Int,
    product: List<Product>,
    salePerUnitPrices: SnapshotStateList<String>,
    isDialogueShow: MutableState<Boolean>,

) {

    val quantity = productQuantity.toIntOrNull() ?: 0
    val price = salePerUnitPrice.toDoubleOrNull() ?: 0.0

    if (selectedProductId == null || productQuantity.isBlank() || salePerUnitPrice.isBlank()) {
        Toast.makeText(context, "Please fill all fields", Toast.LENGTH_SHORT).show()


    } else if (productQuantities[index].toInt() > product[index].product_remaining) {
        Toast.makeText(context, "Please Write Correct Quantity", Toast.LENGTH_SHORT).show()

    } else if (salePerUnitPrices[index].toDouble() < product[index].price) {
        isDialogueShow.value = true


    } else if (ProductEntryManager.productEntries.any { it.productId == selectedProductId }) {
        Toast.makeText(context, "Entry Already Added", Toast.LENGTH_SHORT).show()
    } else {



            ProductEntryManager.addProductEntry(
                ProductEntry(productId = selectedProductId,
                    saleQuantity = quantity,
                    salePrice = price)
            )
            Toast.makeText(context, "Entry Added", Toast.LENGTH_SHORT).show()




    }
}

@Composable
fun LossAlertDialog(
    onConfirm: () -> Unit,
    onCancel: () -> Unit,
    isDialogueShow: MutableState<Boolean>,
) {


    if (isDialogueShow.value) {
        AlertDialog(
            onDismissRequest = onCancel,
            backgroundColor = Color.White,
            title = {
                Text(text = "Loss Alert",
                    style = CustomTypography.h2.copy(color = LightColors.primary))
            },
            text = {
                Text(text = "Are You Want to Sale in Loss",
                    style = CustomTypography.h2.copy(color = LightColors.primary))
            },
            confirmButton = {
                Button(onClick = onConfirm) {
                    Text(text = "Yes")
                }
            },
            dismissButton = {
                Button(onClick = onCancel) {
                    Text(text = "No")
                }
            }
        )

    }

}






