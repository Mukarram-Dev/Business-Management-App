import androidx.compose.material.MaterialTheme
import androidx.compose.material.Typography
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.mukarram.businessmanagementapp.R




private val DeepBlue = Color(0xff000080)
private val LightGrayBackground = Color(0xFFF5F5F5)
private val HeaderFooter = Color(0xFF003366)
private val forButton = Color(0xFF007BFF)
private val complementryforButton = Color(0xFFFFA500)
private val IconsColor = Color(0xFFFFFFFF)
private val TextColor = Color(0xFF003366)
private val onBackgroundText = Color(0xFFF8F8FF)

// Define the custom font
private val customFontFamily = FontFamily(
    Font(R.font.roboto_bold),
    Font(R.font.poppins_medium)
)


// Custom theme
@Composable
fun BizSolutionsTheme(content: @Composable () -> Unit) {
    MaterialTheme(
        colors = LightColors,
        typography=CustomTypography,
        content = content
    )
}



val LightColors= lightColors(
    primary = HeaderFooter,
    secondary = forButton,
    background = LightGrayBackground,
    surface=complementryforButton,
    onPrimary = IconsColor,
    onSecondary = TextColor,
    onBackground = onBackgroundText,
    onSurface = Color.Black
)

val gothicA1 = FontFamily(
    listOf(
        Font(R.font.roboto_bold),
        Font(R.font.roboto),
        Font(R.font.roboto_medium),

        )
)


val gothicA2 = FontFamily(
    listOf(
        Font(R.font.poppins_regular),
        Font(R.font.poppins_bold),
        Font(R.font.poppins_medium),

        )
)


// Set of Material typography styles to start with
val CustomTypography = Typography(
    body1 = TextStyle(
        color = LightColors.onBackground,
        fontFamily = gothicA2,
        fontWeight = FontWeight.Normal,
        fontSize = 14.sp
    ),
    h1 = TextStyle(
        color = LightColors.onPrimary,
        fontFamily = gothicA1,
        fontWeight = FontWeight.Bold,
        fontSize = 22.sp
    ),
    h2 = TextStyle(
        color = LightColors.onSecondary,
        fontFamily = gothicA1,
        fontWeight = FontWeight.Bold,
        fontSize = 18.sp,

    ),
    subtitle1 = TextStyle(
        color = LightColors.onPrimary,
        fontFamily = gothicA2,
        fontWeight = FontWeight.Bold,
        fontSize = 16.sp
    ),
    subtitle2 = TextStyle(
        color = LightColors.onPrimary,
        fontFamily = gothicA2,
        fontWeight = FontWeight.Medium,
        fontSize = 14.sp
    ),

    )
