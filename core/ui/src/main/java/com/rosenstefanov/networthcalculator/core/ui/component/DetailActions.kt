package com.rosenstefanov.networthcalculator.core.ui.component

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.rosenstefanov.networthcalculator.core.ui.icon.NetWorthIcons
import com.rosenstefanov.networthcalculator.core.ui.theme.JakartaSans
import com.rosenstefanov.networthcalculator.core.ui.theme.NetWorthBrandBrush
import com.rosenstefanov.networthcalculator.core.ui.theme.NetWorthCalculatorTheme

private val ButtonShape = RoundedCornerShape(15.dp)
private val DeleteBorder = Color(0xFFF3D6E0)
private val DeleteTint = Color(0xFFE0457B)

@Composable
fun DetailActions(
    accentGradient: Brush,
    glow: Color,
    onEdit: () -> Unit,
    onDelete: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(11.dp),
    ) {
        Row(
            modifier = Modifier
                .weight(1f)
                .height(50.dp)
                .shadow(10.dp, ButtonShape, ambientColor = glow, spotColor = glow)
                .clip(ButtonShape)
                .background(accentGradient)
                .clickable(onClick = onEdit),
            horizontalArrangement = Arrangement.spacedBy(8.dp, Alignment.CenterHorizontally),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Icon(
                painter = painterResource(NetWorthIcons.Edit),
                contentDescription = null,
                tint = Color.White,
                modifier = Modifier.size(18.dp),
            )
            Text(
                text = "Edit",
                fontFamily = JakartaSans,
                fontSize = 14.5.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White,
            )
        }
        Box(
            modifier = Modifier
                .size(50.dp)
                .clip(ButtonShape)
                .background(Color.White)
                .border(1.5.dp, DeleteBorder, ButtonShape)
                .clickable(onClick = onDelete),
            contentAlignment = Alignment.Center,
        ) {
            Icon(
                painter = painterResource(NetWorthIcons.Trash),
                contentDescription = "Delete",
                tint = DeleteTint,
                modifier = Modifier.size(19.dp),
            )
        }
    }
}

@Preview(showBackground = true, backgroundColor = 0xFFEEF0F6)
@Composable
internal fun DetailActionsPreview() {
    NetWorthCalculatorTheme {
        DetailActions(
            accentGradient = NetWorthBrandBrush,
            glow = Color(0xFF5B45F5).copy(alpha = 0.5f),
            onEdit = {},
            onDelete = {},
            modifier = Modifier.padding(16.dp),
        )
    }
}
