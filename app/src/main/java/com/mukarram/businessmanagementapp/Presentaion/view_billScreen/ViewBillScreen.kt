package com.mukarram.businessmanagementapp.Presentaion.view_billScreen

import CustomTypography
import LightColors
import android.util.Log
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.toLowerCase
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.PopupProperties
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.mukarram.businessmanagementapp.CustomAppWidgets.CustomAppBar
import com.mukarram.businessmanagementapp.Presentaion.product_sales.DatePickers
import com.mukarram.businessmanagementapp.R
import java.util.*


@Composable
fun BillReportScreen(
    navController: NavHostController,
    viewModel: BillScreenViewModel = hiltViewModel(),
) {

    LaunchedEffect(Unit) {
        viewModel.fetchBillDetails()
    }

    // Collect the StateFlow using collectAsState
    val billDetailsState by viewModel.billDetailsState.collectAsState()



    Scaffold(
        topBar = { CustomAppBar(title = "Bill Book", navController) },
        content = {
            it
            Column(modifier = Modifier.fillMaxSize()) {

                BillReportContent(navController, viewModel, billDetailsState)
            }
        },
        floatingActionButton = { FloatingButton(navController) }
    )

}

@Composable
fun FloatingButton(navController: NavHostController) {


    FloatingActionButton(
        shape = RoundedCornerShape(20.dp),
        backgroundColor = LightColors.primary.copy(alpha = 0.9f),
        modifier = Modifier
            .padding(8.dp)
            .fillMaxWidth(0.4f),
        onClick = {
            navController.navigate("create_bill")
        },
        content = {

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(5.dp),
                horizontalArrangement = Arrangement.SpaceAround,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Create Bill",
                    style = CustomTypography.h2.copy(color = Color.White)
                )


                Icon(imageVector = Icons.Default.ArrowForward,
                    contentDescription = "ArrowForward",
                    tint = Color.White)
            }

        }
    )

}

@Composable
fun BillReportContent(
    navController: NavHostController,
    viewModel: BillScreenViewModel,
    billDetailsState: List<ViewBillDetails>,
) {

    val selectedCustomer = remember { mutableStateOf("") }
    val selectedDate = remember { mutableStateOf("") }


    //search text field

    SearchTextFieldRow(selectedCustomer)


    Spacer(modifier = Modifier.height(15.dp))


    // Product selection dropdown and date picker
    Row(
        modifier = Modifier
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        BillSelection(billDetailsState, selectedCustomer)

        // Date picker

        DatePickers(selectedDate)

    }
    Spacer(modifier = Modifier.height(16.dp))

    BillListView(selectedCustomer.value,
        selectedDate.value,
        navController,
        viewModel,
        billDetailsState)

}

@Composable
fun SearchTextFieldRow(searchProduct: MutableState<String>) {
    Spacer(modifier = Modifier.height(20.dp))
    Row(
        horizontalArrangement = Arrangement.SpaceAround,
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()

    ) {

        TextField(
            colors = TextFieldDefaults.textFieldColors(backgroundColor = Color.White),
            shape = RoundedCornerShape(20.dp),
            leadingIcon = {
                Icon(
                    imageVector = Icons.Default.Search,
                    contentDescription = "search",
                    tint = LightColors.primary
                )
            },
            value = searchProduct.value,
            onValueChange = { searchProduct.value = it },
            textStyle = CustomTypography.subtitle2
                .copy(color = LightColors.primary.copy(alpha = 0.7f)),
            label = {
                Text(
                    "Search Product",
                    style = CustomTypography.subtitle2
                        .copy(color = LightColors.primary.copy(alpha = 0.7f))
                )
            },
            singleLine = true,
            modifier = Modifier
                .fillMaxWidth(0.8f)
                .padding(10.dp)
                .clip(RoundedCornerShape(20.dp))
                .border(
                    width = 1.dp,
                    color = LightColors.primary.copy(alpha = 0.7f),
                    shape = RoundedCornerShape(20.dp)
                )
        )


        Icon(
            painter = painterResource(id = R.drawable.ic_baseline_filter_alt_24),
            tint = LightColors.primary,
            contentDescription = "filter"
        )
        Icon(
            painter = painterResource(id = R.drawable.ic_baseline_sort_24),
            tint = LightColors.primary,
            contentDescription = "sort"
        )

    }
}

@Composable
fun BillSelection(customerList: List<ViewBillDetails>, selectedCustomer: MutableState<String>) {
    var expanded by remember { mutableStateOf(false) }
    var selectedIndex by remember { mutableStateOf(0) }

    Box(
        modifier = Modifier
            .fillMaxWidth(0.5f)
            .height(50.dp)

            .background(color = Color.White, shape = RoundedCornerShape(10.dp))
            .clickable { expanded = true }
            .border(
                border = BorderStroke(width = 1.dp, LightColors.primary.copy(alpha = 0.7f)),
                shape = RoundedCornerShape(10.dp)
            )

    ) {
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp)
        ) {


            Spacer(modifier = Modifier.width(5.dp))
            Text(
                if (selectedCustomer.value.isNotEmpty()) selectedCustomer.value else "Select Customer",
                style = CustomTypography.subtitle2
                    .copy(color = LightColors.primary.copy(alpha = 0.7f))
            )
            Icon(
                imageVector = Icons.Default.ArrowDropDown, contentDescription = "dropDownButton",
                tint = LightColors.primary, modifier = Modifier.size(20.dp)

            )

            DropdownMenu(
                expanded = expanded,
                offset = DpOffset(0.dp, 0.dp),
                properties = PopupProperties(focusable = true),
                onDismissRequest = { expanded = false },
                modifier = Modifier
                    .fillMaxWidth(0.5f)
                    .background(color = Color.White)
            ) {
                customerList.forEachIndexed { index, customer ->
                    DropdownMenuItem(

                        onClick = {
                            selectedCustomer.value = customer.customerName
                            selectedIndex = index
                            expanded = false
                        }
                    ) {
                        Text(text = customer.customerName, style = CustomTypography.h2)
                    }
                }
            }


        }
    }


}


@Composable
fun BillListView(
    selectedBill: String,
    selectedDate: String,
    navController: NavHostController,
    viewModel: BillScreenViewModel,
    billDetailsState: List<ViewBillDetails>,
) {
    var selectedIndex by remember { mutableStateOf(0) }


    val filteredBill = billDetailsState.filter { bill ->

        (selectedBill.isEmpty() ||
                bill.customerName.lowercase(Locale.getDefault()) == selectedBill.lowercase(
            Locale.getDefault()))
                &&
                (selectedDate.isEmpty() || bill.purchaseDate == selectedDate)

    }
    if (filteredBill.isEmpty()) {
        Text("No Bills found")
    } else {
        LazyColumn(

            modifier = Modifier
                .fillMaxSize()
                .padding(10.dp)


        ) {

            items(filteredBill.size) { bill -> // Pass each individual ViewBillDetails item

                if (filteredBill.isEmpty()) {
                    Text("no data found/inserted", style = CustomTypography.h2)
                } else {
                    BillBox(filteredBill, navController, bill, viewModel)
                    Spacer(modifier = Modifier.height(5.dp))
                }

            }
        }
    }


}

@Composable
fun BillBox(
    bill: List<ViewBillDetails>,
    navController: NavHostController,
    billIndex: Int,
    viewModel: BillScreenViewModel,
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()

            .padding(10.dp)
            .background(color = Color.White, shape = RoundedCornerShape(20.dp))
            .clickable {
                viewModel.setSelectedBillId(bill[billIndex].currentBillId!!)
                navController.navigate("bill_detail/${bill[billIndex].currentBillId}")

            }
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_baseline_receipt_24),
                    contentDescription = "receipt",
                    tint = LightColors.onSecondary,
                    modifier = Modifier.size(38.dp)
                )
                Spacer(modifier = Modifier.width(10.dp))
                Column(horizontalAlignment = Alignment.Start) {
                    Text(
                        text = if (bill[billIndex].customerName != "") {
                            bill[billIndex].customerName

                        } else {
                            "no customer found"
                        },
                        style = CustomTypography.subtitle1.copy(color = LightColors.onSecondary)
                    )

                    Text(

                        text = if (bill[billIndex].purchaseDate != "") {
                            bill[billIndex].purchaseDate
                        } else {
                            "no date found"
                        },
                        style = CustomTypography.subtitle1.copy(color = LightColors.onSecondary)
                    )
                }
            }
            Text(
                text = if (bill[billIndex].totalBill != 0.0) {
                    "Rs. ${bill[billIndex].totalBill}"

                } else {
                    "no amount found"
                },
                style = CustomTypography.subtitle1.copy(color = LightColors.onSecondary),

                )
        }
    }

}
