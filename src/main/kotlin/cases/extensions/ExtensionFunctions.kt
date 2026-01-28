@file:Suppress("unused")

package cases.extensions

import cases.core.helperTopLevel
import cases.types.ResultBox
import cases.types.ok

// 扩展函数
fun String.reverse(): String {
    helperTopLevel(1)
    return this.reversed()
}

// 扩展属性
val String.firstChar: Char
    get() {
        helperTopLevel(2)
        return this.firstOrNull() ?: ' '
    }

// 泛型扩展函数
fun <T> List<T>.second(): T {
    helperTopLevel(3)
    return this[1]
}

// 扩展函数链式调用
fun String.process(): ResultBox<String> {
    return this
        .uppercase()
        .reverse()
        .ok()
}

// 扩展函数作为参数
fun <T> T.applyExtension(block: T.() -> Unit): T {
    helperTopLevel(4)
    block()
    return this
}

fun useExtension() {
    "test".applyExtension {
        helperTopLevel(5) // receiver 中的调用
    }
}

// 成员扩展函数
class ExtensionHost {
    fun String.memberExtension(): String {
        helperTopLevel(6)
        return this.uppercase()
    }
    
    fun useMemberExtension() {
        "test".memberExtension() // 成员扩展调用
    }
}

// 扩展函数重载
fun Int.plus(other: Int): Int {
    helperTopLevel(7)
    return this + other
}

fun useExtensions() {
    val reversed = "hello".reverse()
    val first = "test".firstChar
    val second = listOf(1, 2, 3).second()
    val processed = "test".process()
    
    val host = ExtensionHost()
    host.useMemberExtension()
    
    val sum = 5.plus(3)
}
