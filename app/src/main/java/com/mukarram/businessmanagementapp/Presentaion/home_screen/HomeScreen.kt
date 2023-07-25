package com.mukarram.businessmanagementapp.Presentaion.home_screen

import CustomTypography
import LightColors
import android.util.Log
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.mukarram.businessmanagementapp.CustomAppWidgets.CustomGridItems
import com.mukarram.businessmanagementapp.NavigationClasses.Screen
import com.mukarram.businessmanagementapp.R
import kotlin.math.absoluteValue

import androidx.compose.runtime.collectAsState




val gridItemToScreenMap = mapOf(
    "Add Product" to Screen.AddProduct,
    "Product Stock" to Screen.AllProducts,
    "Product Sales" to Screen.ProductSales,
    "View Bills" to Screen.ViewBills
)

@Composable
fun HomeScreen(
    navController: NavHostController,
    viewModel: HomeViewModel = hiltViewModel(),
) {



    // Use LaunchedEffect to trigger the refresh
    LaunchedEffect(viewModel.refreshTrigger) {
        viewModel.getBills()
    }


    // Observe the profit value using collectAsState
    val profit: Double? by viewModel.profitState.collectAsState()
    val loss: Double? by viewModel.lossState.collectAsState()




    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(color = LightColors.background)
    )

    {
        Column {

            //Home App Bar
            HomeAppBar()

            //profit Loss Box
            Box(

                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .offset(y = -50.0.dp)


            ) {
                ProfitLossBox(profit,loss)
            }


            //product section
            Box(
                modifier = Modifier
                    .padding(start = 15.dp)
                    .fillMaxWidth()

            )
            {
                Text(
                    text = "Products:",
                    style = CustomTypography.h1.copy(color = LightColors.primary)
                )
            }

            Spacer(modifier = Modifier.height(20.dp))


            //product items grid
            Box(
                modifier = Modifier
                    .padding(horizontal = 5.dp)
                    .fillMaxSize()
            )
            {
                Column {

                    ProductGridSection(navController)


                }
            }


        }

        //bottom navigation bar
        val navItems = listOf(
            NavItem("Home", R.drawable.ic_baseline_home_24, "home"),
            NavItem("Settings", R.drawable.ic_baseline_settings_24, "settings")
        )
        var currentRoute by remember { mutableStateOf(navItems.first().route) }
        BottomNavigationBar(
            navItems = navItems,
            modifier = Modifier
                .align(Alignment.BottomCenter),
            initialRoute = currentRoute,
            onTabSelected = { route ->
                currentRoute = route
            }

        )


    }


}

@Composable
fun ProductGridSection(navController: NavHostController) {
    val gridItems = listOf(
        CustomGridItems("Add Product", R.drawable.ic_baseline_add_circle_outline_24),
        CustomGridItems("Product Stock", R.drawable.ic_baseline_view_list_24),
        CustomGridItems("Product Sales", R.drawable.ic_baseline_trending_up_24),
        CustomGridItems("View Bills", R.drawable.ic_baseline_receipt_24),
    )

    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        modifier = Modifier.padding(5.dp)
    ) {
        items(gridItems.size) { index ->
            CardItem(gridItems[index], navController)
        }
    }


}

@Composable
fun CardItem(customGridItems: CustomGridItems, navController: NavHostController) {
    val destination = gridItemToScreenMap[customGridItems.titleItem]
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(10.dp)
            .clip(RoundedCornerShape(20.dp))
            .aspectRatio(1f)
            .clickable { destination?.let { navController.navigate(it.route) } }
            .background(color = LightColors.primary.copy(alpha = 0.9f))

    ) {

        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,

            modifier = Modifier.fillMaxSize()
        ) {


            Icon(
                painter = painterResource(id = customGridItems.icon),
                contentDescription = "iconAdd",
                tint = LightColors.onBackground,
                modifier = Modifier.size(48.dp)
            )

            Spacer(modifier = Modifier.height(10.dp))


            Text(
                text = customGridItems.titleItem,
                style = CustomTypography.h2.copy(color = LightColors.onPrimary)
            )


        }


    }


}


@Composable
fun ProfitLossBox(

    profit: Double?,
    loss: Double?,


    ) {







    Surface(

        color = Color.Transparent,
        elevation = 0.5.dp,
        modifier = Modifier
            .padding(15.dp)
            .clip(RoundedCornerShape(40.dp))
            .border(
                border = BorderStroke(width = 1.dp, color = Color.LightGray),
                shape = RoundedCornerShape(40.dp)
            )
            .fillMaxWidth()
            .height(90.dp)
            .background(color = Color.White)


    ) {

        Row(
            horizontalArrangement = Arrangement.SpaceAround,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxSize()
        ) {

            Column {
                Text(
                    text = "Rs.${profit?.absoluteValue?:0.0}",
                    style = CustomTypography.h2.copy(color = Color.Green)
                )
                Spacer(modifier = Modifier.height(10.dp))
                Text(
                    text = "Your Profit",
                    style = CustomTypography.h2.copy(color = LightColors.onSurface)
                )
            }

            Box(
                modifier = Modifier
                    .fillMaxHeight()
                    .width(1.dp)
                    .background(color = Color.LightGray)
            )


            Column {
                Text(
                    text = "Rs.${loss?.absoluteValue?:0.0}",
                    style = CustomTypography.h2.copy(color = Color.Red)
                )
                Spacer(modifier = Modifier.height(10.dp))
                Text(
                    text = "Your Loss",
                    style = CustomTypography.h2.copy(color = LightColors.onSurface)
                )
            }

        }
    }
}


@Composable
fun HomeAppBar() {

    Box(

        modifier = Modifier
            .fillMaxWidth()
            .height(140.dp)
            .background(color = LightColors.primary.copy(alpha = 0.9f))


    ) {

        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.Top,
            modifier = Modifier
                .padding(start = 15.dp, end = 15.dp, top = 15.dp)
                .fillMaxSize()
        ) {


            Box() {
                Column(

                ) {

                    Text(
                        text = "Business 1",
                        style = CustomTypography.h1
                    )

                    Spacer(modifier = Modifier.height(5.dp))

                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier
                            .padding(start = 1.dp)
                    ) {
                        Text(
                            text = "Business 1",
                            style = CustomTypography.subtitle2
                        )

                        Spacer(modifier = Modifier.width(10.dp))
                        Icon(
                            imageVector = Icons.Default.ArrowDropDown,
                            contentDescription = "DopDownIcon",
                            tint = LightColors.onPrimary,

                            )
                    }

                }


            }
            Icon(
                imageVector = Icons.Default.Notifications,
                contentDescription = "DopDownIcon",
                tint = LightColors.onPrimary,

                )


        }


    }

}


@Composable
fun BottomNavigationBar(
    navItems: List<NavItem>,
    initialRoute: String,
    onTabSelected: (String) -> Unit,
    modifier: Modifier = Modifier,

    ) {
    val selectedTab = remember { mutableStateOf(initialRoute) }

    BottomNavigation(
        modifier = modifier.fillMaxWidth(),
        backgroundColor = Color.White,
        elevation = 8.dp,

        ) {
        navItems.forEach { navItem ->
            BottomNavigationItem(
                icon = {
                    Icon(
                        painter = painterResource(id = navItem.icon),
                        contentDescription = navItem.title
                    )
                },
                label = {
                    Text(
                        text = navItem.title,
                        style = TextStyle(
                            color = if (selectedTab.value == navItem.route) LightColors.primary else Color.LightGray
                        )

                    )
                },
                selected = selectedTab.value == navItem.route,
                onClick = { onTabSelected(navItem.route) },
                selectedContentColor = LightColors.primary,


                unselectedContentColor = Color.LightGray
            )
        }
    }
}


data class NavItem(val title: String, val icon: Int, val route: String)


