@file:Suppress("unused")

package cases.classes

import cases.core.helperTopLevel

class PrimaryConstructor(val id: String, val value: Int) {
    init {
        // init 块中的调用
        helperTopLevel(value)
    }
    
    constructor(id: String) : this(id, 0) {
        // 次构造函数调用主构造函数
        helperTopLevel(1)
    }
}

class SecondaryConstructors {
    val items = mutableListOf<String>()
    
    init {
        items.add("init")
    }
    
    constructor(item: String) {
        items.add(item)
        helperTopLevel(items.size)
    }
    
    constructor(item1: String, item2: String) {
        items.add(item1)
        items.add(item2)
        helperTopLevel(items.size)
    }
}

class CompanionObjectExample {
    companion object {
        const val CONST = "companion"
        
        fun create(): CompanionObjectExample {
            return CompanionObjectExample().also { helperTopLevel(1) }
        }
        
        fun staticCall(): Int = helperTopLevel(2)
    }
    
    fun instanceCall(): Int = CompanionObjectExample.staticCall() // 调用伴生对象方法
}

fun companionObjectCalls(): Int {
    val obj = CompanionObjectExample.create()
    return CompanionObjectExample.staticCall() + obj.instanceCall()
}
