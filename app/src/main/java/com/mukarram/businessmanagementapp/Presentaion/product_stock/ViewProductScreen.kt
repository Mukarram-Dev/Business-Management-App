package com.mukarram.businessmanagementapp.Presentaion.product_stock

import CustomTypography
import LightColors
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.mukarram.businessmanagementapp.CustomAppWidgets.CustomAppBar
import com.mukarram.businessmanagementapp.DatabaseApp.DataClasses.Product
import com.mukarram.businessmanagementapp.R


@Composable
fun ProductListScreen(
    navController: NavHostController,
    viewModel: StockViewModel= hiltViewModel()

    ) {

    val state = viewModel.state.value
    val scaffoldState = rememberScaffoldState()
    val scope = rememberCoroutineScope()

    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        CustomAppBar(title = "All Products",navController)
        ProductListContent(state)
    }
}

@Composable
fun ProductListContent(state: StockState) {
    val searchProduct = remember { mutableStateOf("") }
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

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 10.dp)
    ) {



        Spacer(modifier = Modifier.height(20.dp))

        //table code of three column for product display


        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Product",
                style = CustomTypography.subtitle2.copy(color = Color.Black, fontSize = 12.sp),
                modifier = Modifier.weight(1f)
            )

            Text(
                text = "Total Qty",
                style = CustomTypography.subtitle2.copy(color = Color.Black, fontSize = 12.sp),
                modifier = Modifier.weight(1.0f)
            )

            Text(
                text = "Sale",
                style = CustomTypography.subtitle2.copy(color = Color.Black, fontSize = 12.sp),
                modifier = Modifier.weight(0.6f)
            )

            Text(
                text = "Remaining",
                style = CustomTypography.subtitle2.copy(color = Color.Black, fontSize = 12.sp),

            )
        }
        Divider(thickness = 2.dp)
        Spacer(modifier = Modifier.height(10.dp))


        //for displaying data in columns
        LazyColumn(
            modifier = Modifier.fillMaxSize()
        ) {
            items(state.product.size) { product ->

                if(state.product.isEmpty()){
                    Text("no data found/inserted",style=CustomTypography.h2)
                }
                else {
                    ProductItem(product,state.product)
                    Divider()
                    Spacer(modifier = Modifier.height(15.dp))
                }

            }
        }
    }
}


@Composable
fun ProductItem(index: Int, product: List<Product>) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween

    ) {
        Column(
            horizontalAlignment = Alignment.Start,
            modifier = Modifier.width(90.dp)


        ) {
            Text(
                textAlign = TextAlign.Start,
                text = " ${product[index].name}",
                style = CustomTypography.subtitle2.copy(color = LightColors.onSecondary, fontSize = 12.sp),

            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                textAlign = TextAlign.Start,
                text = " Rs ${product[index].price}",
                style = CustomTypography.subtitle2.copy(color = LightColors.onSecondary, fontSize = 12.sp),

            )
        }

        Column(

        ) {
            Text(
                text = " ${product[index].quantity} ${product[index].product_type}",
                style = CustomTypography.subtitle2.copy(color = LightColors.onSecondary, fontSize = 12.sp),
                modifier = Modifier.width(100.dp)
            )
        }

        Column(

        ) {
            Text(
                text = " ${product[index].saleQty}",
                style = CustomTypography.subtitle2.copy(color = LightColors.onSecondary, fontSize = 12.sp),
                modifier = Modifier.width(75.dp)
            )
        }



        Column(

        ) {
            Text(
                text = " ${product[index].product_remaining}",
                style = CustomTypography.subtitle2.copy(color = LightColors.onSecondary, fontSize = 12.sp),
                modifier = Modifier.width(30.dp)
            )
        }
    }
}
