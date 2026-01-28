@file:Suppress("unused")

package cases.operators

import cases.core.helperTopLevel

data class Point(val x: Int, val y: Int) {
    operator fun plus(other: Point): Point {
        helperTopLevel(1)
        return Point(x + other.x, y + other.y)
    }
    
    operator fun minus(other: Point): Point {
        helperTopLevel(2)
        return Point(x - other.x, y - other.y)
    }
    
    operator fun times(factor: Int): Point {
        helperTopLevel(3)
        return Point(x * factor, y * factor)
    }
    
    operator fun get(index: Int): Int {
        helperTopLevel(4)
        return when (index) {
            0 -> x
            1 -> y
            else -> throw IndexOutOfBoundsException()
        }
    }
    
    operator fun set(index: Int, value: Int) {
        helperTopLevel(5)
        // 只读数据类，这里仅演示语法
    }
    
    operator fun invoke(x: Int, y: Int): Point {
        helperTopLevel(6)
        return Point(this.x + x, this.y + y)
    }
}

class CollectionLike {
    private val items = mutableListOf<String>()
    
    operator fun plusAssign(item: String) {
        helperTopLevel(7)
        items.add(item)
    }
    
    operator fun minusAssign(item: String) {
        helperTopLevel(8)
        items.remove(item)
    }
    
    operator fun contains(item: String): Boolean {
        helperTopLevel(9)
        return items.contains(item)
    }
    
    operator fun iterator(): Iterator<String> {
        helperTopLevel(10)
        return items.iterator()
    }
}

fun operatorCalls() {
    val p1 = Point(1, 2)
    val p2 = Point(3, 4)
    val p3 = p1 + p2 // plus 操作符调用
    val p4 = p1 - p2 // minus 操作符调用
    val p5 = p1 * 2 // times 操作符调用
    val x = p1[0] // get 操作符调用
    val p6 = p1(10, 20) // invoke 操作符调用
    
    val coll = CollectionLike()
    coll += "a" // plusAssign 调用
    coll -= "a" // minusAssign 调用
    val has = "a" in coll // contains 调用
    
    for (item in coll) { // iterator 调用
        helperTopLevel(11)
    }
}
