package org.pointyware.xyz.core.ui

import androidx.compose.ui.tooling.preview.Preview

private const val popularSizeGroup = "popular sizes"
@Preview(
    name = "small device",
    group = popularSizeGroup
)
@Preview(
    name = "large device",
    group = popularSizeGroup,
    widthDp = 800,
    heightDp = 500
)
annotation class PopularSizePreviews
