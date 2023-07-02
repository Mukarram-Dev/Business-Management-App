package com.mukarram.businessmanagementapp.Activities



import BizSolutionsTheme
import ProductListScreen
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.mukarram.businessmanagementapp.NavigationClasses.AppNavGraph


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)



        setContent {


            BizSolutionsTheme {

                AppNavGraph()
            }


        }
    }






}

