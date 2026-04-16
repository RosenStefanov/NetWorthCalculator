package com.rosenstefanov.networthcalculator.core.testing

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

abstract class FakeRepository<T>(initialData: List<T> = emptyList()) {

    private val _data = MutableStateFlow(initialData)
    val data: Flow<List<T>> = _data.asStateFlow()

    fun emit(items: List<T>) {
        _data.value = items
    }

    fun emit(vararg items: T) {
        _data.value = items.toList()
    }

    fun add(item: T) {
        _data.update { current -> current + item }
    }

    fun remove(predicate: (T) -> Boolean) {
        _data.update { current -> current.filterNot(predicate) }
    }

    fun clear() {
        _data.value = emptyList()
    }
}
