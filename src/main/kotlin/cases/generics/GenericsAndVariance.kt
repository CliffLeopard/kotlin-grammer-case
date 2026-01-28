@file:Suppress("unused")

package cases.generics

import cases.core.helperTopLevel

// 泛型类
class Box<T>(private val value: T) {
    fun getValue(): T {
        helperTopLevel(1)
        return value
    }
}

// 泛型函数
fun <T> genericFunction(item: T): T {
    helperTopLevel(2)
    return item
}

// 泛型约束
fun <T : Comparable<T>> maxOf(a: T, b: T): T {
    helperTopLevel(3)
    return if (a > b) a else b
}

// 多个泛型参数
class Pair<A, B>(val first: A, val second: B) {
    fun swap(): Pair<B, A> {
        helperTopLevel(4)
        return Pair(second, first)
    }
}

// 协变
interface Producer<out T> {
    fun produce(): T
}

class StringProducer : Producer<String> {
    override fun produce(): String {
        helperTopLevel(5)
        return "produced"
    }
}

// 逆变
interface Consumer<in T> {
    fun consume(item: T)
}

class AnyConsumer : Consumer<Any> {
    override fun consume(item: Any) {
        helperTopLevel(6)
    }
}

// 星投影
fun processBox(box: Box<*>) {
    helperTopLevel(7)
    val value = box.getValue()
}

// 泛型扩展函数
fun <T> List<T>.secondOrNull(): T? {
    helperTopLevel(8)
    return if (size >= 2) this[1] else null
}

// 泛型委托
class GenericDelegate<T>(private val getter: () -> T) {
    operator fun getValue(thisRef: Any?, property: Any?): T {
        helperTopLevel(9)
        return getter()
    }
}

fun useGenerics() {
    val box = Box(42)
    box.getValue()
    
    genericFunction("test")
    maxOf(1, 2)
    
    val pair = Pair("a", 1)
    pair.swap()
    
    val producer: Producer<String> = StringProducer()
    producer.produce()
    
    val consumer: Consumer<String> = AnyConsumer()
    consumer.consume("test")
    
    processBox(Box(42))
    
    listOf(1, 2, 3).secondOrNull()
}
