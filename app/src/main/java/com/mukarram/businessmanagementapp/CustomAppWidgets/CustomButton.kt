package com.mukarram.businessmanagementapp.CustomAppWidgets

import LightColors
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun AppCustomButton(
    btnText: String,
    modifier: Modifier,
    onClick: () -> Unit,

    ) {
    OutlinedButton(
        onClick = onClick,
        shape=RoundedCornerShape(20.dp),
        colors=ButtonDefaults.outlinedButtonColors(backgroundColor = LightColors.primary),
        modifier = modifier
            .padding(15.dp)
            .height(40.dp)


            .clip(RoundedCornerShape(20.dp))
    ) {
        Text(text = btnText)

    }

}