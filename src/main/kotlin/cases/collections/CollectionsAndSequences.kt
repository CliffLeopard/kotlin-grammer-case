@file:Suppress("unused")

package cases.collections

import cases.core.helperTopLevel
import cases.fp.pipe

fun listOperations() {
    val list = listOf(1, 2, 3)
    list.map { helperTopLevel(it) } // map 回调调用
    list.filter { it > 1 }.forEach { helperTopLevel(it) } // filter + forEach
    list.reduce { acc, n -> acc + helperTopLevel(n) } // reduce 回调
}

fun sequenceOperations() {
    val seq = sequenceOf(1, 2, 3)
        .map { helperTopLevel(it) } // sequence map
        .filter { it > 1 }
        .toList()
}

fun mapOperations() {
    val map = mapOf("a" to 1, "b" to 2)
    map.forEach { (k, v) ->
        helperTopLevel(v) // map forEach
    }
    map.mapValues { (_, v) -> helperTopLevel(v) } // mapValues
}

fun collectionBuilders() {
    val list = buildList {
        add(1)
        add(helperTopLevel(2)) // builder 中调用
    }
    
    val map = buildMap {
        put("a", helperTopLevel(1))
    }
}

fun collectionExtensions() {
    val list = listOf(1, 2, 3)
    list.firstOrNull()?.let { helperTopLevel(it) }
    list.lastOrNull()?.let { helperTopLevel(it) }
    list.singleOrNull()?.let { helperTopLevel(it) }
}

fun collectionChain() {
    listOf(1, 2, 3, 4, 5)
        .filter { it > 2 }
        .map { it * 2 }
        .pipe { helperTopLevel(it.sum()) } // 扩展函数链式调用
        .let { it.toString() }
}
