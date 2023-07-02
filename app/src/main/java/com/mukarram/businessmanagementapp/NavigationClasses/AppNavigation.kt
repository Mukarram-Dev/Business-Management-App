package com.mukarram.businessmanagementapp.NavigationClasses



import ProductListScreen
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.mukarram.businessmanagementapp.Activities.*

@Composable
fun AppNavGraph() {
    val navController = rememberNavController()


    NavHost(navController = navController, startDestination = Screen.Home.route) {
        composable(Screen.AddProduct.route) {
            AddProductScreen(navController)
        }
        composable(Screen.AllProducts.route) {
            ProductListScreen(navController)
        }
        composable(Screen.ProductSales.route) {
            ProductSalesScreen(navController)
        }
        composable(Screen.ViewBills.route) {
            BillReportScreen(navController)
        }
        composable(Screen.Home.route) {
            HomeScreen(navController)
        }
        composable(Screen.CreateBill.route) {
            BillScreen(navController)
        }
        composable(Screen.BillDetail.route) {
            BillDetailScreen(navController)
        }
    }
}
