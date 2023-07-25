@file:OptIn(ExperimentalMaterialApi::class)

package com.mukarram.businessmanagementapp.Presentaion


import CustomTypography
import LightColors
import android.util.Log

import android.widget.Toast
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.mukarram.businessmanagementapp.CustomAppWidgets.AppCustomButton
import com.mukarram.businessmanagementapp.CustomAppWidgets.CustomAppBar
import com.mukarram.businessmanagementapp.CustomAppWidgets.CustomTextField
import com.mukarram.businessmanagementapp.NavigationClasses.Screen
import com.mukarram.businessmanagementapp.Presentaion.create_bill.*

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


    val selectedProducts = ProductEntryManager.productEntries
    // Collect the selected list details using the viewModelScope
    var selectedList by remember { mutableStateOf<List<SelectedListDetail>>(emptyList()) }

    LaunchedEffect(selectedProducts) {
        if (selectedProducts.isNotEmpty()) {
            val entries = viewModel.getAllEntries(selectedProducts)
            selectedList = entries

        }
    }



    val state = stockViewModel.state.value


    Scaffold(
        topBar = { CustomAppBar("Create Bill", navController) },

        )
    {
        it
        BillContent(viewModel, navController, selectedList)
    }
}


@Composable
fun BillContent(

    viewModel: CreateBillViewModel,
    navController: NavHostController,
    selectedList: List<SelectedListDetail>,


    ) {


    val customerName = viewModel.customerName.value
    val customerAddress = viewModel.customerAddress.value
    val phoneNumber = viewModel.customerPhone.value



    viewModel.billDate.value = getCurrentDate()
    val context = LocalContext.current
    val scaffoldState = rememberScaffoldState()



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




    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp)

    ) {
        item {
            // Your header content here
            Header(customerName, viewModel, customerAddress, phoneNumber, navController)
        }
        item {
            // Add your main content here
            // Example: Column with your product entries
            if (selectedList.isNotEmpty()) {
                SelectedItemDetails(selectedList)
            }
            Spacer(modifier = Modifier.height(16.dp))


                    AppCustomButton(
                        btnText = "Save Bill",
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        if (customerName.text.toString().isEmpty() ||
                            customerAddress.text.toString().isEmpty() ||
                            phoneNumber.text.toString().isEmpty()


                        ) {

                            Toast.makeText(context, "Please fill all fields", Toast.LENGTH_SHORT).show()


                        } else {


                            saveAndPrintBill(viewModel, navController)

                            Toast.makeText(context, "Bill Saved", Toast.LENGTH_SHORT).show()


                        }
                    }
        }

    }


}

@Composable
private fun Header(
    customerName: CreateBillFieldStates,
    viewModel: CreateBillViewModel,
    customerAddress: CreateBillFieldStates,
    phoneNumber: CreateBillFieldStates,
    navController: NavHostController,
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


    Box(
        modifier = Modifier
            .fillMaxWidth()
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

            Spacer(modifier = Modifier.width(15.dp))
            Text(
                text = viewModel.billDate.value,
                style = CustomTypography.subtitle2
                    .copy(color = LightColors.primary.copy(alpha = 0.7f))
            )


        }
    }
    Spacer(modifier = Modifier.height(10.dp))
    SelectProductBox(navController)
}


@Composable
fun SelectProductBox(

    navController: NavHostController,


    ) {


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
            .clickable {
                navController.navigate(Screen.SelectionScreen.route) {
                    // Pass the selectionViewModel to the destination screen
                    launchSingleTop = true
                    restoreState = true
                    popUpTo(Screen.CreateBill.route) {
                        inclusive = false
                    }
                }
            }
    ) {
        Column(Modifier.fillMaxWidth()) {
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(15.dp)
            ) {
                Text(
                    text = "Select Items",
                    style = CustomTypography.subtitle2
                        .copy(color = LightColors.primary.copy(alpha = 0.7f))
                )

                Icon(
                    imageVector = Icons.Default.ArrowDropDown,
                    contentDescription = "dropDown",
                    tint = LightColors.primary
                )
            }


        }

    }


}

@Composable
fun SelectedItemDetails(selectedList: List<SelectedListDetail>) {
    //structure of table
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = "Product",
            style = CustomTypography.subtitle2.copy(color = Color.Black, fontSize = 10.sp),
            modifier = Modifier.weight(1f)
        )

        Text(
            text = "Qty",
            style = CustomTypography.subtitle2.copy(color = Color.Black, fontSize = 10.sp),
            modifier = Modifier.width(100.dp)
        )

        Text(
            text = "Price",
            style = CustomTypography.subtitle2.copy(color = Color.Black, fontSize = 10.sp),
            modifier = Modifier.width(80.dp)
        )

        Text(
            text = "Amount",
            style = CustomTypography.subtitle2.copy(color = Color.Black, fontSize = 10.sp),
            modifier = Modifier.width(70.dp)
        )
    }
    Divider(thickness = 2.dp)



        val billTotal= mutableStateOf<Double?>(0.0)
        selectedList.forEach { list ->
            val totalPrice = list.saleQty.times(list.salePrice)
            billTotal.value = billTotal.value?.plus(totalPrice)

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
            ) {
                Text(
                    text = list.prodName,
                    style = CustomTypography.subtitle2.copy(color = LightColors.onSecondary, fontSize = 10.sp),
                    modifier = Modifier.weight(1f)
                )

                Text(
                    text = "${list.saleQty}",
                    style = CustomTypography.subtitle2.copy(color = LightColors.onSecondary, fontSize = 10.sp),
                    modifier = Modifier.width(100.dp)
                )

                Text(
                    text = "${list.salePrice}",
                    style = CustomTypography.subtitle2.copy(color = LightColors.onSecondary, fontSize = 10.sp),
                    modifier = Modifier.width(90.dp)
                )

                Text(
                    text = totalPrice.toString(),
                    style = CustomTypography.subtitle2.copy(color = LightColors.onSecondary, fontSize = 10.sp),
                    modifier = Modifier.width(60.dp)
                )
            }

            Spacer(modifier = Modifier.height(5.dp))
            Divider()
        }

    //total bill Text

    // Total bill value aligned to the right end
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Spacer(modifier = Modifier.weight(0.6f))
        Text(
            text = "Total: ${billTotal.value ?: ""}",
            style = CustomTypography.subtitle2.copy(color = LightColors.onSecondary, fontSize = 10.sp),
        )
    }




}



fun getCurrentDate(): String {
    val dateFormat = SimpleDateFormat("dd-MM-yyyy")
    val currentDate = Date()
    return dateFormat.format(currentDate)
}


fun saveAndPrintBill(viewModel: CreateBillViewModel, navController: NavHostController) {
    // Implement your logic to save and print the bill

    viewModel.saveBillData()



    navController.navigate("viewBills") {
        // Set the destination to the "View Bills" screen
        popUpTo("create_bill") {
            // Remove the "Create Bill" screen from the back stack
            inclusive = false
        }
    }
}


