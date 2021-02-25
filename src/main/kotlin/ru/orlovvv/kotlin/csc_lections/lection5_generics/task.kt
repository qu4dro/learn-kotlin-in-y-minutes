package ru.orlovvv.kotlin.csc_lections.lection5_generics

//параметрический полиморфизм

fun <T> anyToString(value: T): String = value.toString()

class ValueWrapper<T>(val value: T) {
    val valueAsString = value.toString()
}

// ковариантность
class List<out T> {
    // out T === можно доставать T или любой его тип
}

// контрвариантность
interface Processor<in T> {
    // in T === можно положить T или любого его наследника
}

// Примеры
interface SecondProcessor<in T> {
    fun process(item: T): Unit = TODO()
}

fun processStuff() {
    val pUniversal: SecondProcessor<Any> = object : SecondProcessor<Any> {
        override fun process(item: Any) {
            TODO()
        }
    }
    val pString: SecondProcessor<String> = pUniversal
}

// <*> неизвестно что за тип у дженерика
// в котлине есть inline функции
// так как тело встраивается в место вызова, а при встраивании мы можем сохранить фактический тип дженерика...
inline fun <reified R> Array<*>.filterIsInstance(): MutableList<R> {
    val res = mutableListOf<R>()
    for (e in this)
        if (e is R) res += e
    return res
}

// иногда встречается очень длинный тип, хотелось бы писать его компактнее
// для таких случаев есть typealias
val userData: MutableMap<String, MutableMap<String, Any>> = TODO()

fun mergeUserData(other: MutableMap<String, MutableMap<String, Any>>) = TODO()

typealias UserDataAlias<T> = MutableMap<String, MutableMap<String, T>>
// ->
val userDataWithAlias: UserDataAlias<Any> = TODO()
fun mergeUserDataWithAlias(other: UserDataAlias<Any>) = TODO()