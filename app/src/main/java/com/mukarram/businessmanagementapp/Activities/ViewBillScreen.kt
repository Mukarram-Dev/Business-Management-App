package com.mukarram.businessmanagementapp.Activities

import CustomTypography
import LightColors
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.runtime.*
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.PopupProperties
import androidx.navigation.NavHostController
import com.mukarram.businessmanagementapp.CustomAppWidgets.CustomAppBar
import com.mukarram.businessmanagementapp.R


data class BillReport(
    val bilName: String,
    val bilDate: String,
    val bilTotalAmount: Int,
    val bilNo: Long,
)

@Composable
fun BillReportScreen(navController: NavHostController) {
    Scaffold(
        topBar = { CustomAppBar(title = "Bill Book",navController)  },
        content = {
            it
            Column(modifier = Modifier.fillMaxSize()) {

                BillReportContent(navController)
            }
        },
        floatingActionButton = { FloatingButton(navController) }
    )
}

@Composable
fun FloatingButton(navController: NavHostController) {


        FloatingActionButton(
            shape=RoundedCornerShape(20.dp),
            backgroundColor= LightColors.primary.copy(alpha = 0.9f),
            modifier = Modifier.padding(8.dp),
            onClick = {
                navController.navigate("create_bill")
            },
            content = { Text(text = "Create Bill",
                style = CustomTypography.h2.copy(color = Color.White)) }
        )

}

@Composable
fun BillReportContent(navController: NavHostController) {
    val billNamesList = remember { mutableStateListOf("Sale Bulb", "Sale Lights", "Sale LED TV") }
    val selectedProduct = remember { mutableStateOf("") }
    val selectedDate = remember { mutableStateOf("") }

    var billList = remember {
        mutableStateListOf(
            BillReport("Sale Bulb", "29-10-22", 1000,1101),
            BillReport("Sale Lights", "08-06-23", 200,1102),
            BillReport("Sale LED TV", "10-02-21", 600,1103),
        )
    }


    //search text field
    OutlinedTextField(
        value = "",
        onValueChange = {},
        label = {
            Text(
                "Search Product",
                style = CustomTypography.subtitle2
                    .copy(color = LightColors.primary.copy(alpha = 0.7f))
            )
        },
        singleLine = true,
        modifier = Modifier
            .fillMaxWidth(0.7f)
            .clip(RoundedCornerShape(10.dp))
            .padding(10.dp)
    )

    Spacer(modifier = Modifier.height(15.dp))


    // Product selection dropdown and date picker
    Row(
        modifier = Modifier
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceAround
    ) {
        BillSelection(billNamesList, selectedProduct)

        // Date picker

        DatePickers(selectedDate)

    }
    Spacer(modifier = Modifier.height(16.dp))

    BillListView(billList,selectedProduct.value,selectedDate.value,navController)

}

@Composable
fun BillSelection(productList: List<String>, selectedProduct: MutableState<String>) {
    var expanded by remember { mutableStateOf(false) }
    var selectedIndex by remember { mutableStateOf(0) }





    Box(
        modifier = Modifier

            .fillMaxWidth(0.35f)
            .clip(RoundedCornerShape(10.dp))
            .background(
                LightColors.onSecondary
            )
            .clickable(onClick = { expanded = true })
    )
    {

        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .padding(horizontal = 10.dp, vertical = 5.dp)
                .fillMaxWidth()
        ) {
            Text(
                productList[selectedIndex],
            )
            Icon(imageVector = Icons.Default.ArrowDropDown, contentDescription = "DropIcon")


            DropdownMenu(
                expanded = expanded,
                offset = DpOffset(0.dp, 0.dp),
                properties = PopupProperties(focusable = true),
                onDismissRequest = { expanded = false },
                modifier = Modifier.fillMaxWidth()
            ) {
                productList.forEachIndexed { index, product ->
                    DropdownMenuItem(

                        onClick = {
                            selectedProduct.value = product
                            selectedIndex = index
                            expanded = false
                        }
                    ) {
                        Text(text = product, style = CustomTypography.h2)
                    }
                }
            }
        }


    }
}


@Composable
fun BillListView(
    billList: SnapshotStateList<BillReport>,
    selectedBill: String,
    selectedDate: String,
    navController: NavHostController
) {
    var selectedIndex by remember { mutableStateOf(0) }

    val filteredBill = billList.filter { bill ->

        (selectedBill.isEmpty() || bill.bilName == selectedBill) &&
                (selectedDate.isEmpty() || bill.bilDate == selectedDate)

    }
    if (filteredBill.isEmpty())
    {
        Text("No Bills found")
    }
    else {
        LazyColumn(modifier = Modifier.fillMaxSize()) {

            items(filteredBill) { bill ->

                BillBox(bill,navController)

                Divider()

                Spacer(modifier = Modifier.height(5.dp))


            }
        }
    }



}

@Composable
fun BillBox(bill: BillReport, navController: NavHostController) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(10.dp))
            .background(color = LightColors.onBackground)
            .clickable { navController.navigate("bill_detail") }
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
                    modifier = Modifier.size(42.dp)
                )
                Spacer(modifier = Modifier.width(10.dp))
                Column(horizontalAlignment = Alignment.Start) {
                    Text(text = bill.bilName, style = CustomTypography.h2.copy(color = LightColors.onSecondary))
                    Spacer(modifier = Modifier.height(5.dp))
                    Text(text = bill.bilDate, style = CustomTypography.subtitle1.copy(color = LightColors.onSecondary))
                }
            }
            Text(
                text = "Rs.${bill.bilTotalAmount}",
                style = CustomTypography.h2.copy(color = LightColors.onSecondary),

            )
        }
    }

}
