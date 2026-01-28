@file:Suppress("unused")

package cases.fp

import cases.core.helperTopLevel

// 扩展函数 + 高阶函数
inline fun <T, R> T.pipe(block: (T) -> R): R = block(this)

fun functionTypeValues(): Int {
    val f: (Int) -> Int = { it + 1 }
    val g: (Int) -> Int = ::helperTopLevel // 函数引用 CALLS
    return f(1) + g(2)
}

fun lambdaWithReceiver(): String {
    val build: StringBuilder.() -> Unit = {
        append("a")
        append("b")
    }
    val sb = StringBuilder().apply(build) // lambda receiver CALLS
    return sb.toString()
}

fun localFunctionAndRecursion(n: Int): Int {
    fun fact(x: Int): Int = if (x <= 1) 1 else x * fact(x - 1) // local recursion
    return fact(n)
}

fun inlineHigherOrderCalls(): Int {
    return 10.pipe { it * 2 }.pipe { helperTopLevel(it) }
}

class Invokable(private val base: Int) {
    operator fun invoke(x: Int): Int = base + x
}

fun invokeOperatorCalls(): Int {
    val inv = Invokable(5)
    return inv(3) // 调用 invoke
}

infix fun Int.timesPlus(other: Int): Int = (this * other).pipe { it + 1 }

fun infixCall(): Int = 3 timesPlus 4

