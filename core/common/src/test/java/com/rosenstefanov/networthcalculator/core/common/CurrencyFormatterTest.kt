package com.rosenstefanov.networthcalculator.core.common

import com.google.common.truth.Truth.assertThat
import org.junit.jupiter.api.Test

class CurrencyFormatterTest {

    private val formatter = CurrencyFormatter()

    @Test
    fun `empty input returns empty`() {
        assertThat(formatter.format("")).isEmpty()
    }

    @Test
    fun `zero-only input returns empty`() {
        assertThat(formatter.format("0")).isEmpty()
        assertThat(formatter.format("000")).isEmpty()
    }

    @Test
    fun `groups thousands`() {
        assertThat(formatter.format("7")).isEqualTo("7")
        assertThat(formatter.format("58400")).isEqualTo("58,400")
        assertThat(formatter.format("1234567")).isEqualTo("1,234,567")
    }

    @Test
    fun `strips non-digits and leading zeros`() {
        assertThat(formatter.format("58,40a0")).isEqualTo("58,400")
        assertThat(formatter.format("\$1,000")).isEqualTo("1,000")
        assertThat(formatter.format("007")).isEqualTo("7")
    }
}
