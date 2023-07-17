package com.mukarram.businessmanagementapp.Presentaion



import BizSolutionsTheme
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import com.mukarram.businessmanagementapp.NavigationClasses.AppNavGraph
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
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

