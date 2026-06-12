package com.rosenstefanov.networthcalculator.core.ui.models

import androidx.annotation.DrawableRes
import com.rosenstefanov.networthcalculator.core.ui.icon.NetWorthIcons

data class Category(
    val name: String,
    @DrawableRes val icon: Int,
)

val AssetCategories: List<Category> = listOf(
    Category("Cash", NetWorthIcons.Cash),
    Category("Investments", NetWorthIcons.ChartUp),
    Category("Retirement", NetWorthIcons.Plant),
    Category("Real Estate", NetWorthIcons.Home),
    Category("Vehicles", NetWorthIcons.Car),
    Category("Valuables", NetWorthIcons.Gem),
    Category("Other", NetWorthIcons.Grid),
)

val LiabilityCategories: List<Category> = listOf(
    Category("Mortgage", NetWorthIcons.Home),
    Category("Loans", NetWorthIcons.Wallet),
    Category("Credit Card", NetWorthIcons.Card),
    Category("Taxes", NetWorthIcons.Receipt),
    Category("Medical", NetWorthIcons.Heart),
    Category("Other", NetWorthIcons.Grid),
)
