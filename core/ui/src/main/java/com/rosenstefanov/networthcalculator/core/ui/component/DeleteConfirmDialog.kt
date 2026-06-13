package com.rosenstefanov.networthcalculator.core.ui.component

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.rosenstefanov.networthcalculator.core.ui.icon.NetWorthIcons
import com.rosenstefanov.networthcalculator.core.ui.theme.JakartaSans

private val Negative = Color(0xFFE0457B)
private val CancelBackground = Color(0xFFF1F2F8)
private val Ink = Color(0xFF16182B)
private val InkSub = Color(0xFF6B6F86)
private val DialogShape = RoundedCornerShape(24.dp)
private val ButtonShape = RoundedCornerShape(15.dp)

@Composable
fun DeleteConfirmDialog(
    title: String,
    holdingName: String,
    confirmLabel: String,
    onConfirm: () -> Unit,
    onDismiss: () -> Unit,
) {
    Dialog(
        onDismissRequest = onDismiss,
        properties = DialogProperties(usePlatformDefaultWidth = false),
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 28.dp)
                .shadow(24.dp, DialogShape, spotColor = Color(0xFF141032).copy(alpha = 0.5f))
                .clip(DialogShape)
                .background(Color.White)
                .padding(start = 22.dp, end = 22.dp, top = 24.dp, bottom = 20.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Box(
                modifier = Modifier
                    .size(56.dp)
                    .clip(RoundedCornerShape(18.dp))
                    .background(Negative.copy(alpha = 0.12f)),
                contentAlignment = Alignment.Center,
            ) {
                Icon(
                    painter = painterResource(NetWorthIcons.Trash),
                    contentDescription = null,
                    tint = Negative,
                    modifier = Modifier.size(27.dp),
                )
            }
            Spacer(Modifier.height(16.dp))

            Text(
                text = title,
                fontFamily = JakartaSans,
                fontSize = 18.sp,
                fontWeight = FontWeight.ExtraBold,
                color = Ink,
                textAlign = TextAlign.Center,
            )
            Spacer(Modifier.height(8.dp))

            val body = buildAnnotatedString {
                append("This removes ")
                withStyle(
                    SpanStyle(
                        color = Ink,
                        fontWeight = FontWeight.Bold,
                    ),
                ) {
                    append(holdingName)
                }
                append(" and its history from your net worth. This can't be undone.")
            }
            Text(
                text = body,
                fontFamily = JakartaSans,
                fontSize = 13.5.sp,
                fontWeight = FontWeight.Medium,
                color = InkSub,
                lineHeight = 20.25.sp,
                textAlign = TextAlign.Center,
            )
            Spacer(Modifier.height(22.dp))

            DialogButton(
                label = confirmLabel,
                background = Negative,
                contentColor = Color.White,
                glow = Negative.copy(alpha = 0.5f),
                onClick = onConfirm,
            )
            Spacer(Modifier.height(10.dp))
            DialogButton(
                label = "Cancel",
                background = CancelBackground,
                contentColor = Ink,
                glow = null,
                onClick = onDismiss,
            )
        }
    }
}

@Composable
private fun DialogButton(
    label: String,
    background: Color,
    contentColor: Color,
    glow: Color?,
    onClick: () -> Unit,
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(50.dp)
            .then(
                if (glow != null) {
                    Modifier.shadow(10.dp, ButtonShape, ambientColor = glow, spotColor = glow)
                } else {
                    Modifier
                },
            )
            .clip(ButtonShape)
            .background(background)
            .clickable(onClick = onClick),
        contentAlignment = Alignment.Center,
    ) {
        Text(
            text = label,
            fontFamily = JakartaSans,
            fontSize = 14.5.sp,
            fontWeight = FontWeight.Bold,
            color = contentColor,
        )
    }
}
