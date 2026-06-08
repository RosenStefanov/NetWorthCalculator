package com.rosenstefanov.networthcalculator.core.ui.component

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
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
fun NetWorthSegmentedToggle(
    options: List<String>,
    selected: String,
    onSelected: (String) -> Unit,
    modifier: Modifier = Modifier,
) {
    val isDark = MaterialTheme.colorScheme.surface.luminance() < 0.5f
    val buttonShape = RoundedCornerShape(10.dp)

    Row(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(14.dp))
            .background(NetWorthTheme.extendedColors.field)
            .padding(4.dp),
        horizontalArrangement = Arrangement.spacedBy(4.dp),
    ) {
        options.forEach { option ->
            val isSelected = option == selected
            val selectedBg = if (isDark) Color.White.copy(alpha = 0.16f) else Color.White

            val selectedModifier = when {
                isSelected && !isDark ->
                    Modifier
                        .shadow(elevation = 2.dp, shape = buttonShape, spotColor = ToggleShadow, ambientColor = ToggleShadow)
                        .clip(buttonShape)
                        .background(selectedBg)
                isSelected ->
                    Modifier.clip(buttonShape).background(selectedBg)
                else ->
                    Modifier.clip(buttonShape)
            }

            Box(
                modifier = Modifier
                    .weight(1f)
                    .then(selectedModifier)
                    .clickable { onSelected(option) }
                    .padding(vertical = 8.dp),
                contentAlignment = Alignment.Center,
            ) {
                Text(
                    text = option,
                    fontFamily = JakartaSans,
                    fontSize = 12.5.sp,
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
}

private val ToggleShadow = Color(0xFF1E1646)
