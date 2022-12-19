package co.nimblehq.ic.kmm.suv.android.ui.theme

import androidx.compose.runtime.*
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Stable
class Dimensions(mediumPadding: Dp, defaultComponentHeight: Dp) {
    var mediumPadding by mutableStateOf(mediumPadding, structuralEqualityPolicy())
        internal set

    var defaultComponentHeight by mutableStateOf(defaultComponentHeight, structuralEqualityPolicy())
        internal set
}

fun appDimensions(
    mediumPadding: Dp = 20.dp,
    defaultComponentHeight: Dp = 56.dp
): Dimensions = Dimensions(mediumPadding, defaultComponentHeight)

internal val LocalDimensions = staticCompositionLocalOf { appDimensions() }
