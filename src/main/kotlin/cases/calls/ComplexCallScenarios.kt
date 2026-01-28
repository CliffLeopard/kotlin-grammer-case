@file:Suppress("unused")

package cases.calls

import cases.core.helperTopLevel
import cases.core.CoreSingleton
import cases.classes.Animal
import cases.classes.Dog
import cases.fp.pipe
import cases.types.ResultBox
import cases.types.ok

// 递归调用
fun factorial(n: Int): Int {
    return if (n <= 1) 1 else n * factorial(n - 1)
}

// 尾递归
tailrec fun tailRecFactorial(n: Int, acc: Int = 1): Int {
    return if (n <= 1) acc else tailRecFactorial(n - 1, n * acc)
}

// 相互递归
fun isEven(n: Int): Boolean = if (n == 0) true else isOdd(n - 1)
fun isOdd(n: Int): Boolean = if (n == 0) false else isEven(n - 1)

// 高阶函数调用
fun higherOrderCall(f: (Int) -> Int): Int {
    return f(helperTopLevel(1))
}

// 函数引用作为参数
fun functionReferenceCall() {
    higherOrderCall(::helperTopLevel) // 函数引用调用
}

// SAM 转换调用
fun samCall(runnable: Runnable) {
    runnable.run()
}

fun useSamCall() {
    samCall { helperTopLevel(2) } // SAM 转换
}

// 多级链式调用
fun chainCall(): ResultBox<Int> {
    return 10
        .pipe { helperTopLevel(it) }
        .pipe { it * 2 }
        .ok()
}

// 条件调用
fun conditionalCall(flag: Boolean): Int {
    return if (flag) {
        helperTopLevel(3)
    } else {
        CoreSingleton.staticLikeCall().length
    }
}

// 循环中的调用
fun loopCalls() {
    for (i in 1..5) {
        helperTopLevel(i)
    }
    
    while (true) {
        if (helperTopLevel(1) > 0) break
    }
    
    do {
        helperTopLevel(2)
    } while (false)
}

// 异常处理中的调用
fun exceptionCall() {
    try {
        helperTopLevel(1)
    } catch (e: Exception) {
        helperTopLevel(2) // catch 中的调用
    } finally {
        helperTopLevel(3) // finally 中的调用
    }
}

// 多态调用
fun polymorphicCall(animal: Animal): String {
    return when (animal) {
        is Dog -> animal.bark() // 具体类型方法调用
        else -> animal.makeSound()
    }
}

// 泛型方法调用
fun <T> genericCall(value: T, transform: (T) -> String): String {
    return transform(value)
}

fun useGenericCall() {
    genericCall(42) { helperTopLevel(it).toString() }
}

// 扩展函数调用链
fun extensionChain(): String {
    return "test"
        .let { it.uppercase() }
        .pipe { helperTopLevel(it.length) }
        .toString()
}

// 属性访问器调用
class PropertyAccess {
    var value: Int = 0
        get() {
            helperTopLevel(field) // getter 中调用
            return field
        }
        set(v) {
            helperTopLevel(v) // setter 中调用
            field = v
        }
}

fun propertyAccessCall() {
    val obj = PropertyAccess()
    obj.value = 10 // 触发 setter
    val x = obj.value // 触发 getter
}
