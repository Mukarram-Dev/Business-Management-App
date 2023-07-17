@file:OptIn(ExperimentalMaterialApi::class)

package com.mukarram.businessmanagementapp.Presentaion


import CustomTypography
import LightColors
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.*
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.mukarram.businessmanagementapp.CustomAppWidgets.AppCustomButton
import com.mukarram.businessmanagementapp.CustomAppWidgets.CustomAppBar
import com.mukarram.businessmanagementapp.CustomAppWidgets.CustomTextField

import com.mukarram.businessmanagementapp.Presentaion.create_bill.CreateBillEvent
import com.mukarram.businessmanagementapp.Presentaion.create_bill.CreateBillFieldStates
import com.mukarram.businessmanagementapp.Presentaion.create_bill.CreateBillViewModel

import com.mukarram.businessmanagementapp.Presentaion.product_stock.StockState
import com.mukarram.businessmanagementapp.Presentaion.product_stock.StockViewModel
import kotlinx.coroutines.flow.collectLatest
import java.text.SimpleDateFormat
import java.util.*


@Composable
fun BillScreen(
    navController: NavHostController,
    viewModel: CreateBillViewModel = hiltViewModel(),
    stockViewModel: StockViewModel = hiltViewModel(),
) {


    val state = stockViewModel.state.value
    Scaffold(
        topBar = { CustomAppBar("Create Bill", navController) },

        )
    {
        it
        BillContent(state, viewModel, navController)
    }
}


@OptIn(ExperimentalLayoutApi::class)
@Composable
fun BillContent(
    state: StockState,
    viewModel: CreateBillViewModel,
    navController: NavHostController,

    ) {
    val customerName = viewModel.customerName.value
    val customerAddress = viewModel.customerAddress.value
    val phoneNumber = viewModel.customerPhone.value
    val productQuantity = viewModel.productQty.value
    val salePerUnitPrice = viewModel.salePrice.value


    var selectedProduct = viewModel.selectedProduct.value
    var selectedProductId = viewModel.selectedProductId.value
    var selectedProductQty = remember { mutableStateOf(0) }
    var selectedProductPrice = remember { mutableStateOf(0.0) }
    var textFieldErrorQty = remember { mutableStateOf("") }
    var textFieldErrorPrice = remember { mutableStateOf("") }

    viewModel.billDate.value = getCurrentDate()
    viewModel.totalBill.value = calculateTotalBill(salePerUnitPrice, productQuantity)

    val context = LocalContext.current


    val focusManager = LocalFocusManager.current
    val customerNameFocusRequest = remember { FocusRequester() }
    val selectedItemFocusRequest = remember { FocusRequester() }
    val productQuantityFocusRequest = remember { FocusRequester() }
    val customerAddressFocusRequest = remember { FocusRequester() }
    val salePerUnitPriceFocusRequest = remember { FocusRequester() }

    val scaffoldState = rememberScaffoldState()
    val chipList = remember { mutableStateListOf<String>() }




    LaunchedEffect(key1 = true) {
        viewModel.eventFlow.collectLatest { event ->
            when (event) {
                is CreateBillViewModel.BillUiEvent.ShowSnackbar -> {
                    scaffoldState.snackbarHostState.showSnackbar(
                        message = event.message
                    )
                }
                is CreateBillViewModel.BillUiEvent.SaveBill -> {

                }

                else -> {}
            }
        }
    }







    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp),

        ) {

        Spacer(modifier = Modifier.height(20.dp))
        //customer name field

        CustomTextField(
            label = customerName.text.toString(),
            onValueChange = { viewModel.onEvent(CreateBillEvent.EnteredCustName(it)) },
            hint = customerName.hint,
            modifier = Modifier.fillMaxWidth(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
            imageVector = Icons.Default.Person,
            contentDescription = "person",

            )




        Spacer(modifier = Modifier.height(10.dp))

        CustomTextField(
            label = customerAddress.text.toString(),
            onValueChange = { viewModel.onEvent(CreateBillEvent.EnteredCustAddress(it)) },
            hint = customerAddress.hint,
            modifier = Modifier.fillMaxWidth(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
            imageVector = Icons.Default.LocationOn,
            contentDescription = "location",
        )



        Spacer(modifier = Modifier.height(10.dp))

        CustomTextField(
            label = phoneNumber.text.toString(),
            onValueChange = { viewModel.onEvent(CreateBillEvent.EnteredCustPhone(it)) },
            hint = phoneNumber.hint,
            modifier = Modifier.fillMaxWidth(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone),
            imageVector = Icons.Default.Phone,
            contentDescription = "phone",
        )


        Spacer(modifier = Modifier.height(10.dp))




        SelectProductBox(
            state,
            viewModel,
            chipList,
            selectedProductQty,
            selectedProductPrice
        )




        Spacer(modifier = Modifier.height(10.dp))

//        FlowRow(
//            maxItemsInEachRow = 3,
//
//            horizontalArrangement = Arrangement.SpaceAround
//        ) {
//            chipList.forEach { chip ->
//
//                Chip(
//                    onClick = { },
//                    modifier = Modifier.padding(8.dp),
//
//
//                    shape = RoundedCornerShape(10.dp),
//                    leadingIcon = {
//                        IconButton(onClick = {
//                            chipList.remove(chip)
//                        }) {
//                            Icon(
//                                imageVector = Icons.Default.Delete,
//                                contentDescription = "deleteIcon",
//                            )
//
//
//                        }
//                    },
//                    colors = ChipDefaults.chipColors(
//                        backgroundColor = Color.White,
//                        contentColor = LightColors.primary
//                    ),
//                    content = {
//                        if (chip.isNotBlank()) {
//                            Text(
//                                text = chip,
//                                style = CustomTypography.subtitle1.copy(LightColors.primary)
//                            )
//                        }
//
//                    })
//
//
//            }
//        }


        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically

        ) {


            Box(
                modifier = Modifier
                    .fillMaxWidth(0.5f)
                    .height(80.dp)
                    .padding(10.dp)
                    .border(
                        border = BorderStroke(width = 1.dp, color = LightColors.primary),
                        shape = RoundedCornerShape(20.dp)
                    )
                    .background(color = Color.White, shape = RoundedCornerShape(20.dp))

            ) {
                Row(

                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(15.dp)
                ) {

                    Icon(
                        imageVector = Icons.Default.DateRange, contentDescription = "date",
                        tint = LightColors.primary
                    )

                    Spacer(modifier = Modifier.width(5.dp))
                    Text(
                        text = viewModel.billDate.value,
                        style = CustomTypography.subtitle2
                            .copy(color = LightColors.primary.copy(alpha = 0.7f))
                    )


                }
            }


            Spacer(modifier = Modifier.width(5.dp))



            CustomTextField(
                label = productQuantity.text.toString(),
                onValueChange = { viewModel.onEvent(CreateBillEvent.EnteredProdQty(it)) },
                hint = productQuantity.hint,
                modifier = Modifier.fillMaxWidth(1.2f),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                imageVector = Icons.Default.Add,
                contentDescription = "add",
            )


        }


        Spacer(modifier = Modifier.height(10.dp))




        CustomTextField(
            label = salePerUnitPrice.text.toString(),
            onValueChange = { viewModel.onEvent(CreateBillEvent.EnteredSalePrice(it)) },
            hint = salePerUnitPrice.hint,
            modifier = Modifier.fillMaxWidth(0.5f),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            imageVector = Icons.Default.Send,
            contentDescription = "sale",
        )


        Spacer(modifier = Modifier.height(15.dp))







        Text(
            text = textFieldErrorQty.value,
            style = CustomTypography.subtitle2.copy(color = Color.Red),
        )

        Spacer(modifier = Modifier.height(10.dp))

        Text(
            text = textFieldErrorPrice.value,
            style = CustomTypography.subtitle2.copy(color = Color.Red),
        )
        Spacer(modifier = Modifier.height(16.dp))


        AppCustomButton(
            btnText = "Save Bill",
            modifier = Modifier.fillMaxWidth()
        ) {
            if (customerName.text.toString().isEmpty() ||
                customerAddress.text.toString().isEmpty() ||
                phoneNumber.text.toString().isEmpty() ||
                selectedProduct.isEmpty() ||
                productQuantity.text.toString().isEmpty() ||
                salePerUnitPrice.text.toString().isEmpty()

            ) {

                Toast.makeText(context, "Please fill all fields", Toast.LENGTH_SHORT).show()

            } else if (productQuantity.text?.toInt()!! > selectedProductQty.value) {

                textFieldErrorQty.value =
                    "Not Enough Qty,Have ${selectedProductQty.value} in Stock"

            } else if (salePerUnitPrice.text?.toDouble()!! < selectedProductPrice.value) {
                textFieldErrorPrice.value = "You Can't Sale This in this price ,You Are in Loss"
            } else {


                saveAndPrintBill(viewModel, navController)


                Toast.makeText(context, "Bill Saved", Toast.LENGTH_SHORT).show()
                Log.e("billDate", viewModel.billDate.value)
                Log.e("totalAmount", viewModel.totalBill.value.toString())
                Log.e("productId", selectedProductId)

            }
        }





        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = if (viewModel.totalBill.value == 0.0) "Total Bill: 0" else "Total bill : ${viewModel.totalBill.value}",
            color = Color.Black,
            modifier = Modifier.align(Alignment.End)

        )
    }
}


@Composable
fun SelectProductBox(

    state: StockState,
    viewModel: CreateBillViewModel,
    chipList: SnapshotStateList<String>,
    selectedProductQty: MutableState<Int>,
    selectedProductPrice: MutableState<Double>,
) {
    var selectedProduct = viewModel.selectedProduct.value
    var selectedProductId = viewModel.selectedProductId.value
    var expanded by remember { mutableStateOf(false) }

    Box(
        modifier = Modifier
            .padding(10.dp)
            .height(60.dp)
            .fillMaxWidth()
            .clip(RoundedCornerShape(20.dp))
            .border(
                width = 1.dp,
                color = LightColors.primary.copy(alpha = 0.7f),
                shape = RoundedCornerShape(20.dp)
            )

            .background(color = Color.White, shape = RoundedCornerShape(20.dp))
            .clickable { expanded = true }
    ) {
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier
                .fillMaxWidth()
                .padding(15.dp)
        ) {
            Text(
                text = if (selectedProduct.isNotEmpty()) selectedProduct else "Select Unit",
                style = CustomTypography.subtitle2
                    .copy(color = LightColors.primary.copy(alpha = 0.7f))
            )

            Icon(
                imageVector = Icons.Default.ArrowDropDown, contentDescription = "dropDown",
                tint = LightColors.primary
            )
        }

        DropdownMenu(
            expanded = expanded,
            modifier = Modifier.background(color = Color.White),

            onDismissRequest = { expanded = false },
        ) {
            state.product.forEach { unit ->
                DropdownMenuItem(
                    onClick = {
                        selectedProduct =
                            viewModel.onEvent(CreateBillEvent.SelectedProduct(unit.name))
                                .toString()
                        selectedProductId =
                            viewModel.onEvent(CreateBillEvent.SelectedProductId(unit.id.toString()))
                                .toString()
                        selectedProductQty.value = unit.product_remaining
                        selectedProductPrice.value = unit.price


                        if (chipList.contains(unit.name)) {
                            return@DropdownMenuItem
                        } else {
                            chipList.add(unit.name)
                        }

                        expanded = false
                    }
                ) {
                    Text(
                        text = unit.name,
                        style = CustomTypography.subtitle2
                            .copy(color = LightColors.primary.copy(alpha = 0.7f))
                    )
                }
            }
        }
    }
}

fun getCurrentDate(): String {
    val dateFormat = SimpleDateFormat("dd-MM-yyyy")
    val currentDate = Date()
    return dateFormat.format(currentDate)
}

// Helper function to calculate the total bill
fun calculateTotalBill(
    salePerUnitPrice: CreateBillFieldStates,
    productQuantity: CreateBillFieldStates,

    ): Double {
    val price = salePerUnitPrice.text.toString().toDoubleOrNull()
    val quantity = productQuantity.text.toString().toIntOrNull()

    if (price != null && quantity != null) {
        return price * quantity

    } else {
        return 0.0
    }

}


fun saveAndPrintBill(viewModel: CreateBillViewModel, navController: NavHostController) {
    // Implement your logic to save and print the bill

    viewModel.saveBillData()



    navController.navigate("viewBills")
}


