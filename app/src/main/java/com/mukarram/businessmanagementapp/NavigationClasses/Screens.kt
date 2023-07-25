package com.mukarram.businessmanagementapp.NavigationClasses

sealed class Screen(val route: String) {
    object AddProduct : Screen("addProduct")
    object AllProducts : Screen("allProducts")
    object ProductSales : Screen("productSales")
    object ViewBills : Screen("viewBills")
    object Home : Screen("home")
    object CreateBill : Screen("create_bill")
    object BillDetail : Screen("bill_detail")
    object SelectionScreen : Screen("selection_screen")
}
