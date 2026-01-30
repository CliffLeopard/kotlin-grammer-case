@file:Suppress("unused")

package cases.calls

import cases.core.helperTopLevel

/**
 * 跨类调用链示例：ClassA -> ClassB -> ClassC。
 * 用于验证解析器对跨类 CALLS 关系的识别。
 */

class ChainC {
    fun value(): Int = 1
    fun compute(x: Int): Int = helperTopLevel(x) + value()
}

class ChainB(private val c: ChainC = ChainC()) {
    fun value(): Int = c.value()           // 跨类：调用 ChainC
    fun compute(x: Int): Int = c.compute(x) + 2  // 跨类：调用 ChainC
}

class ChainA(private val b: ChainB = ChainB()) {
    fun value(): Int = b.value()           // 跨类：调用 ChainB
    fun compute(x: Int): Int = b.compute(x) + 3  // 跨类：调用 ChainB
}

fun runCrossClassChain(): Int {
    val a = ChainA()
    return a.compute(10)  // ChainA.compute -> ChainB.compute -> ChainC.compute -> helperTopLevel
}
