@file:Suppress("unused")

package shared

/**
 * 子模块 shared 对外 API。
 * 被根模块通过 implementation(project(":shared")) 依赖并调用，形成跨模块 CALLS。
 */
fun sharedGreet(name: String): String = "Hello from shared, $name!"

fun sharedCompute(x: Int): Int = x * 2

class SharedService {
    fun process(value: String): String = sharedGreet(value)
    fun compute(n: Int): Int = sharedCompute(n)
}
