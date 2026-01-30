@file:Suppress("unused")

package cases.interop

import cases.core.helperTopLevel

/**
 * Kotlin 与 Java 混编互调示例。
 * - Kotlin 调用 Java：JavaUtils.getVersion()、JavaUtils.add()、JavaService 各方法。
 * - Java 调用 Kotlin：JavaService 内部调用 helperTopLevel、CoreSingleton、TypeSystemKt.ok。
 */
fun kotlinCallsJava(): String {
    val version = JavaUtils.getVersion()           // Kotlin 调用 Java 静态方法
    val sum = JavaUtils.add(1, 2)                  // Kotlin 调用 Java 静态方法
    val formatted = JavaUtils.format("test")       // Kotlin 调用 Java 静态方法
    return "$version $sum $formatted"
}

fun kotlinCallsJavaService(): Int {
    val service = JavaService()
    val a = service.useKotlinHelper(10)            // Kotlin 调用 Java，Java 内部再调 Kotlin
    val s = service.useKotlinSingleton()
    val box = service.useKotlinResult("ok")
    val chained = service.chainKotlinAndJava(5, 3) // Java 内链式调用 Kotlin + Java
    return a + s.length + box.value.length + chained
}

fun mixedChain(): Int {
    val x = helperTopLevel(1)                     // Kotlin 顶层函数
    val y = JavaUtils.add(x, 2)                    // 再调 Java
    return JavaService().useKotlinHelper(y)         // Java 再调 Kotlin
}
