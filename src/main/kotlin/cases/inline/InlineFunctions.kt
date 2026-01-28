@file:Suppress("unused")

package cases.inline

import cases.core.helperTopLevel

// 内联函数
inline fun <T> inlineFunction(block: () -> T): T {
    helperTopLevel(1)
    return block()
}

// 非内联 lambda 参数
inline fun <T> inlineWithNoinline(
    inlineBlock: () -> T,
    noinline noinlineBlock: () -> Unit
) {
    helperTopLevel(2)
    inlineBlock()
    noinlineBlock()
}

// 跨内联
inline fun <T> crossInline(crossinline block: () -> T) {
    helperTopLevel(3)
    val runnable = object : Runnable {
        override fun run() {
            block() // 跨内联调用
        }
    }
    runnable.run()
}

// 内联扩展函数
inline fun <T> T.inlineApply(block: T.() -> Unit): T {
    helperTopLevel(4)
    block()
    return this
}

// 内联泛型函数
inline fun <reified T> reifiedType(): String {
    helperTopLevel(5)
    return T::class.simpleName ?: "unknown"
}

fun useInline() {
    inlineFunction {
        helperTopLevel(6)
        "result"
    }
    
    inlineWithNoinline(
        { helperTopLevel(7) },
        { helperTopLevel(8) }
    )
    
    crossInline {
        helperTopLevel(9)
    }
    
    "test".inlineApply {
        helperTopLevel(10)
    }
    
    val typeName = reifiedType<String>()
}
