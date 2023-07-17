package com.mukarram.businessmanagementapp.Presentaion

import CustomTypography
import LightColors
import android.widget.Toast
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.Clear
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.mukarram.businessmanagementapp.CustomAppWidgets.CustomAppBar
import com.mukarram.businessmanagementapp.DatabaseApp.DataClasses.Product
import com.mukarram.businessmanagementapp.Presentaion.add_product.AddProductViewModel
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import com.mukarram.businessmanagementapp.CustomAppWidgets.AppCustomButton
import com.mukarram.businessmanagementapp.Presentaion.add_product.AddProductEvents
import com.mukarram.businessmanagementapp.Presentaion.add_product.UiEvent
import kotlinx.coroutines.flow.collectLatest


@Composable
fun AddProductScreen(
    navController: NavHostController,
    viewModel : AddProductViewModel=hiltViewModel()
) {


    Scaffold(
        topBar = { CustomAppBar("Add Product",navController)  },

        )
    {
        innerPadding->
        FormContent(viewModel,modifier=Modifier.padding(innerPadding))
    }
}


@Composable
fun FormContent(viewModel: AddProductViewModel, modifier: Modifier) {
    val prodNameState=viewModel.productName.value
    val prodQtyState=viewModel.productQty.value
    val prodPriceState=viewModel.productPrice.value
    var productTypeState=viewModel.productType.value

    val scaffoldState = rememberScaffoldState()
    val scope = rememberCoroutineScope()

    LaunchedEffect(key1 = true){
        viewModel.eventFlow.collectLatest { event->
            when(event){
                is UiEvent.ShowSnackbar-> {
                    scaffoldState.snackbarHostState.showSnackbar(
                        message = event.message
                    )
                }
                is UiEvent.SaveProduct-> {

                }
            }
        }
    }


    val unitList = listOf("Unit", "Gram", "Piece", "Litre")

    val context = LocalContext.current
    val focusManager = LocalFocusManager.current






    val productQuantityFocusRequest = remember { FocusRequester() }
    val purchasePriceFocusRequest = remember { FocusRequester() }


    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp),

        ) {
        Spacer(modifier = Modifier.height(15.dp))
        OutlinedTextField(
            value = prodNameState.text,
            onValueChange = {
                viewModel.onEvent(AddProductEvents.EnteredProdName(it))
                            },

            label = {
                Text(
                    "Product Name",
                    style = CustomTypography.subtitle2
                        .copy(color = LightColors.primary.copy(alpha = 0.7f))
                )
            },
            singleLine = true,
            textStyle=CustomTypography.subtitle2
                .copy(color = LightColors.primary.copy(alpha = 0.7f)),

            modifier = Modifier.fillMaxWidth(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
            keyboardActions = KeyboardActions(
                onNext = { productQuantityFocusRequest.requestFocus() }
            ),
            trailingIcon = {
                if (prodNameState.text.isNotEmpty()) {
                    IconButton(onClick = { prodNameState.text = "" }) {
                        Icon(Icons.Default.Clear, contentDescription = "Clear")
                    }
                }
            }
        )

        Spacer(modifier = Modifier.height(15.dp))

        OutlinedTextField(
            value = prodQtyState.text,
            onValueChange = {
                            viewModel.onEvent(AddProductEvents.EnteredProdQty(it))
            },
            label = { Text("Product Quantity",
                style = CustomTypography.subtitle2
                    .copy(color = LightColors.primary.copy(alpha = 0.7f))) },
            singleLine = true,
            textStyle=CustomTypography.subtitle2
                .copy(color = LightColors.primary.copy(alpha = 0.7f)),
            modifier = Modifier.fillMaxWidth(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),

            keyboardActions = KeyboardActions(
                onNext = { purchasePriceFocusRequest.requestFocus() }
            ),
            trailingIcon = {
                if (prodQtyState.text.isNotEmpty()) {
                    IconButton(onClick = { prodQtyState.text = "" }) {
                        Icon(Icons.Default.Clear, contentDescription = "Clear")
                    }
                }
            }
        )

        Spacer(modifier = Modifier.height(15.dp))

        OutlinedTextField(
            value = prodPriceState.text,
            onValueChange = {viewModel.onEvent(AddProductEvents.EnteredProdPrice(it)) },
            label = {
                Text(
                    "Purchase Price per Unit",
                    style = CustomTypography.subtitle2
                        .copy(color = LightColors.primary.copy(alpha = 0.7f))
                )
            },
            singleLine = true,
            modifier = Modifier.fillMaxWidth(),
            textStyle=CustomTypography.subtitle2
                .copy(color = LightColors.primary.copy(alpha = 0.7f)),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            keyboardActions = KeyboardActions(
                onNext = { focusManager.clearFocus() }
            ),
            trailingIcon = {
                if ( prodPriceState.text.isNotEmpty()) {
                    IconButton(onClick = {  prodPriceState.text = "" }) {
                        Icon(Icons.Default.Clear, contentDescription = "Clear")
                    }
                }
            }
        )

        Spacer(modifier = Modifier.height(15.dp))

        var expanded by remember { mutableStateOf(false) }

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp)
                .clip(RoundedCornerShape(10.dp,))
                .border(border = BorderStroke(width = 1.dp, color = Color.Gray))
                .clickable { expanded = true }
        ) {
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(15.dp)
            ) {
                Text(
                    text = if (productTypeState.isNotEmpty()) productTypeState else "Select Unit",
                    style = CustomTypography.subtitle2
                        .copy(color = LightColors.primary.copy(alpha = 0.7f))
                )

                Icon(imageVector = Icons.Default.ArrowDropDown, contentDescription = "dropDown")
            }

            DropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded=false},
            ) {
                unitList.forEach { unit ->
                    DropdownMenuItem(
                        onClick = {
                            productTypeState =
                                viewModel.onEvent(AddProductEvents.SelectProdType(unit)).toString()
                            expanded=false
                        }
                    ) {
                        Text(
                            text = unit,
                            style = CustomTypography.subtitle2
                                .copy(color = LightColors.primary.copy(alpha = 0.7f))
                        )
                    }
                }
            }
        }



        Spacer(modifier = Modifier.height(16.dp))


        AppCustomButton(btnText = "Add Product", modifier = Modifier.fillMaxWidth()) {
            if (prodNameState.text.isEmpty() || prodQtyState.text.isEmpty() ||
                prodPriceState.text.isEmpty() || productTypeState.isEmpty()
            ) {
                // Show error message or perform appropriate action
                Toast.makeText(context, "Please fill in all fields", Toast.LENGTH_SHORT).show()
            } else {
                // Form is valid, perform action

                viewModel.onEvent(AddProductEvents.saveProduct)

                Toast.makeText(context, "Product added", Toast.LENGTH_SHORT).show()

                prodNameState.text=""
                prodQtyState.text=""
                prodPriceState.text=""
                productTypeState=""


            }
        }


    }
}







