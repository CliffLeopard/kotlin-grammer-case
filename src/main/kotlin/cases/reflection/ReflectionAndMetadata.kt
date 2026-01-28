@file:Suppress("unused")

package cases.reflection

import cases.core.helperTopLevel
import kotlin.reflect.KClass
import kotlin.reflect.KFunction

class ReflectiveClass {
    fun method(x: Int): Int {
        helperTopLevel(x)
        return x * 2
    }
    
    companion object {
        fun staticMethod(): String {
            helperTopLevel(1)
            return "static"
        }
    }
}

fun reflectionCalls() {
    val clazz: KClass<ReflectiveClass> = ReflectiveClass::class
    val instance = ReflectiveClass() // 直接构造，不使用反射创建实例
    
    val method: KFunction<*>? = clazz.members.find { it.name == "method" } as? KFunction<*>
    // 注意：KFunction.call 需要 kotlin-reflect 依赖，这里仅演示语法结构
    // method?.call(instance, 5) // 反射调用
    
    val staticMethod = ReflectiveClass::staticMethod
    staticMethod() // 函数引用调用
}

fun classReference() {
    val clazz = String::class
    helperTopLevel(clazz.simpleName?.length ?: 0)
}

fun propertyReference() {
    val prop = String::length
    val len = prop.get("test") // 属性引用调用
    helperTopLevel(len)
}
