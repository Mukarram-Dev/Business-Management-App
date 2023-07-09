package com.mukarram.businessmanagementapp.Presentaion

import CustomTypography
import LightColors
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.mukarram.businessmanagementapp.CustomAppWidgets.CustomAppBar


@Composable
fun BillScreen(navController: NavHostController) {
    Scaffold(
        topBar = { CustomAppBar("Create Bill",navController)  },

        )
    {
        it
        BillContent()
    }
}


@Composable
fun BillContent() {
    var currentDate by remember { mutableStateOf("") }
    var customerName by remember { mutableStateOf("") }
    var selectedItem by remember { mutableStateOf("") }
    var phoneNumber by remember { mutableStateOf("") }
    var productQuantity by remember { mutableStateOf("") }
    var customerAddress by remember { mutableStateOf("") }
    var salePerUnitPrice by remember { mutableStateOf("") }
    var billNumber by remember { mutableStateOf("") }
    var totalBill by remember { mutableStateOf("") }

    val context = LocalContext.current
    val focusManager = LocalFocusManager.current
    val currentDateFocusRequest = remember { FocusRequester() }
    val customerNameFocusRequest = remember { FocusRequester() }
    val selectedItemFocusRequest = remember { FocusRequester() }
    val phoneNumberFocusRequest = remember { FocusRequester() }
    val productQuantityFocusRequest = remember { FocusRequester() }
    val customerAddressFocusRequest = remember { FocusRequester() }
    val salePerUnitPriceFocusRequest = remember { FocusRequester() }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp),

    ) {

        Spacer(modifier = Modifier.height(20.dp))
        //customer name field

        OutlinedTextField(
            value = customerName,
            onValueChange = { customerName = it },
            textStyle=CustomTypography.subtitle2
                .copy(color = LightColors.primary.copy(alpha = 0.7f)),
            label = {
                Text(
                    "Customer Name",

                    style = CustomTypography.subtitle2
                        .copy(color = LightColors.primary.copy(alpha = 0.7f))

                )
            },
            singleLine = true,
            modifier = Modifier.fillMaxWidth(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
            keyboardActions = KeyboardActions(
                onNext = { selectedItemFocusRequest.requestFocus() }
            ),
            trailingIcon = {
                if (customerName.isNotEmpty()) {
                    IconButton(onClick = { customerName = "" }) {
                        Icon(Icons.Default.Clear, contentDescription = "Clear")
                    }
                }
            }
        )

        Spacer(modifier = Modifier.height(10.dp))

        OutlinedTextField(
            value = customerAddress,
            onValueChange = { customerAddress = it },
            textStyle=CustomTypography.subtitle2
                .copy(color = LightColors.primary.copy(alpha = 0.7f)),
            label = {
                Text(
                    "Customer Address",
                    style = CustomTypography.subtitle2
                        .copy(color = LightColors.primary.copy(alpha = 0.7f))


                )
            },
            singleLine = true,
            modifier = Modifier.fillMaxWidth(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
            keyboardActions = KeyboardActions(
                onNext = { salePerUnitPriceFocusRequest.requestFocus() }
            ),
            trailingIcon = {
                if (customerAddress.isNotEmpty()) {
                    IconButton(onClick = { customerAddress = "" }) {
                        Icon(Icons.Default.Clear, contentDescription = "Clear")
                    }
                }
            }
        )


        Spacer(modifier = Modifier.height(10.dp))

        OutlinedTextField(
            value = phoneNumber,
            onValueChange = { phoneNumber = it },
            textStyle=CustomTypography.subtitle2
                .copy(color = LightColors.primary.copy(alpha = 0.7f)),
            label = {
                Text(
                    "Phone Number",

                    style = CustomTypography.subtitle2
                        .copy(color = LightColors.primary.copy(alpha = 0.7f))
                )
            },
            singleLine = true,
            modifier = Modifier.fillMaxWidth(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone),
            keyboardActions = KeyboardActions(
                onNext = { productQuantityFocusRequest.requestFocus() }
            ),
            trailingIcon = {
                if (phoneNumber.isNotEmpty()) {
                    IconButton(onClick = { phoneNumber = "" }) {
                        Icon(Icons.Default.Clear, contentDescription = "Clear")
                    }
                }
            }
        )

        Spacer(modifier = Modifier.height(10.dp))

        OutlinedTextField(
            value = selectedItem,
            onValueChange = { selectedItem = it },
            textStyle=CustomTypography.subtitle2
                .copy(color = LightColors.primary.copy(alpha = 0.7f)),
            label = {
                Text(
                    "Select Item",

                    style = CustomTypography.subtitle2
                        .copy(color = LightColors.primary.copy(alpha = 0.7f))
                )
            },
            singleLine = true,
            modifier = Modifier.fillMaxWidth(1f),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
            keyboardActions = KeyboardActions(
                onNext = { phoneNumberFocusRequest.requestFocus() }
            ),
            trailingIcon = {
                if (selectedItem.isNotEmpty()) {
                    IconButton(onClick = { selectedItem = "" }) {
                        Icon(Icons.Default.Clear, contentDescription = "Clear")
                    }
                }
            }
        )


        Spacer(modifier = Modifier.height(10.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            OutlinedTextField(
                value = currentDate,
                onValueChange = { currentDate = it },
                label = {
                    Text(
                        "Select Date",
                        style = CustomTypography.subtitle2
                            .copy(color = LightColors.primary.copy(alpha = 0.7f))
                    )
                },


                singleLine = true,
                modifier = Modifier.weight(0.5f),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
                keyboardActions = KeyboardActions(
                    onNext = { customerNameFocusRequest.requestFocus() }
                ),
                leadingIcon = {
                    if (currentDate.isNotEmpty()) {
                        IconButton(onClick = { currentDate = "" }) {
                            Icon(Icons.Default.DateRange, contentDescription = "dropdown")
                        }
                    }
                }
            )

            Spacer(modifier = Modifier.width(5.dp))


            OutlinedTextField(
                value = productQuantity,
                onValueChange = { productQuantity = it },
                textStyle=CustomTypography.subtitle2
                    .copy(color = LightColors.primary.copy(alpha = 0.7f)),
                label = {
                    Text(
                        "Product Quantity",
                        style = CustomTypography.subtitle2
                            .copy(color = LightColors.primary.copy(alpha = 0.7f))


                    )
                },
                singleLine = true,
                modifier = Modifier.fillMaxWidth(0.5f),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                keyboardActions = KeyboardActions(
                    onNext = { customerAddressFocusRequest.requestFocus() }
                ),
                trailingIcon = {
                    if (productQuantity.isNotEmpty()) {
                        IconButton(onClick = { productQuantity = "" }) {
                            Icon(Icons.Default.Clear, contentDescription = "Clear")
                        }
                    }
                }
            )

            // Date Select Option
            // Implement your date selection logic here

        }


       Spacer(modifier = Modifier.height(10.dp))


        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {

            OutlinedTextField(
                value = salePerUnitPrice,
                onValueChange = { salePerUnitPrice = it },
                textStyle=CustomTypography.subtitle2
                    .copy(color = LightColors.primary.copy(alpha = 0.7f)),
                label = {
                    Text(
                        "Sale Per Unit Price",
                        style = CustomTypography.subtitle2
                            .copy(color = LightColors.primary.copy(alpha = 0.7f))

                    )
                },
                singleLine = true,
                modifier = Modifier.fillMaxWidth(0.5f),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                keyboardActions = KeyboardActions(
                    onNext = { focusManager.clearFocus() }
                ),
                trailingIcon = {
                    if (salePerUnitPrice.isNotEmpty()) {
                        IconButton(onClick = { salePerUnitPrice = "" }) {
                            Icon(Icons.Default.Clear, contentDescription = "Clear")
                        }
                    }
                }
            )

            Spacer(modifier = Modifier.width(5.dp))

            OutlinedTextField(
                value = billNumber,
                onValueChange = { billNumber = it },
                textStyle=CustomTypography.subtitle2
                    .copy(color = LightColors.primary.copy(alpha = 0.7f)),
                label = {
                    Text(
                        "Bill Number",
                        style = CustomTypography.subtitle2
                            .copy(color = LightColors.primary.copy(alpha = 0.7f))
                    )
                },
                singleLine = true,
                modifier = Modifier.fillMaxWidth(1.0f),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                keyboardActions = KeyboardActions(
                    onNext = { focusManager.clearFocus() }
                ),
                trailingIcon = {
                    if (billNumber.isNotEmpty()) {
                        IconButton(onClick = { billNumber = "" }) {
                            Icon(Icons.Default.Clear, contentDescription = "Clear")
                        }
                    }
                }
            )



        }



        Spacer(modifier = Modifier.height(16.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Button(
                onClick = {
                    // Calculate total bill
                    totalBill = calculateTotalBill()
                }
            ) {
                Text(text = "Calculate Bill")
            }

            Button(
                onClick = {
                    // Save and print bill
                    if (validateForm()) {
                        saveAndPrintBill()
                    }
                }
            ) {
                Text(text = "Save and Print Bill")
            }
        }

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = "Total Bill: $totalBill",
            modifier = Modifier.align(Alignment.End)
        )
    }
}

// Helper function to calculate the total bill
fun calculateTotalBill(): String {
    // Implement your logic to calculate the total bill
    return "100" // Dummy value
}

// Helper function to validate the form
fun validateForm(): Boolean {
    // Implement your form validation logic
    return true // Dummy value
}

// Helper function to save and print the bill
fun saveAndPrintBill() {
    // Implement your logic to save and print the bill
}
