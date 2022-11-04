package co.nimblehq.ic.kmm.suv.android.ui.theme

import androidx.compose.material.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import co.nimblehq.ic.kmm.suv.android.R

private val Neuzeit = FontFamily(
    Font(R.font.neuzeit_book, FontWeight.Normal),
    Font(R.font.neuzeit_heavy, FontWeight.Bold)
)

// Set of Material typography styles to start with
val Typography = Typography(
    defaultFontFamily = Neuzeit,
    h4 = TextStyle(
        fontWeight = FontWeight.Bold,
        fontSize = 34.sp
    ),
    subtitle1 = TextStyle(
        fontWeight = FontWeight.Normal,
        fontSize = 17.sp
    ),
    subtitle2 = TextStyle(
        fontWeight = FontWeight.Bold,
        fontSize = 17.sp
    ),
    caption = TextStyle(
        fontWeight = FontWeight.Bold,
        fontSize = 13.sp
    )
)
