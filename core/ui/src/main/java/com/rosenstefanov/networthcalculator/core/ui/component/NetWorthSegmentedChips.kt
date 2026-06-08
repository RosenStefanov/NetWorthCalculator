package com.rosenstefanov.networthcalculator.core.ui.component

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.luminance
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.rosenstefanov.networthcalculator.core.ui.theme.JakartaSans
import com.rosenstefanov.networthcalculator.core.ui.theme.NetWorthTheme

@Composable
fun NetWorthSegmentedChips(
    options: List<String>,
    selected: String,
    onSelected: (String) -> Unit,
    modifier: Modifier = Modifier,
) {
    val isDark = MaterialTheme.colorScheme.surface.luminance() < 0.5f
    val chipShape = RoundedCornerShape(7.dp)

    Row(
        modifier = modifier
            .clip(RoundedCornerShape(9.dp))
            .background(NetWorthTheme.extendedColors.field)
            .padding(3.dp),
    ) {
        options.forEach { option ->
            val isSelected = option == selected
            val selectedBg = if (isDark) Color.White.copy(alpha = 0.16f) else Color.White

            val chipModifier = when {
                isSelected && !isDark ->
                    Modifier
                        .shadow(elevation = 3.dp, shape = chipShape, spotColor = ChipShadow, ambientColor = ChipShadow)
                        .clip(chipShape)
                        .background(selectedBg)
                isSelected ->
                    Modifier.clip(chipShape).background(selectedBg)
                else ->
                    Modifier.clip(chipShape)
            }

            Text(
                text = option,
                modifier = chipModifier
                    .clickable { onSelected(option) }
                    .padding(horizontal = 9.dp, vertical = 4.dp),
                fontFamily = JakartaSans,
                fontSize = 11.sp,
                fontWeight = FontWeight.SemiBold,
                color = if (isSelected) {
                    MaterialTheme.colorScheme.onSurface
                } else {
                    NetWorthTheme.extendedColors.inkSub
                },
            )
        }
    }
}

private val ChipShadow = Color(0xFF1E1646)
