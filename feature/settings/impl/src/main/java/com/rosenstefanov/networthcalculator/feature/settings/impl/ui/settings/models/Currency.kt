package com.rosenstefanov.networthcalculator.feature.settings.impl.ui.settings.models

data class Currency(
    val symbol: String,
    val code: String,
    val name: String,
)

val PopularCurrencies: List<Currency> = listOf(
    Currency("$", "USD", "US Dollar"),
    Currency("€", "EUR", "Euro"),
    Currency("£", "GBP", "British Pound"),
)

val AllCurrencies: List<Currency> = listOf(
    Currency("$", "AUD", "Australian Dollar"),
    Currency("$", "CAD", "Canadian Dollar"),
    Currency("Fr", "CHF", "Swiss Franc"),
    Currency("¥", "CNY", "Chinese Yuan"),
    Currency("₹", "INR", "Indian Rupee"),
    Currency("¥", "JPY", "Japanese Yen"),
)
