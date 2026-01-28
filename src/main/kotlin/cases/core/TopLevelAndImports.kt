@file:Suppress("unused")

package cases.core

import cases.fp.pipe
import cases.types.ResultBox
import cases.types.ok
import kotlin.math.abs as kAbs

const val CONST_INT: Int = 42
val topLevelVal: String = "value"

typealias StringMap = Map<String, String>

fun topLevelFun(a: Int, b: Int = 1): Int = a + b

fun defaultAndNamedArgs(): Int {
    val x = topLevelFun(a = 1, b = 2)
    val y = topLevelFun(a = 5) // 默认参数
    return x + y
}

fun varargCall(vararg xs: Int): Int = xs.sum()

fun callWithSpread(list: IntArray): Int = varargCall(*list)

fun aliasImportCall(n: Int): Int = kAbs(n)

fun crossPackageCallChain(input: String): ResultBox<String> {
    // CALLS：扩展函数、顶层函数、泛型工厂
    return input
        .pipe { it.trim() }
        .pipe { it.uppercase() }
        .ok()
}

