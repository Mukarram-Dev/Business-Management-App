package com.mukarram.businessmanagementapp.Presentaion.product_sales

import CustomTypography
import LightColors
import android.app.DatePickerDialog

import android.widget.DatePicker
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.PopupProperties
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.mukarram.businessmanagementapp.CustomAppWidgets.CustomAppBar
import com.mukarram.businessmanagementapp.Presentaion.getCurrentDate
import com.mukarram.businessmanagementapp.Presentaion.product_stock.StockState
import com.mukarram.businessmanagementapp.Presentaion.product_stock.StockViewModel
import com.mukarram.businessmanagementapp.R
import java.util.*

data class ProductSales(
    val productName: String,
    val CustomerName: String,
    val productSalesQunatity: Int,
    val saleDate: String,
    val amountProduct: Int,
)


@Composable

fun ProductSalesScreen(
    navController: NavHostController,
    viewModel: ProductSaleViewModel= hiltViewModel(),
    stockViewModel: StockViewModel = hiltViewModel(),
) {
    val productState = stockViewModel.state.value
    LaunchedEffect(Unit) {
        viewModel.fetchSaleDetails()
    }

    // Collect the StateFlow using collectAsState
    val saleDetailState by viewModel.saleDetailState.collectAsState()

    Scaffold(
        topBar = { CustomAppBar(title = "Product Sales", navController) },

        ) {
        it
        ProductSalesContent(saleDetailState,productState)

    }
}


@Composable
fun ProductSalesContent(saleDetailState: List<SaleDetailsModel>, productState: StockState) {
    val productDetailList = remember { mutableStateListOf<ProductSales>() }


    val selectedProduct = remember { mutableStateOf("") }
    val selectedDate = remember { mutableStateOf("") }
    val searchProduct = remember { mutableStateOf("") }


    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 8.dp)
    ) {


        Spacer(modifier = Modifier.height(10.dp))

        //search text field
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

        Spacer(modifier = Modifier.height(15.dp))


        // Product selection dropdown and date picker
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceAround
        ) {
            ProductSelection(productState, selectedProduct)

            // Date picker

            DatePickers(selectedDate)


        }


        Spacer(modifier = Modifier.height(16.dp))

        // Filtered sales list
        SalesList(saleDetailState, selectedProduct.value, selectedDate.value)
    }
}

@Composable
fun ProductSelection(productList: StockState, selectedProduct: MutableState<String>) {
    var expanded by remember { mutableStateOf(false) }
    var selectedIndex by remember { mutableStateOf(0) }





    Box(
        modifier = Modifier
            .fillMaxWidth(0.4f)
            .height(50.dp)

            .background(color = Color.White, shape = RoundedCornerShape(10.dp))
            .clickable { expanded = true }
            .border(
                border = BorderStroke(width = 1.dp, color = Color.Gray),
                shape = RoundedCornerShape(10.dp)
            )

    ) {
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier
                .fillMaxWidth()
                .padding(15.dp)
        ) {


            Spacer(modifier = Modifier.width(5.dp))
            Text(
                if (selectedProduct.value.isNotEmpty()) selectedProduct.value else "Select Product",
                style = CustomTypography.subtitle2
                    .copy(color = LightColors.primary.copy(alpha = 0.7f))
            )
            Icon(
                imageVector = Icons.Default.ArrowDropDown, contentDescription = "dropDownButton",
                tint = LightColors.primary
            )

            DropdownMenu(
                expanded = expanded,
                offset = DpOffset(0.dp, 0.dp),
                properties = PopupProperties(focusable = true),
                onDismissRequest = { expanded = false },
                modifier = Modifier.fillMaxWidth()
            ) {
                productList.product.forEachIndexed { index, product ->
                    DropdownMenuItem(

                        onClick = {
                            selectedProduct.value = product.name
                            selectedIndex = index
                            expanded = false
                        }
                    ) {
                        Text(text = product.name, style = CustomTypography.h2)
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

    val todayDate = getCurrentDate()

    var currentDate by remember { mutableStateOf(todayDate) }






    Box(
        modifier = Modifier

            .fillMaxWidth(0.6f)
            .height(50.dp)
            .background(color = Color.White, shape = RoundedCornerShape(10.dp))
            .border(
                border = BorderStroke(
                    width = 1.dp,
                    color = LightColors.primary.copy(alpha = 0.7f),
                ),
                shape = RoundedCornerShape(10.dp)
            )

            .clickable(onClick = {
                mDatePickerDialog.show()
                currentDate = mDate.value
            })
    )
    {
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier
                .fillMaxWidth()
                .padding(15.dp)

        ) {


            Text(
                currentDate,
                style = CustomTypography.subtitle2
                    .copy(color = LightColors.primary)
            )

            Icon(
                Icons.Default.DateRange,
                contentDescription = "iconDate",
                tint = LightColors.primary
            )
        }
    }


}

@Composable
fun SalesList(
    filteredSales: List<SaleDetailsModel>,
    selectedProduct: String,
    selectedDate: String
) {
    Spacer(modifier = Modifier.height(20.dp))


    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(5.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = "Product",
            style = CustomTypography.subtitle2.copy(color = Color.Black),
            modifier = Modifier.weight(1f)
        )

        Text(
            text = "Sale Qty",
            style = CustomTypography.subtitle2.copy(color = Color.Black),
            modifier = Modifier.weight(0.9f)
        )

        Text(
            text = "Price",
            style = CustomTypography.subtitle2.copy(color = Color.Black),
            modifier = Modifier.weight(0.7f)
        )

        Text(
            text = "Customer",
            style = CustomTypography.subtitle2.copy(color = Color.Black),

            )
    }
    Divider(thickness = 2.dp)

    Spacer(modifier = Modifier.height(15.dp))


    val filteredList = filteredSales.filter { sale ->

        (selectedProduct.isEmpty() || sale.productName == selectedProduct) &&
                (selectedDate.isEmpty() || sale.purchaseDate == selectedDate)
    }

    if (filteredList.isEmpty()) {
        Text("No sales found")
    } else {
        LazyColumn(
            modifier = Modifier.fillMaxSize()
        ) {
            items(filteredList) { sale ->
                if(filteredList.isEmpty()){
                    Text(text = "No data found / Insert",
                        style = CustomTypography.h2
                    )
                }else {
                    ProductTable(sale)

                    Divider()

                    Spacer(modifier = Modifier.height(15.dp))

                }


            }
        }
    }
}

@Composable
fun ProductTable(sale: SaleDetailsModel) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(5.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween

    ) {
        Column(horizontalAlignment = Alignment.Start,modifier = Modifier.width(100.dp)) {
            Text(
                text = sale.productName,
                style = CustomTypography.subtitle2.copy(color = LightColors.onSecondary),
                textAlign = TextAlign.Start,

            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = sale.purchaseDate,
                style = CustomTypography.subtitle2.copy(color = LightColors.onSecondary),
                textAlign = TextAlign.Start,
            )
        }

        Column {
            Text(
                text = " ${sale.saleQty} ${sale.productType}",
                style = CustomTypography.subtitle2.copy(color = LightColors.onSecondary),
                modifier = Modifier.width(50.dp)
            )
        }

        Column {
            Text(
                text = " ${sale.totalBill}",
                style = CustomTypography.subtitle2.copy(color = LightColors.onSecondary),
                modifier = Modifier.width(60.dp)
            )
        }
        Column {
            Text(
                text = sale.customerName,
                style = CustomTypography.subtitle2.copy(color = LightColors.onSecondary),
                modifier = Modifier.width(80.dp)
            )
        }
    }

}

