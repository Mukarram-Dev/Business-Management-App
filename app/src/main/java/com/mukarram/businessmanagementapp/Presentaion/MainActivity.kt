package com.mukarram.businessmanagementapp.Activities



import BizSolutionsTheme
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import com.mukarram.businessmanagementapp.DatabaseApp.ViewModelClasses.ProductViewModel
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

