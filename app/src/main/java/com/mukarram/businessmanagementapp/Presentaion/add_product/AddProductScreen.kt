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
import com.mukarram.businessmanagementapp.DatabaseApp.ViewModelClasses.ProductViewModel
import androidx.compose.runtime.Composable




@Composable
fun AddProductScreen(navController: NavHostController) {
    val productViewModel: ProductViewModel= viewModel()

    Scaffold(
        topBar = { CustomAppBar("Add Product",navController)  },

        )
    {
        innerPadding->
        FormContent(productViewModel,modifier=Modifier.padding(innerPadding))
    }
}


@Composable
fun FormContent(viewModel: ProductViewModel, modifier: Modifier) {
    var productName by remember { mutableStateOf("") }
    var productQuantity by remember { mutableStateOf("") }
    var purchasePrice by remember { mutableStateOf("") }
    var selectedUnit by remember { mutableStateOf("") }
    val unitList = listOf("Unit", "Gram", "Piece", "Litre")

    val context = LocalContext.current
    val focusManager = LocalFocusManager.current
    val productNameFocusRequest = remember { FocusRequester() }
    val productQuantityFocusRequest = remember { FocusRequester() }
    val purchasePriceFocusRequest = remember { FocusRequester() }


    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp),

        ) {
        Spacer(modifier = Modifier.height(15.dp))
        OutlinedTextField(
            value = productName,
            onValueChange = { productName = it },

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
                if (productName.isNotEmpty()) {
                    IconButton(onClick = { productName = "" }) {
                        Icon(Icons.Default.Clear, contentDescription = "Clear")
                    }
                }
            }
        )

        Spacer(modifier = Modifier.height(15.dp))

        OutlinedTextField(
            value = productQuantity,
            onValueChange = { productQuantity = it },
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
                if (productQuantity.isNotEmpty()) {
                    IconButton(onClick = { productQuantity = "" }) {
                        Icon(Icons.Default.Clear, contentDescription = "Clear")
                    }
                }
            }
        )

        Spacer(modifier = Modifier.height(15.dp))

        OutlinedTextField(
            value = purchasePrice,
            onValueChange = { purchasePrice = it },
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
                if (purchasePrice.isNotEmpty()) {
                    IconButton(onClick = { purchasePrice = "" }) {
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
                    text = if (selectedUnit.isNotEmpty()) selectedUnit else "Select Unit",
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
                            selectedUnit = unit
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

        Button(
            onClick = {
                // Perform form validation
                if (productName.isEmpty() || productQuantity.isEmpty() ||
                    purchasePrice.isEmpty() || selectedUnit.isEmpty()
                ) {
                    // Show error message or perform appropriate action
                    Toast.makeText(context, "Please fill in all fields", Toast.LENGTH_SHORT).show()
                } else {
                    // Form is valid, perform action

                    AddProductToDatabase(productName,productQuantity,purchasePrice,selectedUnit,viewModel)

                    Toast.makeText(context, "Product added", Toast.LENGTH_SHORT).show()

                    productName=""
                    productQuantity=""
                    purchasePrice=""
                    selectedUnit=""


                }
            },
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .background(color = LightColors.background)
                .clip(RoundedCornerShape(10.dp))
                .fillMaxWidth()
        ) {
            Text(text = "Add Product")
        }
    }
}


fun AddProductToDatabase(
    productName: String,
    productQuantity: String,
    purchasePrice: String,
    selectedUnit: String,
    viewModel: ProductViewModel
) {


    val pName:String=productName
    val pQty : Int=productQuantity.toInt()
    val prchasePrice:Double =purchasePrice.toDouble()
    val productUnit: String =selectedUnit


    val product=Product(0,pName,prchasePrice,pQty,0,productUnit)

    viewModel.insertProduct(product)



}




