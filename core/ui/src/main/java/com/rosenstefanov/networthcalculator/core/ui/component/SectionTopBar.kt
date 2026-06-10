package com.rosenstefanov.networthcalculator.core.ui.component

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.em
import androidx.compose.ui.unit.sp
import com.rosenstefanov.networthcalculator.core.ui.icon.NetWorthIcons
import com.rosenstefanov.networthcalculator.core.ui.theme.JakartaSans

@Composable
fun SectionTopBar(
    title: String,
    addBrush: Brush,
    addGlow: Color,
    onSettings: () -> Unit,
    onAdd: () -> Unit,
    modifier: Modifier = Modifier,
) {
    NetWorthTopAppBar(
        modifier = modifier,
        horizontalPadding = 22.dp,
        leading = {
            Text(
                text = title,
                fontFamily = JakartaSans,
                fontSize = 20.sp,
                fontWeight = FontWeight.ExtraBold,
                letterSpacing = (-0.02).em,
                color = MaterialTheme.colorScheme.onBackground,
            )
        },
        actions = {
            NetWorthTopBarIconButton(
                icon = NetWorthIcons.SettingsGear,
                contentDescription = "Settings",
                onClick = onSettings,
            )
            NetWorthGradientIconButton(
                icon = NetWorthIcons.Plus,
                contentDescription = "Add account",
                brush = addBrush,
                glow = addGlow,
                onClick = onAdd,
            )
        },
    )
}
