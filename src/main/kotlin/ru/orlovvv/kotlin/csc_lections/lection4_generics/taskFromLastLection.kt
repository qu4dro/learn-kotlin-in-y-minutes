package ru.orlovvv.kotlin.csc_lections.lection4_generics

//параметрический полиморфизм

fun <T> anyToString(value: T): String = value.toString()

class ValueWrapper<T>(val value: T) {
    val valueAsString = value.toString()
}