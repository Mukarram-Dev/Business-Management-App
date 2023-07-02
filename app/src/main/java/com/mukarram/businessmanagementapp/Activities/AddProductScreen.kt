package com.mukarram.businessmanagementapp.Activities

import CustomTypography
import LightColors
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.mukarram.businessmanagementapp.CustomAppWidgets.CustomAppBar


@Composable
fun AddProductScreen(navController: NavHostController) {
    Scaffold(
        topBar = { CustomAppBar("Add Product",navController)  },

        )
    {
        it
        FormContent()
    }
}


@Composable
fun FormContent() {
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

        DropdownMenu(
            expanded = selectedUnit.isNotEmpty(),
            onDismissRequest = { selectedUnit = "" },
            modifier = Modifier.fillMaxWidth()
        ) {
            unitList.forEach { unit ->
                DropdownMenuItem(
                    onClick = {
                        selectedUnit = unit
                        focusManager.clearFocus()
                    }
                ) {
                    Text(text = unit,style=CustomTypography.subtitle2
                        .copy(color = LightColors.primary.copy(alpha = 0.7f)),)
                }
            }
        }

        OutlinedTextField(
            value = selectedUnit,
            onValueChange = {selectedUnit},
            textStyle=CustomTypography.subtitle2
                .copy(color = LightColors.primary.copy(alpha = 0.7f)),
            label = {
                Text(
                    "Select Product Unit",
                    style = CustomTypography.subtitle2
                        .copy(color = LightColors.primary.copy(alpha = 0.7f))
                )
            },
            modifier = Modifier
                .fillMaxWidth()
                .clickable { focusManager.clearFocus(); selectedUnit = unitList.first() }
        )

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
                    Toast.makeText(context, "Product added", Toast.LENGTH_SHORT).show()
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




