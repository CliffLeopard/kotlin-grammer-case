@file:Suppress("unused")

package cases.calls

/**
 * 跨文件调用链 - 最底层文件 C。
 * 被 CrossFileB.kt 中的函数/类调用，形成跨文件 CALLS 关系。
 */
fun valueInC(): Int = 100

fun formatInC(s: String): String = "C:$s"

class CalleeC {
    fun compute(): Int = valueInC()
    fun format(msg: String): String = formatInC(msg)
}
