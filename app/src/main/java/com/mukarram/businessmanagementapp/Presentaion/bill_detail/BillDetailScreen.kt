package com.mukarram.businessmanagementapp.Presentaion

import CustomTypography
import LightColors
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.mukarram.businessmanagementapp.CustomAppWidgets.AppCustomButton
import com.mukarram.businessmanagementapp.CustomAppWidgets.CustomAppBar
import com.mukarram.businessmanagementapp.Presentaion.bill_detail.BillDetailViewModel
import com.mukarram.businessmanagementapp.Presentaion.bill_detail.GetBillDetails


@Composable
fun BillDetailScreen(
    navController: NavHostController,
    billId: Long,
    viewModel: BillDetailViewModel = hiltViewModel(),

) {
    // Get the bill ID from the arguments passed by the previous screen


    // Fetch bill details using LaunchedEffect and the bill ID
    LaunchedEffect(billId) {
        viewModel.getBillDetailsById(billId)
    }

    // Fetch bill details using LaunchedEffect and the bill ID
    val billDetailsState by viewModel.billDetailsState.collectAsState()











    Scaffold(
        topBar = { CustomAppBar("Bill Detail",navController)  },

        modifier = Modifier
            .background(color = LightColors.background),
        content = {
            it
            Box(
                modifier = Modifier
                    .fillMaxSize()

            ) {
                Column(
                    modifier = Modifier
                        .align(Alignment.TopCenter)
                        .padding(horizontal = 16.dp, vertical = 24.dp)
                ) {
                    BillDetailHeader(billDetailsState)

                    Divider()

                    Spacer(modifier = Modifier.height(16.dp))

                    BillTable(billDetailsState)
                }

                Box(
                    modifier = Modifier.align(Alignment.BottomCenter)
                ) {
                    BottomBar()
                }
            }

        }
    )
}

@Composable
fun BottomBar() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(color = Color.White)

    ) {


        Row(
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically, modifier = Modifier.fillMaxWidth()
        ) {

            AppCustomButton(btnText = "Download", modifier = Modifier.fillMaxWidth(0.5f)) {

            }

            AppCustomButton(btnText = "Share", modifier = Modifier.fillMaxWidth(1f)) {

            }


        }

    }
}

@Composable
fun BillTable(bill: GetBillDetails?) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = "Product",
            style = CustomTypography.subtitle2.copy(color = Color.Black),
            modifier = Modifier.weight(1f)
        )

        Text(
            text = "Qty",
            style = CustomTypography.subtitle2.copy(color = Color.Black),
            modifier = Modifier.width(80.dp)
        )

        Text(
            text = "Price",
            style = CustomTypography.subtitle2.copy(color = Color.Black),
            modifier = Modifier.width(80.dp)
        )

        Text(
            text = "Amount",
            style = CustomTypography.subtitle2.copy(color = Color.Black),
            modifier = Modifier.width(80.dp)
        )
    }
    Divider(thickness = 2.dp)

    //Lazy Column for items

    Spacer(modifier = Modifier.height(5.dp))

    LazyColumn {
        items(listOfNotNull(bill).size) { item ->
            BillItem(item,bill)
            Divider()
            Spacer(modifier = Modifier.height(10.dp))
        }
    }

    Spacer(modifier = Modifier.height(16.dp))


    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier.fillMaxWidth()
    ) {
        Text(
            text = "Total Amount: ",
            style = CustomTypography.h2.copy(color = LightColors.onSecondary),

            )
        Text(
            text = "Rs. ${bill?.totalBill}",
            style = CustomTypography.h2.copy(color = LightColors.onSecondary),
            modifier = Modifier.padding(end = 20.dp)

            )
    }
}

@Composable
fun BillDetailHeader(bill: GetBillDetails?) {


    Column(horizontalAlignment = Alignment.Start) {


        Text(
            text = "Customer Name: ${bill?.customerName}",
            style = CustomTypography.h2.copy(color = LightColors.onSecondary),
            modifier = Modifier.padding(bottom = 8.dp)
        )

        Text(
            text = "Customer Phone: ${bill?.customerPhone}",
            style = CustomTypography.subtitle1.copy(color = LightColors.onSecondary),
            modifier = Modifier.padding(bottom = 16.dp)
        )

        Text(
            text = "Customer Address: ${bill?.customerAdress}",
            style = CustomTypography.h2.copy(color = LightColors.onSecondary),
            modifier = Modifier.padding(bottom = 8.dp)
        )

        Text(
            text = "Purchase Date: ${bill?.purchaseDate}",
            style = CustomTypography.subtitle1.copy(color = LightColors.onSecondary),
            modifier = Modifier.padding(bottom = 16.dp)
        )


    }


}

@Composable
fun BillItem(index: Int, bill: GetBillDetails?) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = bill?.productName?:"prod",
            style = CustomTypography.subtitle1.copy(color = LightColors.onSecondary),
            modifier = Modifier.weight(1f)
        )



        Text(
            text = "${bill?.saleQty}",
            style = CustomTypography.subtitle2.copy(color = LightColors.onSecondary),
            modifier = Modifier.width(80.dp)
        )

        Text(
            text = "${bill?.salePrice}",
            style = CustomTypography.subtitle2.copy(color = LightColors.onSecondary),
            modifier = Modifier.width(80.dp)
        )

        Text(
            text = "Rs. ${bill?.saleQty?.times((bill.salePrice))}",
            style = CustomTypography.subtitle2.copy(color = LightColors.onSecondary),
            modifier = Modifier.width(80.dp)
        )
    }
}
