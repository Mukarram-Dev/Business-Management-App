package com.mukarram.businessmanagementapp.NavigationClasses




import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.mukarram.businessmanagementapp.Presentaion.*
import com.mukarram.businessmanagementapp.Presentaion.product_sales.ProductSalesScreen
import com.mukarram.businessmanagementapp.Presentaion.product_stock.ProductListScreen
import com.mukarram.businessmanagementapp.Presentaion.view_billScreen.BillReportScreen

@Composable
fun AppNavGraph() {
     var navController = rememberNavController()


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
        composable(Screen.BillDetail.route + "/{billId}", // Use curly braces to denote the argument
            arguments = listOf(
                navArgument("billId") {
                    type = NavType.LongType // Specify the argument type as Long
                    defaultValue = -1 // Provide a default value if needed
                }
            )
        ) { backStackEntry ->
            val arguments = requireNotNull(backStackEntry.arguments)
            val billId = arguments.getLong("billId")
            // Call the composable function for the BillDetailScreen and pass the billId argument
            BillDetailScreen(navController, billId)
        }
    }
}
