package com.rosenstefanov.networthcalculator.core.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.saveable.Saver
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.navigation3.runtime.NavKey
import kotlinx.serialization.InternalSerializationApi
import kotlinx.serialization.KSerializer
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonObject
import kotlinx.serialization.json.addJsonArray
import kotlinx.serialization.json.buildJsonObject
import kotlinx.serialization.json.int
import kotlinx.serialization.json.jsonArray
import kotlinx.serialization.json.jsonObject
import kotlinx.serialization.json.jsonPrimitive
import kotlinx.serialization.json.put
import kotlinx.serialization.json.putJsonArray
import kotlinx.serialization.serializer

@Composable
fun rememberNavigator(topLevelRoutes: List<NavKey>): Navigator =
    rememberSaveable(saver = navigatorSaver(topLevelRoutes)) {
        Navigator(topLevelRoutes)
    }

internal fun navigatorSaver(topLevelRoutes: List<NavKey>): Saver<Navigator, String> =
    Saver(
        save = { encodeState(it.currentTabIndex, it.snapshotBackStacks()) },
        restore = { encoded ->
            val (tabIndex, backStacks) = decodeState(encoded)
            Navigator(topLevelRoutes).also { it.restore(tabIndex, backStacks) }
        },
    )

private val saverJson = Json

private fun encodeState(tabIndex: Int, backStacks: List<List<NavKey>>): String =
    buildJsonObject {
        put("tab", tabIndex)
        putJsonArray("stacks") {
            backStacks.forEach { stack ->
                addJsonArray {
                    stack.forEach { key -> add(encodeKey(key)) }
                }
            }
        }
    }.toString()

private fun encodeKey(key: NavKey): JsonObject = buildJsonObject {
    put("type", key.javaClass.name)
    put("value", saverJson.encodeToString(serializerForClass(key::class), key))
}

private fun decodeState(encoded: String): Pair<Int, List<List<NavKey>>> {
    val root = saverJson.parseToJsonElement(encoded).jsonObject
    val tabIndex = root.getValue("tab").jsonPrimitive.int
    val backStacks = root.getValue("stacks").jsonArray.map { stack ->
        stack.jsonArray.map { keyElement -> decodeKey(keyElement.jsonObject) }
    }
    return tabIndex to backStacks
}

private fun decodeKey(obj: JsonObject): NavKey {
    val type = obj.getValue("type").jsonPrimitive.content
    val value = obj.getValue("value").jsonPrimitive.content
    return saverJson.decodeFromString(serializerForClass(Class.forName(type).kotlin), value)
}

@OptIn(InternalSerializationApi::class)
@Suppress("UNCHECKED_CAST")
private fun serializerForClass(kClass: kotlin.reflect.KClass<*>): KSerializer<NavKey> =
    (kClass as kotlin.reflect.KClass<Any>).serializer() as KSerializer<NavKey>
