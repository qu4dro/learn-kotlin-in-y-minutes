package ru.orlovvv.kotlin.csc_lections.lection3_class_part2

open class A {
    open fun foo(a: Int = 1, b: Int = 2): String = "$a$b"
}

class B : A() {
    override fun foo(b: Int, a: Int): String {
        return super.foo(a, b)
    }
}

fun main() {
    val a: A = B()
    println(a.foo())
}