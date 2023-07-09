package com.mukarram.businessmanagementapp.Activities

import CustomTypography
import LightColors
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.mukarram.businessmanagementapp.CustomAppWidgets.CustomAppBar


data class BillItem(
    val name: String,
    val quantity: Int,
    val price: Double,
    val amount: Double
)

data class Bill(
    val billNumber: String,
    val date: String,
    val customerName: String,
    val customerPhone: String,
    val totalAmount: Double,
    val items: List<BillItem>,
)



@Composable
fun BillDetailScreen(navController: NavHostController) {
    val bill = Bill(
        billNumber = "B001",
        date = "2023-06-25",
        totalAmount = 70.0,
        customerName = "Ijaz Bhatti",
        customerPhone = "0310300123",
        items = listOf(
            BillItem("Product A", 2, 10.0, 20.0),
            BillItem("Product B", 3, 15.0, 45.0),
            BillItem("Product C", 1, 5.0, 5.0)
        )
    )




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
                    BillDetailHeader(bill)

                    Divider()

                    Spacer(modifier = Modifier.height(16.dp))

                    BillTable(bill)
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
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically, modifier = Modifier.fillMaxWidth()
        ) {

            Button(
                onClick = { /*TODO*/ },
                border = ButtonDefaults.outlinedBorder,
                modifier = Modifier.fillMaxWidth(0.5f)

                ) {

                Text(text = "Download")
            }

            OutlinedButton(
                onClick = { /*TODO*/ },
                border = ButtonDefaults.outlinedBorder,
                modifier = Modifier.fillMaxWidth(0.8f)
            ) {
                Text(text = "Share")
            }

        }

    }
}

@Composable
fun BillTable(bill: Bill) {
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
        items(bill.items) { item ->
            BillItem(item)
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
            text = "Rs. ${bill.totalAmount}",
            style = CustomTypography.h2.copy(color = LightColors.onSecondary),
            modifier = Modifier.padding(end = 20.dp)

            )
    }
}

@Composable
fun BillDetailHeader(bill: Bill) {


    Column(horizontalAlignment = Alignment.Start) {

        Text(
            text = "Bill Number: ${bill.billNumber}",
            style = CustomTypography.h2.copy(color = LightColors.onSecondary),
            modifier = Modifier.padding(bottom = 8.dp)
        )

        Text(
            text = "Date: ${bill.date}",
            style = CustomTypography.subtitle1.copy(color = LightColors.onSecondary),
            modifier = Modifier.padding(bottom = 16.dp)
        )


        Text(
            text = "Customer Name: ${bill.customerName}",
            style = CustomTypography.h2.copy(color = LightColors.onSecondary),
            modifier = Modifier.padding(bottom = 8.dp)
        )

        Text(
            text = "Customer Phone: ${bill.customerPhone}",
            style = CustomTypography.subtitle1.copy(color = LightColors.onSecondary),
            modifier = Modifier.padding(bottom = 16.dp)
        )


    }


}

@Composable
fun BillItem(item: BillItem) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = item.name,
            style = CustomTypography.subtitle1.copy(color = LightColors.onSecondary),
            modifier = Modifier.weight(1f)
        )



        Text(
            text = "${item.quantity}",
            style = CustomTypography.subtitle2.copy(color = LightColors.onSecondary),
            modifier = Modifier.width(80.dp)
        )

        Text(
            text = "${item.price}",
            style = CustomTypography.subtitle2.copy(color = LightColors.onSecondary),
            modifier = Modifier.width(80.dp)
        )

        Text(
            text = "Rs. ${item.amount}",
            style = CustomTypography.subtitle2.copy(color = LightColors.onSecondary),
            modifier = Modifier.width(80.dp)
        )
    }
}
