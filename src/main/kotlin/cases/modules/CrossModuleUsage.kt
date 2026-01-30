@file:Suppress("unused")

package cases.modules

import shared.SharedService
import shared.sharedCompute
import shared.sharedGreet

/**
 * 跨模块调用示例：根模块调用子模块 shared 的 API。
 * 形成 DEFINES/IMPORTS/EXPORTS 与跨模块 CALLS 关系。
 */
fun callSharedModule(): String {
    val msg = sharedGreet("Kotlin")       // 跨模块：调用 shared 顶层函数
    val n = sharedCompute(21)             // 跨模块：调用 shared 顶层函数
    val svc = SharedService()
    val out = svc.process("World")        // 跨模块：调用 shared 中的类方法
    val m = svc.compute(10)
    return "$msg $n $out $m"
}

fun runCrossModule(): Int {
    val svc = SharedService()
    return svc.compute(5) + sharedCompute(3)
}
