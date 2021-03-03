// Single-line comments start with //
/*
Multi-line comments look like this.
*/

// The "package" keyword works in the same way as in Java.
package ru.orlovvv.kotlin

import java.awt.event.MouseAdapter
import java.awt.event.MouseEvent
import java.util.function.BinaryOperator
import java.util.function.IntBinaryOperator
import kotlin.properties.Delegates
import kotlin.reflect.KProperty

/*
The entry point to a Kotlin program is a function named "main".
The function is passed an array containing any command-line arguments.
Since Kotlin 1.3 the "main" function can also be defined without
any parameters.
*/
fun main(args: Array<String>) {
    /*
    Declaring values is done using either "var" or "val".
    "val" declarations cannot be reassigned, whereas "vars" can.
    */
    val fooVal = 5 // we cannot later reassign fooVal to something else
    var fooVar = 10
    fooVar = 20 // fooVar can be reassigned

    /*
    In most cases, Kotlin can determine what the type of a variable is,
    so we don't have to explicitly specify it every time.
    We can explicitly declare the type of a variable like so:
    */
    val foo: Int = 30

    /*
    Strings can be represented in a similar way as in Java.
    Escaping is done with a backslash.
    */
    val fooString = "This is string!"
    val barString = "On a new line?\nYes!"
    val bazString = "Add a new tab?\tYes!"
    println(fooString)
    println(barString)
    println(bazString)

    /*
    A raw string is delimited by a triple quote (""").
    Raw strings can contain newlines and any other characters.
    */
    val fooRawString = """
                fun helloWorld(val name : String) {
                    println("Hello, world!")
                }
                """
    println(fooRawString)

    /*
    Strings can contain template expressions.
    A template expression starts with a dollar sign ($).
    */
    val fooTemplateString = "$fooString has ${fooString.length} characters"
    println(fooTemplateString)

    /*
    For a variable to hold null it must be explicitly specified as nullable.
    A variable can be specified as nullable by appending a ? to its type.
    We can access a nullable variable by using the ?. operator.
    We can use the ?: operator to specify an alternative value to use
    if a variable is null.
    */
    var fooNullable: String? = "nullable string"
    println(fooNullable?.length)
    println(fooNullable?.length ?: -1)
    fooNullable = null
    println(fooNullable?.length)
    println(fooNullable?.length ?: -1)

    /*
    Functions can be declared using the "fun" keyword.
    Function arguments are specified in brackets after the function name.
    Function arguments can optionally have a default value.
    The function return type, if required, is specified after the arguments.
    */
    fun hello(name: String = "world"): String {
        return "Hello, $name!"
    }
    println(hello("foo"))
    println(hello(name = "bar"))
    println(hello())

    /*
    A function parameter may be marked with the "vararg" keyword
    to allow a variable number of arguments to be passed to the function.
    */
    fun varargExample(vararg names: Int) {
        println("Argument has ${names.size} elements")
    }
    varargExample()
    varargExample(1)
    varargExample(1, 2, 3)

    /*
    When a function consists of a single expression then the curly brackets can
    be omitted. The body is specified after the = symbol.
    */
    fun odd(x: Int): Boolean = x % 2 == 1
    println(odd(5))
    println(odd(10))

    // If the return type can be inferred then we don't need to specify it.
    fun even(x: Int) = x % 2 == 0
    println(even(5))
    println(even(10))

    // Functions can take functions as arguments and return functions.
    fun not(f: (Int) -> Boolean): (Int) -> Boolean {
        return { n -> !f.invoke(n) }
    }
    // Named functions can be specified as arguments using the :: operator.
    val notOdd = not(::odd)
    val notEven = not(::odd)
    // Lambda expressions can be specified as arguments.
    val notZero = not { n -> n == 0 }
    /*
    If a lambda has only one parameter
    then its declaration can be omitted (along with the ->).
    The name of the single parameter will be "it".
    */
    val notPositive = not { n -> n > 0 }
    val notPositive2 = not { it > 0 }
    for (i in 0..4) {
        println("${notOdd(i)} ${notEven(i)} ${notZero(i)} ${notPositive(i)} ${notPositive2(i)}")
    }

    // The "class" keyword is used to declare classes.
    class ExampleClass(val x: Int) {
        fun memberFunction(y: Int): Int {
            return x + y
        }

        infix fun infixMemberFunction(y: Int): Int {
            return x * y
        }
    }
    /*
    To create a new instance we call the constructor.
    Note that Kotlin does not have a "new" keyword.
    */
    val fooExampleClass = ExampleClass(10)
    // Member functions can be called using dot notation.
    println(fooExampleClass.memberFunction(5))
    /*
    If a function has been marked with the "infix" keyword then it can be
    called using infix notation.
    */
    println(fooExampleClass infixMemberFunction 6)

    /*
    Data classes are a concise way to create classes that just hold data.
    The "hashCode"/"equals" and "toString" methods are automatically generated.
    */
    data class DataClassExample(val x: Int, val y: Int, val z: Int)

    val fooData = DataClassExample(1, 2, 4)
    println(fooData)

    // Data classes have a "copy" function.
    val fooCopy = fooData.copy(y = 5)
    println(fooCopy)

    // Objects can be destructured into multiple variables.
    val (a, b, c) = fooCopy
    println("$a $b $c")

    // destructuring in "for" loop
    for ((a2, b2, c2) in listOf(fooData)) {
        println("$a2 $b2 $c2")
    }

    val mapData = mapOf("a" to 1, "b" to 2)
    // Map.Entry is destructurable as well
    for ((key, value) in mapData) {
        println("$key -> $value")
    }

    // The "with" function is similar to the JavaScript "with" statement.
    data class MutableDataClassExample(var x: Int, var y: Int, var z: Int)

    val fooMutableData = MutableDataClassExample(5, 10, 15)
    with(fooMutableData) {
        x += 5
        y -= 5
        z++
    }
    println(fooMutableData)

    /*
    We can create a list using the "listOf" function.
    The list will be immutable - elements cannot be added or removed.
    */
    val fooList = listOf("a", "b", "c")
    println(fooList.size)
    println(fooList.first())
    println(fooList.last())
    // Elements of a list can be accessed by their index.
    println(fooList[1])

    // A mutable list can be created using the "mutableListOf" function.
    val fooMutableList = mutableListOf("a", "b", "c")
    fooMutableList.add("d")
    println(fooMutableList.size)
    println(fooMutableList.last())

    // We can create a set using the "setOf" function.
    val fooSet = setOf("a", "b", "c")
    println(fooSet.contains("a"))
    println(fooSet.contains("z"))

    // We can create a map using the "mapOf" function.
    val fooMap = mapOf("a" to 8, "b" to 7, "c" to 9)
    // Map values can be accessed by their key.
    println(fooMap["a"])

    /*
    Sequences represent lazily-evaluated collections.
    We can create a sequence using the "generateSequence" function.
    */
    val fooSequence = generateSequence(1, { it + 1 })
    val x = fooSequence.take(10).toList()
    println(x)

    // An example of using a sequence to generate Fibonacci numbers:
    fun fibonacciSequence(): Sequence<Long> {
        var fibA = 0L
        var fibB = 1L

        fun next(): Long {
            val result = fibA + fibB
            fibA = fibB
            fibB = result
            return fibA
        }
        return generateSequence(::next)
    }

    val y = fibonacciSequence().take(10).toList()
    println(y)

    // Kotlin provides higher-order functions for working with collections.

    val z = (1..100).map { it * 3 }
        .filter { it < 30 }
        .groupBy { it % 2 == 0 }
        .mapKeys { if (it.key) "even" else "odd" }
    println(z) // => {odd=[3, 9, 15, 21, 27], even=[6, 12, 18, 24]}

    // A "for" loop can be used with anything that provides an iterator.
    for (char in "hello") {
        println(char)
    }

    // "while" loops work in the same way as other languages.
    var ctr = 0
    while (ctr < 5) {
        println(ctr)
        ctr++
    }
    do {
        println(ctr)
        ctr++
    } while (ctr < 10)

    /*
    "if" can be used as an expression that returns a value.
    For this reason the ternary ?: operator is not needed in Kotlin.
    */
    val num = 10
    val message = if (num % 2 == 0) "even" else "odd"
    println("$num is $message")

    // "when" can be used as an alternative to "if-else if" chains.
    val i = 10
    when {
        i < 7 -> println("first when")
        fooString.startsWith("This") -> println("second when")
        else -> println("else when")
    }

    // "when" can be used with an argument.
    when (i) {
        0, 10 -> println("0 or 21")
        in 1..20 -> println("in range 1 to 20")
        else -> println("else")
    }

    // "when" can be used as a function that returns a value.
    var whenResult = when (i) {
        0, 21 -> "0 or 21"
        in 1..20 -> "in range 1 to 20"
        else -> "else"
    }
    println(whenResult)

    /*
    We can check if an object is of a particular type by using the "is" operator.
    If an object passes a type check then it can be used as that type without
    explicitly casting it.
    */
    fun smartCastExample(x: Any): Boolean {
        return when (x) {
            is Boolean -> x
            is Int -> x > 0
            is String -> x.isNotEmpty()
            else -> false
        }
    }
    println(smartCastExample("Hello"))
    println(smartCastExample(""))
    println(smartCastExample(5))
    println(smartCastExample(0))
    println(smartCastExample(true))
    println(smartCastExample(false))

    /*
    Extensions are a way to add new functionality to a class.
    This is similar to C# extension methods.
    */
    fun String.remove(c: Char): String {
        return this.filter { it != c }
    }
    println("Hello, world!".remove('o'))

    //Working with collections
    val people = mutableMapOf<String, Int>("Fred" to 30, "Ann" to 23)
    println(people.map { "${it.key} is ${it.value}" }.joinToString(", "))
    println(people.filter { it.key.length < 30 }.toString())

    //Lambdas
    val triple: (Int) -> Int = { a: Int -> a * 3 }
    println(triple(5))
    val triple2: (Int) -> Int = { it * 3 }

    //Higher-order functions
    //This just means passing a function (in this case a lambda)
    //to another function, or returning a function from another function.
    val peopleNames = listOf("Fred", "Ann", "Barbara", "Joe")
    println(peopleNames.sortedWith { str1: String, str2: String -> str1.length - str2.length })

    val words = listOf("about", "acute", "awesome", "balloon", "best", "brief", "class", "coffee", "creative")
    val filteredWords = words.filter { it.startsWith("b", ignoreCase = true) }
        .shuffled()
        .take(2)
        .sorted()

}

// Enum classes are similar to Java enum types.
enum class EnumExample {
    A, B, C
}

fun printEnum() = println(EnumExample.A)

// Since each enum is an instance of the enum class, they can be initialized as:
enum class EnumExample2(val value: Int) {
    A(value = 5),
    B(value = 4),
    C(value = 3)
}

fun printProperty() = println(EnumExample2.A.value)

// Every enum has properties to obtain its name and ordinal(position) in the enum class declaration:
fun printName() = println(EnumExample2.A.name)
fun printPosition() = println(EnumExample2.A.ordinal)

/*
The "object" keyword can be used to create singleton objects.
We cannot instantiate it but we can refer to its unique instance by its name.
This is similar to Scala singleton objects.
*/

object ObjectExample {
    fun hello(): String {
        return "hello"
    }

    override fun toString(): String {
        return "Hello, it's me, ${ObjectExample::class.simpleName}"
    }
}

fun useSingletonObject() {
    println(ObjectExample.hello())
    // In Kotlin, "Any" is the root of the class hierarchy, just like "Object" is in Java
    val someRef: Any = ObjectExample
    println(someRef)
}

/* The not-null assertion operator (!!) converts any value to a non-null type and
throws an exception if the value is null.
*/
var b: String? = "abc"
val l = b!!.length

data class Counter(var value: Int) {
    // overload Counter += Int
    operator fun plusAssign(increment: Int) {
        this.value += increment
    }

    // overload Counter++ and ++Counter
    operator fun inc() = Counter(value + 1)

    // overload Counter + Counter
    operator fun plus(other: Counter) = Counter(this.value + other.value)

    // overload Counter * Counter
    operator fun times(other: Counter) = Counter(this.value * other.value)

    // overload Counter * Int
    operator fun times(value: Int) = Counter(this.value * value)

    // overload Counter in Counter
    operator fun contains(other: Counter) = other.value == this.value

    // overload Counter[Int] = Int
    operator fun set(index: Int, value: Int) {
        this.value = index + value
    }

    // overload Counter instance invocation
    operator fun invoke() = println("The value of the counter is $value")

}

/* You can also overload operators through an extension methods */
// overload -Counter
operator fun Counter.unaryMinus() = Counter(-this.value)

fun operatorOverloadingDemo() {
    var counter1 = Counter(0)
    var counter2 = Counter(5)
    counter1 += 7
    println(counter1) // => Counter(value=7)
    println(counter1 + counter2) // => Counter(value=12)
    println(counter1 * counter2) // => Counter(value=35)
    println(counter2 * 2) // => Counter(value=10)
    println(counter1 in Counter(5)) // => false
    println(counter1 in Counter(7)) // => true
    counter1[26] = 10
    println(counter1) // => Counter(value=36)
    counter1() // => The value of the counter is 36
    println(-counter2) // => Counter(value=-5)
}

// Изолированные классы
/* Изолированные классы используются для ограниченных иерархий классов,
   когда значение может иметь тип только из ограниченного набора, и никакой другой.
   Они являются, по сути, расширением enum классов: набор значений enum типа также
   ограничен, но каждая enum-константа существует только в единственном экземпляре, в то
   время как наследник изолированного класса может иметь множество экземпляров, которые могут
   нести в себе какое-то состояние.

   Изолированный класс может иметь наследников, но все они должны быть объявлены в том же файле, что
   и сам изолированный класс.
 */

sealed class Expr
data class Const(val number: Double) : Expr()
data class Sum(val e1: Expr, val e2: Expr) : Expr()
object NotANumber : Expr()

fun eval(expr: Expr): Double = when (expr) {
    is Const -> expr.number
    is Sum -> eval(expr.e1) + eval(expr.e2)
    NotANumber -> Double.NaN
}

// Сам по себе изолированный класс является абстрактным, он не может быть создан напрямую и может иметь абстрактные компоненты.
// Ниже пример из лекций по котлину
// Абстрактный класс, у которого может быть какое-то количество наследников
// и они обязаны быть с ним в том же самом файле
// Своя иарархия наследования с классами, но количество вариантов ты
// задаешь сам, больше никто наследоваться не может
sealed class HttpCodeEx(val code: Int, val msg: String) {
    object OK : HttpCodeEx(200, "Ok")

    class BAD_REQUEST(reason: String) : HttpCodeEx(400, reason)

    class FORBIDDEN(reason: String) : HttpCodeEx(400, reason)

    object I_M_A_TEAPOT : HttpCodeEx(418, "Some tea?") {
        fun boilMeSomeWater(): Unit = TODO()
    }

    class INTERNAL_SERVER_ERROR(reason: String) : HttpCodeEx(400, reason)
}

enum class HttpCode(val code: Int) {
    OK(200),
    BAD_REQUEST(400),
    FORBIDDEN(403),
    I_M_A_TEAPOT(418) {
        override fun isOfficial() = false
    },
    INTERNAL_SERVER_ERROR(500);

    open fun isOfficial() = true
}

// Вложенные классы
// Классы могут быть вложены в другие классы

class Outer {
    private val bar: Int = 1

    class Nested {
        fun foo() = 2
    }
}

val demo = Outer.Nested().foo()

// Внутренние классы
// Класс может быть помечен как внутренний с помощью слова inner, тем самым он будет иметь доступ к членам
// внешнего класса. Внутренние классы содержат ссылку на объект внешнего класса
class Outer2 {
    private val bar: Int = 1

    inner class Inner {
        fun foo() = bar
    }
}

val demo2 = Outer2().Inner().foo()

// Перечисляемые типы
// Наиболее базовый пример использования enum - это реализация типобезопасных перечислений
enum class Direction {
    NORTH, SOUTH, WEST, EAST
}
// Каждая enum константа является объектом. При объявлении константы разделяются запятыми.

// Инициализация
// Так как константы являются экземплярами enum-класса, они могут быть инициализированны
enum class Color(val rgb: Int) {
    RED(0xFF0000),
    GREEN(0x00FF00),
    BLUE(0x0000FF)
}

// Реализация интерфейсов в классах enum
/* Класс enum может реализовывать интерефейс (но не наследоваться от класса), предоставляя либо
   единственную реализацию членов интерфейса для всех элементов enum, либо отдельные для каждого
   элемента в своем анонимном классе.
 */

enum class IntArithmetics : BinaryOperator<Int>, IntBinaryOperator {
    PLUS {
        override fun apply(t: Int, u: Int): Int {
            return t + u
        }
    },
    TIMES {
        override fun apply(t: Int, u: Int): Int {
            return t * u
        }
    };

    override fun applyAsInt(t: Int, u: Int): Int {
        return apply(t, u)
    }
}

// Работа с enum-константами
// Enum классы имеют стандартные методы для вывода списка объявленных констант и получения по ее имени.
EnumClass.valueOf(value: String): EnumClass
EnumClass.values(): Array<EnumClass>
// к константам в классе enum можно получить доступ универсальным способом, используя функции enumValues<T>() и enumValueOf<T>()
enum class RGB2 { RED, GREEN, BLUE }

inline fun <reified T : Enum<T>> printAllValues() {
    print(enumValues<T>().joinToString { it.name })
}

printAllValues<RGB> // RED, GREEN, BLUE
// каждая enum-константа имеет поля, в которых содержатся ее имя и порядковый номер в enum классе
val name: String
val ordinal: Int

// Анонимные объекты и объявление объектов
/* Иногда необходимо получить экземпляр некоторого класса с незначительной молификацией,
желательно без написания нового подкласса. Java справляется с этим с помощью вложенных анонимных
классов. Kotlin улучшил этот подход.
 */

// Для того, чтобы создать объект анонимного класса, который наследуется от какого-то типа, используется конструкция:
windows.addMouseListener(object : MouseAdapter() {
    override fun mouseClicked(e: MouseEvent?) {
        //
    }

    override fun mouseEntered(e: MouseEvent?) {
    }
})

/* Если у супертипа есть конструктор, то в него должны быть переданы соответсвующие параметры.
   Множество супертипов может быть указано после двоеточия в виде списка, заполненного через запятую:
 */

open class A(x: Int) {
    open val y: Int = x
}

interface B {}

val ab: A = object : A(1), B {
    override val y = 15
}

// Если нужен просто объект без всяких родительских классов, то:
val adHoc = object {
    var x: Int = 0
    var y: Int = 0
}

// Код внутри объявленного объекта может обращаться к переменны за скобками

// Объявление объектов
// Синглтон очень полезный паттер программирования, и Kotlin позволяет объявлять его довольно простым способом:

object DataProviderManager {
    fun registerDataProvider(provider: DataProvider) {

    }

    val allDataProviders: Collection<DataProvider>
        get() = TODO()
}

/* Это называется объявлением объекта и всегда имеет приставку в виде ключевого слова object.
   Аналогично  объявлению переменной, объявление объекта не является выражением и не может быть использовано
   в правой части оператора присваивания.

   Для ссылки на объект используется его имя.

   Подобные объекты могут иметь супертивы: */
object DefaultListener : MouseAdapter() {
    override fun mouseClicked(e: MouseEvent?) {

    }

    override fun mouseEntered(e: MouseEvent?) {

    }
}

// Вспомогательные объекты
// Объявление объекта внутри класса может быть отмечено ключевым словом companion
class MyClass {
    companion object Factory {
        fun create(): MyClass = MyClass()
    }
}

// Для вызова членов такого companion объекта используется имя класса:
val instance = MyClass.create()

// Не обязательно указывать имя вспомогательного объекта. В таком случае он будет назван Companion:
class MyClass2 {
    companion object {
    }
}

val x = MyClass2.Companion

/* Такие члены вспомогательных объектов выглядят, как статические члены в других языкак, но на самом
   деле они являются членами реальных объектов и могут реализовывать, к примеру, интерфейсы:
 */

interface Factory<T> {
    fun create(): T
}

class MyClass3 {
    companion object : Factory<MyClass3> {
        override fun create(): MyClass3 {
            return MyClass3()
        }
    }
}

/* Семантическое различие между анонимным и деклариуемым объектом.
   Существует только одно смысловое различие между этими понятиями:
     - анонимный объект инициализируется непосредственно при использовании
     - декларированный объект инициализируется лениво, в момент первого к нему доступа
     - вспомогательный объект инициализируется в момент, когда класс, к которому он относится,
       загружен и семантически совпадает со статическим инициализатором Java.
 */

// СНОВА ПРО ДЕЛЕГАТЫ (ЕЩЕ ИНФОРМАЦИЯ ЕСТЬ В ФАЙЛЕ lection6_delegates)
// Делегирование
// Делегирование класса

/* Шаблон делегирования является хорошей альтернативой наследованию, и Kotlin поддерживает
   его нативно, освобождая вас от необходимости написания шаблонного кода. */

interface Base {
    fun print()
}

class BaseImpl(val x: Int) : Base {
    override fun print() {
        print(x)
    }
}

class Derived(b: Base) : Base by b

fun main() {
    val b = BaseImpl(10)
    Derived(b).print()
}
/* Ключевое слово by в оглавлении Derived, находящееся после типа делегируемого класса, гвоорит о том,
   что объект b типа Base будет храниться внутри экземпляра Derived, и компилятор сгенерирует у Derived
   соответсвующие методы из Base, которые при вызове будут переданы объекту b */

// Делегированные свойства
/* Существует несколько основных видов свойств, которые мы реализуем каждый раз вручную в случае их надобности.
   Однако намного удобнее было бы реализовать их один раз и полодить в библиотеку. Примеры:
      - ленивые свойства (lazy property): значение вычисляется один раз, при первом обращении
      - свойства, на события об изменении которых можно подписаться (observable properties)
      - свойства, хранимые в ассоциативном списке, а не отдельных полях */
// Пример делегированных свойств
class Example {
    var p: String by Delegate()
}
// делагат не обязан реализовывать какой-то интерфейс, достаточно, чтобы у него были методы getValue(), setValue() с определенной сигнатурой
class Delegate {
    operator fun getValue(thisRef: Any?, property: KProperty<*>): String {
        return "$thisRef, спасибо за делегирование мне '${property.name}"
    }
    operator fun setValue(thisRef: Any?, property: KProperty<*>, value: String) {
        print("$value было присвоено значение '${property.name}' в $thisRef")
    }
}

// Стандартные делегаты
// ленивые свойства

/* Функция lazy() принимает лямбду и возвращает экземпляр класса Lazy<T>, который служит делегатом для
   реализации ленивого свойства.
   При первом вызове get() запускается лямбда выражение (переданное в lazy() в качестве аргумента),
   затем значение запоминается и при следующих вызовах просто возвращается. */

val lazyValue: String by lazy {
    println("первое обращение")
    "Hello"
}

fun main() {
    println(lazyValue)
    println(lazyValue)
}
// вывод
// первое обращение
// Hello
// Hello

// observable свойства
/* Delegates.observable() принимает два аргумента: начальное и обработчик (лямбла), вызывающийся при изменении
   свойства. У обработчика три параметра (описание свойства, старое значение, новое значение) */

class User {
    var name: String by Delegates.observable("no name") {
        prop, old, new ->
        println("$old -> $new")
    }
}
fun main() {
    val user = User()
    user.name = "first"
    user.name = "second"
}
// вывод
// no name -> first
// no name -> second

// для запрета присваивания необходимо использовать функцию vetoable()

// хранение свойств в ассоциативном массиве
// это полезно в динамическом коде, например, при работе с JSON
class User2(val map: Map<String, Any?>) {
    val name: String by map
    val age: Int     by map
}

// Конструктор принимает ассоциативный список
val user2 = User2(mapOf(
    "name" to "Peter",
    "age" to 22
))
// делегированные свойства берут значения из этого ассоциативного списка (по строковым ключам)
println(user2.name)
println(user2.age)

// Локальные делегированные свойства
// можно объявить локальную переменную как делегированное свойство
// например можно сделать локальную переменную ленивой

fun example(computeFoo: () -> String) {
    val memoizedFoo by lazy(computeFoo)

    if (someCondition && memoizedFoo.isValid()) {
        memoizedFoo.doSomething()
    }
}
// локальная переменная будет вычеслена при первом обращении к ней
// если условие someCondition будет ложно, то значени вообще не будет вычислено