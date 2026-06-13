package com.rosenstefanov.networthcalculator.core.ui.component

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.rosenstefanov.networthcalculator.core.ui.models.AssetCategories
import com.rosenstefanov.networthcalculator.core.ui.models.Category
import com.rosenstefanov.networthcalculator.core.ui.models.LiabilityCategories
import com.rosenstefanov.networthcalculator.core.ui.theme.JakartaSans
import com.rosenstefanov.networthcalculator.core.ui.theme.NetWorthCalculatorTheme
import com.rosenstefanov.networthcalculator.core.ui.theme.NetWorthTheme

private val ChipShape = RoundedCornerShape(12.dp)

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun CategorySection(
    categories: List<Category>,
    selected: Category,
    accent: Color,
    onSelect: (Category) -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(modifier) {
        Text(
            text = "Category",
            fontFamily = JakartaSans,
            fontSize = 12.5.sp,
            fontWeight = FontWeight.SemiBold,
            color = NetWorthTheme.extendedColors.inkSub,
            modifier = Modifier.padding(bottom = 9.dp),
        )
        FlowRow(
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            categories.forEach { category ->
                CategoryChip(
                    category = category,
                    selected = category == selected,
                    accent = accent,
                    onClick = { onSelect(category) },
                )
            }
        }
    }
}

@Composable
private fun CategoryChip(
    category: Category,
    selected: Boolean,
    accent: Color,
    onClick: () -> Unit,
) {
    Row(
        modifier = Modifier
            .clip(ChipShape)
            .background(if (selected) accent.copy(alpha = 0.07f) else MaterialTheme.colorScheme.surface)
            .border(
                width = 1.5.dp,
                color = if (selected) accent else NetWorthTheme.extendedColors.line,
                shape = ChipShape,
            )
            .clickable(onClick = onClick)
            .padding(horizontal = 13.dp, vertical = 9.dp),
        horizontalArrangement = Arrangement.spacedBy(7.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Icon(
            painter = painterResource(category.icon),
            contentDescription = null,
            tint = if (selected) accent else NetWorthTheme.extendedColors.inkSub,
            modifier = Modifier.size(16.dp),
        )
        Text(
            text = category.name,
            fontFamily = JakartaSans,
            fontSize = 13.sp,
            fontWeight = FontWeight.SemiBold,
            color = if (selected) accent else MaterialTheme.colorScheme.onSurface,
        )
    }
}

@Preview(showBackground = true, backgroundColor = 0xFFEEF0F6)
@Composable
internal fun CategorySectionAssetPreview() {
    NetWorthCalculatorTheme {
        CategorySection(
            categories = AssetCategories,
            selected = AssetCategories[1],
            accent = Color(0xFF3B6BFF),
            onSelect = {},
            modifier = Modifier.padding(16.dp),
        )
    }
}

@Preview(showBackground = true, backgroundColor = 0xFFEEF0F6)
@Composable
internal fun CategorySectionLiabilityPreview() {
    NetWorthCalculatorTheme {
        CategorySection(
            categories = LiabilityCategories,
            selected = LiabilityCategories[0],
            accent = Color(0xFF9333EA),
            onSelect = {},
            modifier = Modifier.padding(16.dp),
        )
    }
}
