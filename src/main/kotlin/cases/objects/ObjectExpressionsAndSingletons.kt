@file:Suppress("unused")

package cases.objects

import cases.core.helperTopLevel
import cases.types.ResultBox
import cases.types.ok

object Singleton {
    fun call(): Int = helperTopLevel(1)
    
    fun chainCall(): ResultBox<Int> = call().ok()
}

class ObjectExpressionExample {
    fun useAnonymousObject(): Int {
        val obj = object {
            fun innerCall(): Int = helperTopLevel(2)
        }
        return obj.innerCall() // 对象表达式方法调用
    }
    
    fun useInterfaceImplementation(): String {
        val listener = object : Runnable {
            override fun run() {
                helperTopLevel(3) // 接口实现中的调用
            }
        }
        listener.run()
        return "done"
    }
}

class CompanionWithCall {
    companion object Factory {
        fun create(): CompanionWithCall {
            helperTopLevel(4)
            return CompanionWithCall()
        }
    }
    
    fun useCompanion(): Int {
        return CompanionWithCall.create().hashCode() // 调用伴生对象方法
    }
}

fun singletonCalls(): Int {
    return Singleton.call() + Singleton.chainCall().value
}
