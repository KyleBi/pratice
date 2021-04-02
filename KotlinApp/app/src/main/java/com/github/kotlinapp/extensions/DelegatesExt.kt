package com.github.kotlinapp.extensions

import android.content.Context
import android.content.SharedPreferences
import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

object DelegatesExt {
    fun <T> notNullSingleValue() = NotNullSingleValueVar<T>()
    fun <T> preference(ctx: Context, name: String, default: T) = Preference(ctx, name, default)
}


class NotNullSingleValueVar<T> {

    private var value: T? = null

    operator fun getValue(thisRef: Any?, property: KProperty<*>): T =
        value ?: throw IllegalStateException("${property.name} not initialized")

    operator fun setValue(thisRef: Any?, property: KProperty<*>, value: T) {
        this.value =
            if (this.value == null) value else throw IllegalStateException("${property.name} already initialized")
    }
}


class Preference<T>(private val ctx: Context, val name: String, private val default: T) {

    private val prefs: SharedPreferences by lazy {
        ctx.getSharedPreferences("default", Context.MODE_PRIVATE)
    }

    operator fun setValue(thisRef: Any?, property: KProperty<*>, value: T) {
        putPreference(name, value)
    }

    operator fun getValue(thisRef: Any?, property: KProperty<*>): T = findPreference(name, default)

    @Suppress("UNCHECKED_CAST")
    private fun findPreference(name: String, default: T): T = with(prefs) {
        val result: Any = when (default) {
            is Long -> getLong(name, default)
            is Int -> getInt(name, default)
            is Boolean -> getBoolean(name, default)
            is Float -> getFloat(name, default)
            else -> throw IllegalArgumentException("This type can be saved into Preferences")
        }

        result as T
    }

    private fun putPreference(name: String, value: T) = with(prefs.edit()) {
        when (value) {
            is Long -> putLong(name, value)
            is String -> putString(name, value)
            is Int -> putInt(name, value)
            is Boolean -> putBoolean(name, value)
            is Float -> putFloat(name, value)
            else -> throw IllegalArgumentException("This type can't be saved into Preferences")
        }.apply()
    }
}