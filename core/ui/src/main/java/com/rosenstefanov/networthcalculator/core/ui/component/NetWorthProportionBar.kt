package com.rosenstefanov.networthcalculator.core.ui.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.rosenstefanov.networthcalculator.core.ui.theme.SemanticAssets
import com.rosenstefanov.networthcalculator.core.ui.theme.SemanticLiabilities

@Composable
fun NetWorthProportionBar(
    assetsWeight: Float,
    modifier: Modifier = Modifier,
    assetsColor: Color = SemanticAssets,
    liabilitiesColor: Color = SemanticLiabilities,
) {
    val assets = assetsWeight.coerceIn(0f, 1f)
    val liabilities = 1f - assets

    Row(
        modifier = modifier
            .fillMaxWidth()
            .height(14.dp),
        horizontalArrangement = Arrangement.spacedBy(4.dp),
    ) {
        if (assets > 0f) segment(assets, assetsColor)
        if (liabilities > 0f) segment(liabilities, liabilitiesColor)
    }
}

@Composable
private fun RowScope.segment(weight: Float, color: Color) {
    Box(
        Modifier
            .weight(weight)
            .fillMaxHeight()
            .clip(RoundedCornerShape(5.dp))
            .background(color),
    )
}
