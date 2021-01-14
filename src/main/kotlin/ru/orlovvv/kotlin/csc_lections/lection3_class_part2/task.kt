package ru.orlovvv.kotlin.csc_lections.lection3_class_part2

data class MyPOKO(val value: String)

interface Jsonable {
    fun toJson(): String = TODO()
}

data class SignUpMsg(val denizenId: String, val password: String, val email: String?) : Jsonable

data class LoginMsg(val denizenId: String, val password: String) : Jsonable

data class InfoMsg(val denizenId: String) : Jsonable

//destructive declaration
fun processSignUpMsg(msg: SignUpMsg) {
    val (id, pwd, email) = msg
}

fun fixSignUpMsg(msg: SignUpMsg): SignUpMsg {
    val (_, password) = msg
    val pwdHash = computePwdHash(password)
    return SignUpMsg(msg.denizenId, pwdHash, msg.email)
}

fun fixSignUpMsgCopy(msg: SignUpMsg): SignUpMsg = msg.copy(
    password = computePwdHash(msg.password)
)


fun computePwdHash(password: String): String {
    TODO("Not yet implemented")
}

enum class Sex {
    Male,
    Female,
    NonBinary
}

enum class MaritalStatus {
    Married,
    NotMarried,
    Unknown
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

// АБстрактный класс, у которого может быть какое-то количество наследников
// и они обязаны быть с ним в том же самом файле
// Своя иарархия наследования с классами, но количество вариантов ты
// задаешь сам, больше никто наследоваться не может
sealed class HttpCodeEx(val code: Int, val msg: String) {
    object OK: HttpCodeEx(200, "Ok")

    class BAD_REQUEST(reason: String): HttpCodeEx(400, reason)

    class FORBIDDEN(reason: String): HttpCodeEx(400, reason)

    object I_M_A_TEAPOT: HttpCodeEx(418, "Some tea?") {
        fun boilMeSomeWater(): Unit = TODO()
    }
    class INTERNAL_SERVER_ERROR(reason: String): HttpCodeEx(400, reason)
}