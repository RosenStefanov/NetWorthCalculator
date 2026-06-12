package com.rosenstefanov.networthcalculator.core.common

import javax.inject.Inject

class CurrencyFormatter @Inject constructor() {

    fun format(input: String): String {
        val digits = input.filter(Char::isDigit).trimStart('0')
        if (digits.isEmpty()) return ""
        val out = StringBuilder()
        val n = digits.length
        for (i in 0 until n) {
            if (i > 0 && (n - i) % 3 == 0) out.append(',')
            out.append(digits[i])
        }
        return out.toString()
    }
}
