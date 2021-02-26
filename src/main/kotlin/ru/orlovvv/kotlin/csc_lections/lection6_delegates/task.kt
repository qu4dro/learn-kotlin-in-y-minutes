package ru.orlovvv.kotlin.csc_lections.lection6_delegates

import java.net.http.HttpRequest
import java.net.http.HttpResponse
import kotlin.properties.Delegates
import kotlin.reflect.KProperty

// Делегирование
// альтернатива наследованию
// частный случай композиции
// вместо реализации методов вы делегируете их реализацию другому объекту


// все неперегруженные вызовы будут делегироваться полю innerSet
// перегруженные вызовы будут работать в соответствии с реализацией
class BetterFlexibleSet<T>(private val threshold: Int = 4, private val innerSet: MutableSet<T> = COWArraySet<T>()) :
    MutableSet<T> by innerSet {
    // ...
}

// А если я хочу отделегировать, но не всю реализацию интерефейса, а аодин метод? Тогда проще взять и реализовать
// А если не метод, а свойство?

class Config(val data: MutableMap<String, Any?>) {
    var Url: String
        get() = data["Url"] as String
        set(value) {
            data["Url"] = value
        }
}

// ---->
class BetterConfig(val data: MutableMap<String, Any?>) {
    val Url: String by data
}

// Стандартные делегаты
// делегат lazy { } (ленивая инициализация)
// (на первом доставании значения сделает то чт сказано, а потом просто будет повторно использовать это значение)
class BetterConfig2(val data: MutableMap<String, Any?>) {
    val secretKey: String by lazy { loadSecretKeyFromSecretStorage() }
}

// observable(defaultValue) { prop, old, new -> ... }
// он будет вызывать то, что описано в лямбде. после того как значение поменялось, передавать само свойство,
// передавать старое значение и новое значение
class BetterConfig3(val data: MutableMap<String, Any?>) {
    var ThreadCount: Int
            by Delegates.observable(data["ThreadCount"] as Int) { _, old, new ->
                println("Changind thread count from $old to $new")
            }
}

// vetoable(default) { prop, old, new -> ... }
// возвращает boolean, в случае true вы разрешаете модификацию, иначе остается старое значение
class BetterConfig4(val data: MutableMap<String, Any?>) {
    var ThreadCount: Int
            by Delegates.vetoable(data["ThreadCount"] as Int) { _, old, new -> new <= 24 }
}

// делегаты для локальных переменных
fun hadleHttpRequest(req: HttpRequest): HttpResponse {
    val userId by req
    val userData by req
}

operator fun HttpRequest.getValue(thisRef: Nothing?, prop: KProperty<*>): String? = this.getParam(prop.name)