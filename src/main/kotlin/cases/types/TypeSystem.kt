@file:Suppress("unused", "MemberVisibilityCanBePrivate")

package cases.types

import cases.core.Marker

sealed interface Outcome<out T> {
    data class Ok<T>(val value: T) : Outcome<T>
    data class Err(val message: String, val cause: Throwable? = null) : Outcome<Nothing>
}

sealed class Expr {
    data class Lit(val n: Int) : Expr()
    data class Add(val l: Expr, val r: Expr) : Expr()
}

fun eval(e: Expr): Int = when (e) {
    is Expr.Lit -> e.n
    is Expr.Add -> eval(e.l) + eval(e.r) // 递归 CALLS
}

enum class Color(val rgb: Int) {
    RED(0xFF0000),
    GREEN(0x00FF00),
    BLUE(0x0000FF);

    fun isWarm(): Boolean = this == RED
}

interface Repo<T> {
    fun get(id: String): T?
    fun put(id: String, value: T)
}

open class BaseRepo<T> : Repo<T> {
    private val store = mutableMapOf<String, T>()
    override fun get(id: String): T? = store[id]
    override fun put(id: String, value: T) {
        store[id] = value
    }
}

class UserRepo : BaseRepo<User>()

data class User(val id: String, val name: String)

@Marker("generic-box")
data class ResultBox<T>(val value: T)

fun <T> T.ok(): ResultBox<T> = ResultBox(this)

fun <T> requireNotNullCall(v: T?): T {
    // CALLS：标准库 + Elvis
    return requireNotNull(v) { "value is null" }
}

inline fun <reified T> castOrNull(x: Any): T? = x as? T

fun typeChecksAndSmartCasts(x: Any): String = when (x) {
    is String -> x.uppercase() // smart cast
    is Int -> (x + 1).toString()
    else -> x.toString()
}

