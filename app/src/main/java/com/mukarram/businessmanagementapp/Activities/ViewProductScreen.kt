import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Divider
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.mukarram.businessmanagementapp.CustomAppWidgets.CustomAppBar

data class Product(
    val name: String,
    val totalQuantity: Int,
    val remainingQuantity: Int
)


@Composable
fun ProductListScreen(navController: NavHostController) {
    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        CustomAppBar(title = "All Products",navController)
        ProductListContent()
    }
}

@Composable
fun ProductListContent() {
    val productList = remember { mutableStateListOf<Product>() }
    productList.addAll(
        listOf(
            Product("Product A", 100, 50),
            Product("Product B", 200, 100),
            Product("Product C", 150, 75),
            Product("Product D", 300, 200),
        )
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp)
    ) {
        Spacer(modifier = Modifier.height(15.dp))
        OutlinedTextField(
            value = "",
            onValueChange = {},
            label = {
                Text(
                    "Search Product",
                    style = CustomTypography.subtitle2
                        .copy(color = LightColors.primary.copy(alpha = 0.7f))
                )
            },
            singleLine = true,
            modifier = Modifier
                .fillMaxWidth(5f)
                .clip(RoundedCornerShape(10.dp))
        )

        Spacer(modifier = Modifier.height(20.dp))

        //table code of three column for product display


        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column(

            ) {
                Text(
                    text = "Product",
                    style = CustomTypography.h2.copy(Color.Black)
                )
            }

            Column(

            ) {
                Text(
                    text = "Total Quantity",
                    style = CustomTypography.h2.copy(Color.Black)
                )
            }

            Column(

            ) {
                Text(
                    text = "Remaining",
                    style = CustomTypography.h2.copy(Color.Black)
                )
            }
        }

        Divider()
        Spacer(modifier = Modifier.height(20.dp))


        //for displaying data in columns
        LazyColumn(
            modifier = Modifier.fillMaxSize()
        ) {
            items(productList) { product ->
                ProductItem(product)
                Divider()
                Spacer(modifier = Modifier.height(15.dp))
            }
        }
    }
}


@Composable
fun ProductItem(product: Product) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Column(

        ) {
            Text(
                text = product.name,
                style = CustomTypography.h2.copy(color = LightColors.onSecondary)
            )
        }



        Column(

        ) {
            Text(
                text = " ${product.totalQuantity}",
                style = CustomTypography.h2.copy(color = LightColors.onSecondary)
            )
        }



        Column(

        ) {
            Text(
                text = " ${product.remainingQuantity}",
                style = CustomTypography.h2.copy(color = LightColors.onSecondary)
            )
        }
    }
}
