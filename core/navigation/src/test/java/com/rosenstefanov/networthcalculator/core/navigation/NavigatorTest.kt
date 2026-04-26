package com.rosenstefanov.networthcalculator.core.navigation

import androidx.navigation3.runtime.NavKey
import com.google.common.truth.Truth.assertThat
import kotlinx.serialization.Serializable
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

class NavigatorTest {

    @Serializable
    private data object TabA : NavKey

    @Serializable
    private data object TabB : NavKey

    @Serializable
    private data class Detail(val id: Long) : NavKey

    private val tabs = listOf<NavKey>(TabA, TabB)

    @Test
    fun `starts on first top-level route`() {
        val navigator = Navigator(tabs)

        assertThat(navigator.currentTab).isEqualTo(TabA)
        assertThat(navigator.currentBackStack.toList()).containsExactly(TabA)
    }

    @Test
    fun `navigate to top-level route switches tab`() {
        val navigator = Navigator(tabs)

        navigator.navigate(TabB)

        assertThat(navigator.currentTab).isEqualTo(TabB)
        assertThat(navigator.currentBackStack.toList()).containsExactly(TabB)
    }

    @Test
    fun `navigate to non-top-level route pushes onto current tab`() {
        val navigator = Navigator(tabs)

        navigator.navigate(Detail(42))

        assertThat(navigator.currentTab).isEqualTo(TabA)
        assertThat(navigator.currentBackStack.toList())
            .containsExactly(TabA, Detail(42))
            .inOrder()
    }

    @Test
    fun `goBack pops from current tab`() {
        val navigator = Navigator(tabs)
        navigator.navigate(Detail(1))

        navigator.goBack()

        assertThat(navigator.currentBackStack.toList()).containsExactly(TabA)
    }

    @Test
    fun `goBack does nothing at tab root`() {
        val navigator = Navigator(tabs)

        navigator.goBack()

        assertThat(navigator.currentTab).isEqualTo(TabA)
        assertThat(navigator.currentBackStack.toList()).containsExactly(TabA)
    }

    @Test
    fun `each tab has independent back stack`() {
        val navigator = Navigator(tabs)

        navigator.navigate(Detail(1))          // push onto TabA
        navigator.navigate(TabB)                // switch to TabB
        assertThat(navigator.currentBackStack.toList()).containsExactly(TabB)

        navigator.navigate(TabA)                // back to TabA
        assertThat(navigator.currentBackStack.toList())
            .containsExactly(TabA, Detail(1))
            .inOrder()
    }

    @Test
    fun `constructor rejects empty top-level route list`() {
        assertThrows<IllegalArgumentException> {
            Navigator(emptyList())
        }
    }
}
