package com.mukarram.businessmanagementapp.CustomAppWidgets

import CustomTypography
import LightColors
import androidx.compose.compiler.plugins.kotlin.ComposeFqNames.remember

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp


@Composable
fun CustomTextField(
    label: String,
    hint: String,
    modifier: Modifier,
    keyboardOptions: KeyboardOptions,
    imageVector: ImageVector?,
    onValueChange: (String) -> Unit,
    contentDescription: String,


    ) {


    TextField(
        colors = TextFieldDefaults.textFieldColors(backgroundColor = Color.White),
        value = label,
        shape = RoundedCornerShape(20.dp),
        singleLine = true,
        onValueChange = onValueChange,


        label = {
            Text(text = hint, style = CustomTypography.subtitle2
                    .copy(color = LightColors.primary.copy(alpha = 0.7f))
            )


        },
        textStyle = CustomTypography.subtitle2
            .copy(color = LightColors.primary.copy(alpha = 0.7f)),
        keyboardOptions = keyboardOptions,
        leadingIcon = {
            if (imageVector != null) {
                Icon(
                    imageVector = imageVector,
                    contentDescription = contentDescription,
                    tint = LightColors.primary
                )
            }
        },
        modifier = modifier
            .padding(10.dp)
            .height(60.dp)
            .clip(RoundedCornerShape(20.dp))
            .border(
                width = 1.dp,
                color = LightColors.primary.copy(alpha = 0.7f),
                shape = RoundedCornerShape(20.dp)
            )
    )

}
