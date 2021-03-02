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

// Использование коллекции String'ов и чтение из нее Object нормально только в том случае, если вы
// берете элементы их коллекции. Наоборот, если вы только вносите элементы в коллекцию, то нормально брать
// коллекцию Object и помещать в нее String.

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

// Проекция типов
// вариативность на месте использования
/* Объявлять параметризированный тип T как out очень удобно: при его использовании
не будет никаких проблем с подтипами. И это действительно так в случае с классами, которын могут
быть ограничены на только возвращение T. А как быть с теми классами, которые еще и принимают
T?
 */
class Array<T>(val size: Int) {
    fun get(index: Int): T {
        TODO()
    }

    fun set(index: Int, value: T) {
        TODO()
    }
}

// этот класс не может быть ни ко-, ни контрвариантным по T, что ведет к некоторому снижению гибкости.
// Теперь рассмотрим функцию, которая по задумке должна копировать значения из одного массива в другой.
fun copy(from: Array<Int>, to: Array<Any>) {
    assert(from.size == to.size)
    for (i in from.indices)
        to[i] = from[i]
}

// однако ошибка
val ints: Array<Int> = arrayOf(1, 2, 3)
val any = Array<Any>(3)
copy(ints, any) // ошибка, ожидалось (Array<Any>, Array<Any>)

/* Здесь Array<T> инвариантен по T, таким образом Array<Int> не является подтипом Array<Any>, так как копирование
 потенциально опасно

 Таким образом необходимо удостоверится, что copy() не сделает ничего плохого. Мы хотим запретить методу записывать
 в from
 */

fun copy2(from: Array<out Any>, to: Array<Any>) {

}
/* Это называется проекция типов.
Мы сказали, что from - не просто массив, а ограниченный (спроецированный): мы можем вызвать те методы,
которые возвращают параметризированный тип T, что в этом случае означает, что мы можем вызвать только get().
 */

// Еще раз про звездные проекции
/* Иногда вы ничего не знаете о типе аргумента, но хотите использовать его безопасным образом
   Этой безопасности можно добиться путем определения такой проекции параметризованного типа, при
   котором его экземпляр будет подтипом этой проекции.
 */

/* Для Foo<out T>, где T - ковариантный параметризированный тип с верхней граничей TUpper,
   Foo<*> является эквивалентом Foo<out TUpper>. Это значит, что когда T неизвестен, вы можете
   безопасно читать значения типа TUpper из Foo<*>.

   Для Foo<in T>, где T - контрвариантный параметризованный тип, Foo<*> является эквивалентом
   Foo<in Nothing>. Это значит, что вы не можете безопасно писать Foo<*> при неизвестном T.

   Для Foo<T>, где T - инвариантный параметризованный тип с верхней гранцией TUpper, Foo<*>
   является эквивалентом Foo<out TUpper> при чтении значений и Foo<in Nothing> при записи значений.
 */

//Если параметризованный тип имеет несколько параметров, каждый из них проецируется независимо.
//Например, если тип объявлен как interface Function<in T, out U>, мы можем представить следующую "звёздную" проекцию:
//
//Function<*, String> означает Function<in Nothing, String>;
//Function<Int, *> означает Function<Int, out Any?>;
//Function<*, *> означает Function<in Nothing, out Any?>.
//Примечаение: "звёздные" проекции очень похожи на сырые (raw) типы из Java, за тем исключением, что являются безопасными.

// Обобщенные ограничения
// Самый распространенный тип ограничений - верхняя граница
fun <T : Comparable<T>> sort(list: List<T>) {

}
// Тип, указанный после двоеточия, является верхней границей: только подтип Comparable<T> может быть передан в T

//По умолчанию (если не указана явно) верхняя граница — Any?. В угловых скобках может быть указана только одна верхняя граница.
//Для указания нескольких верхних границ нужно использовать отдельное условие where:

fun <T> cloneWhenGreater(list: List<T>, threshold: T): List<T>
        where T : Comparable,
              T : Cloneable {
    return list.filter { it > threshold }.map { it.clone() }
}