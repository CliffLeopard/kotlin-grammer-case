@file:Suppress("unused")

package cases.calls

/**
 * 跨文件调用链 - 中间层文件 B。
 * 调用 CrossFileC.kt 中的 valueInC、formatInC、CalleeC；
 * 被 CrossFileA.kt 中的函数/类调用。
 */
fun computeInB(x: Int): Int {
    val base = valueInC()           // 跨文件：调用 CrossFileC.kt 顶层函数
    return base + x
}

fun formatInB(s: String): String {
    return formatInC(s)             // 跨文件：调用 CrossFileC.kt 顶层函数
}

class CalleeB(private val calleeC: CalleeC = CalleeC()) {
    fun compute(x: Int): Int {
        return calleeC.compute() + x // 跨文件：调用 CrossFileC.kt 中的 CalleeC
    }
    fun format(msg: String): String = calleeC.format(msg)
}

fun createCalleeB(): CalleeB = CalleeB()
