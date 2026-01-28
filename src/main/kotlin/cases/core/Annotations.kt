@file:JvmName("CoreAnnotations")
@file:Suppress("unused", "UNUSED_PARAMETER")

package cases.core

import kotlin.jvm.JvmField
import kotlin.jvm.JvmInline
import kotlin.jvm.JvmName
import kotlin.jvm.JvmOverloads
import kotlin.jvm.JvmStatic

@Target(
    AnnotationTarget.CLASS,
    AnnotationTarget.FUNCTION,
    AnnotationTarget.PROPERTY,
    AnnotationTarget.VALUE_PARAMETER,
    AnnotationTarget.CONSTRUCTOR,
    AnnotationTarget.FILE
)
@Retention(AnnotationRetention.RUNTIME)
annotation class Marker(val tag: String = "default")

@Target(AnnotationTarget.FUNCTION)
@Retention(AnnotationRetention.BINARY)
annotation class Trace

@Marker("annotated-class")
class AnnotatedClass @JvmOverloads constructor(
    @Marker("param") val name: String,
    @Marker("param") val count: Int = 0
) {
    @JvmField
    val fieldLike: String = "FIELD"

    @Marker("property")
    var mutable: Int = 0
        get() = field.also { tracedGetterCall(it) }
        set(value) {
            tracedSetterCall(value)
            field = value
        }

    @Trace
    fun instanceCall(x: Int): Int = helperTopLevel(x) + tracedMember(x)

    private fun tracedMember(x: Int): Int = x * 2
}

@JvmInline
value class UserId(val raw: String) {
    fun normalized(): String = raw.trim().lowercase()
}

object CoreSingleton {
    @JvmStatic
    fun staticLikeCall(): String = "ok"
}

@Trace
fun helperTopLevel(x: Int): Int = x + 1

private fun tracedGetterCall(v: Int) {
    // 供 CALLS 关系：属性 getter -> 顶层函数
    helperTopLevel(v)
}

private fun tracedSetterCall(v: Int) {
    // 供 CALLS 关系：属性 setter -> 对象方法
    CoreSingleton.staticLikeCall()
    helperTopLevel(v)
}

