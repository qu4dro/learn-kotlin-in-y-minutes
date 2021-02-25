package ru.orlovvv.kotlin.csc_lections.lection4_fp_in_kotlin

import java.util.*
import javax.lang.model.type.ErrorType

fun <T> stringifyList(list: List<T>, f: (T) -> String): List<String> {
    val res = mutableListOf<String>()
    for (e in list) {
//        res.add(f.invoke(e))
        res.add(f(e))
    }
    return res
}

class ErrorProcessor {
    //на уровне функции
//    fun buildErrorDescList(): List<String> {
//        val errorList = getErrorList()
//        return stringifyList(errorList, ::anyToString)
//    }

    //на уровне метода
    fun buildErrorDesc(e: Error): String = e.toString()
//    fun buildErrorDescList(): List<String> {
//        val errorList = getErrorList()
//        return stringifyList(errorList, this::buildErrorDesc)
//    }

    //    fun buildErrorDescList(): List<String> {
//        val errorList = getErrorList()
//        return stringifyList(errorList, fun(e): String {return e.toString})
//
//    }

    fun buildErrorDescList(): List<String> {
        val errorList = getErrorList()
        return stringifyList(errorList) { e: Error -> e.toString() }
    }

    private val freshErrorId
        get() = UUID.randomUUID()
    private val ignoredErrorTypes = mutableSetOf<ErrorType>()

    fun buildErrorDesc(): List<String> = getErrorList()
        .filter { it.type !in ignoredErrorTypes }
        .map { freshErrorId to it }
        .map { (id, error) -> "[id] ${error.msg}" }
}

//T.() вызов функции только над объектом определенного типа
fun <T, R> with(receiver: T, block: T.() -> R): R = receiver.block()

//How to return

inline fun bar(angle: Double, f: (Double) -> Double): Double {
    return f(angle)
}

fun main() {
    bar(Math.PI) { angle ->
        if (angle == 0.0) return@bar Double.NaN
        Math.sin(angle) + Math.cos(angle)

    }
}