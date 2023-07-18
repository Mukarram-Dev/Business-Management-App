package com.mukarram.businessmanagementapp.Presentaion.bill_detail.utils

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.pdf.PdfDocument
import android.net.Uri
import android.os.Environment
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext

import androidx.compose.ui.unit.sp
import androidx.core.content.FileProvider
import com.mukarram.businessmanagementapp.BuildConfig
import com.mukarram.businessmanagementapp.CustomAppWidgets.AppCustomButton
import com.mukarram.businessmanagementapp.Presentaion.bill_detail.BillDetailViewModel
import java.io.File





@Composable
fun SharePdfFile(generatedPdfFile: File?) {


    if (generatedPdfFile != null) {
        val context = LocalContext.current
        val pdfFile = generatedPdfFile

        AppCustomButton(btnText = "Share", modifier = Modifier.fillMaxWidth(1f)) {
            pdfFile.let {
                val intent = Intent(Intent.ACTION_SEND)
                intent.type = "application/pdf"
                val uri = FileProvider.getUriForFile(
                    context,
                    "${BuildConfig.APPLICATION_ID}.provider",
                    it
                )
                intent.putExtra(Intent.EXTRA_STREAM, uri)
                intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
                context.startActivity(Intent.createChooser(intent, "Share via"))
            }
        }
    }
}