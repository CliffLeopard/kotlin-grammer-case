@file:Suppress("unused")

package cases.calls

/**
 * 跨文件调用链 - 入口文件 A。
 * 调用 CrossFileB.kt 中的 computeInB、formatInB、CalleeB、createCalleeB；
 * 形成 A -> B -> C 的跨文件 CALLS 链。
 */
fun callFromA(x: Int): Int {
    return computeInB(x)            // 跨文件：调用 CrossFileB.kt 顶层函数
}

fun formatFromA(s: String): String {
    return formatInB(s)             // 跨文件：调用 CrossFileB.kt 顶层函数
}

class CallerA(private val calleeB: CalleeB = createCalleeB()) {
    fun run(x: Int): Int {
        return calleeB.compute(x)   // 跨文件：调用 CrossFileB.kt 中的 CalleeB
    }
    fun runFormat(msg: String): String = calleeB.format(msg)
}

fun runCrossFileChain(): Int {
    val a = CallerA()
    return a.run(1) + callFromA(2)  // 跨类 + 跨文件调用
}
