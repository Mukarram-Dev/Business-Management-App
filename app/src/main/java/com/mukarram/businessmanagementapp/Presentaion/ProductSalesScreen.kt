package com.mukarram.businessmanagementapp.Activities

import CustomTypography
import LightColors
import android.app.DatePickerDialog

import android.widget.DatePicker
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.runtime.*
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.PopupProperties
import androidx.navigation.NavHostController
import com.mukarram.businessmanagementapp.CustomAppWidgets.CustomAppBar
import java.util.*

data class ProductSales(
    val productName: String,
    val CustomerName: String,
    val productSalesQunatity: Int,
    val saleDate: String,
    val amountProduct: Int,
)



@Composable

fun ProductSalesScreen(navController: NavHostController) {
    Scaffold(
        topBar = { CustomAppBar(title = "Product Sales",navController)  },

        ) {
        it
        ProductSalesContent()

    }
}


@Composable
fun ProductSalesContent() {
    val productDetailList = remember { mutableStateListOf<ProductSales>() }

    val productList = remember { mutableStateListOf("LED Light 20W", "Tube Light 200W", "Fans 100W") }
    val selectedProduct = remember { mutableStateOf("") }
    val selectedDate = remember { mutableStateOf("") }
    val filteredSales = remember {
        mutableStateListOf(
            ProductSales("LED Light 20W","Khalid Baloch", 20, "27-06-2023", 500),
            ProductSales("Tube Light 200W", "Younas Jutt",40, "26-06-2023", 7000),
            ProductSales("Fans 100W", "Mian Channu",50, "30-06-2023", 5600),
            ProductSales("Charger 50W", "Babar Azam",60, "24-06-2023", 5400),
            ProductSales("Bottle 1.5 Ltr", "Saith Zulifqar",70, "10-06-2023", 100),
        )
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 8.dp)
    ) {


        Spacer(modifier = Modifier.height(16.dp))

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
                .fillMaxWidth(5f)
                .clip(RoundedCornerShape(10.dp))
        )

        Spacer(modifier = Modifier.height(15.dp))


        // Product selection dropdown and date picker
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceAround
        ) {
            ProductSelection(productList, selectedProduct)

            // Date picker

                DatePickers(selectedDate)


        }


        Spacer(modifier = Modifier.height(16.dp))

        // Filtered sales list
        SalesList(filteredSales, selectedProduct.value, selectedDate.value)
    }
}

@Composable
fun ProductSelection(productList: List<String>, selectedProduct: MutableState<String>) {
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
fun DatePickers(selectedDate: MutableState<String>) {
    val selectedDateText = if (selectedDate.value.isNotEmpty()) {
        "Selected Date: ${selectedDate.value}"
    } else {
        "No date selected"
    }


    val mContext = LocalContext.current
    // Declaring integer values
    // for year, month and day
    val mYear: Int
    val mMonth: Int
    val mDay: Int

    // Initializing a Calendar
    val mCalendar = Calendar.getInstance()

    // Fetching current year, month and day
    mYear = mCalendar.get(Calendar.YEAR)
    mMonth = mCalendar.get(Calendar.MONTH)
    mDay = mCalendar.get(Calendar.DAY_OF_MONTH)

    mCalendar.time = Date()

    // Declaring a string value to
    // store date in string format
    val mDate = remember { mutableStateOf("") }

    // Declaring DatePickerDialog and setting
    // initial values as current values (present year, month and day)
    val mDatePickerDialog = DatePickerDialog(
        mContext,
        { _: DatePicker, mYear: Int, mMonth: Int, mDayOfMonth: Int ->
            mDate.value = "$mDayOfMonth-${mMonth + 1}-$mYear"
        }, mYear, mMonth, mDay
    )
    // Creating a button that on
    // click displays/shows the DatePickerDialog



    var currentDate by remember { mutableStateOf("") }
    currentDate=mDate.value





    Box(modifier = Modifier
        .fillMaxWidth(0.5f)
        .clip(RoundedCornerShape(15.dp))
        .background(color = LightColors.onSecondary)
        .padding(horizontal = 10.dp, vertical = 5.dp)
        .clickable(onClick = {
            mDatePickerDialog.show()
        })
    )
    {
        Row(modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceAround,
        verticalAlignment = Alignment.CenterVertically) {


            Icon(Icons.Default.DateRange, contentDescription = "iconDate")


            Text(
                currentDate,
                style = CustomTypography.subtitle2
                    .copy(color = Color.White)
            )


        }
    }



}

@Composable
fun SalesList(
    filteredSales: SnapshotStateList<ProductSales>,
    selectedProduct: String,
    selectedDate: String
) {


    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Column {
            Text(
                text = "Product",
                style = CustomTypography.h2.copy(Color.Black)
            )
        }

        Column {
            Text(
                text = "Sales Quantity",
                style = CustomTypography.h2.copy(Color.Black)
            )
        }

        Column {
            Text(
                text = "Price",
                style = CustomTypography.h2.copy(Color.Black)
            )
        }
    }

    Divider(thickness = 2.dp)

    Spacer(modifier = Modifier.height(15.dp))


    val filteredList = filteredSales.filter { sale ->

        (selectedProduct.isEmpty() || sale.productName == selectedProduct) &&
                (selectedDate.isEmpty() || sale.saleDate == selectedDate)
    }

    if (filteredList.isEmpty()) {
        Text("No sales found")
    } else {
        LazyColumn(
            modifier = Modifier.fillMaxSize()
        ) {
            items(filteredList) { sale ->

                ProductTable(sale)

                Divider()

                Spacer(modifier = Modifier.height(15.dp))
            }
        }
    }
}

@Composable
fun ProductTable(product: ProductSales) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Column(Modifier.weight(1f)) {
            Text(
                text = product.productName,
                style = CustomTypography.subtitle1.copy(color = LightColors.onSecondary)
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = product.CustomerName,
                style = CustomTypography.subtitle1.copy(color = LightColors.onSecondary)
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = product.saleDate,
                style = CustomTypography.subtitle2.copy(color = LightColors.onSecondary)
            )
        }

        Column(
            modifier = Modifier
                .align(Alignment.CenterVertically)
                .weight(1f)
        ) {
            Text(
                text = " ${product.productSalesQunatity} Units",
                style = CustomTypography.subtitle1.copy(color = LightColors.onSecondary)
            )
        }

        Column(
            modifier = Modifier
                .align(Alignment.CenterVertically)
                .weight(0.3f)
        ) {
            Text(
                text = " ${product.amountProduct}",
                style = CustomTypography.subtitle1.copy(color = LightColors.onSecondary)
            )
        }
    }

}

