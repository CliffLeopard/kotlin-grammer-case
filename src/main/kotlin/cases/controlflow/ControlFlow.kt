@file:Suppress("unused", "ControlFlowWithEmptyBody")

package cases.controlflow

import cases.core.helperTopLevel

fun ifExpressions(x: Int): Int {
    return if (x > 0) {
        helperTopLevel(1)
        x
    } else {
        helperTopLevel(2)
        0
    }
}

fun whenExpressions(value: Any): String {
    return when (value) {
        is String -> {
            helperTopLevel(1)
            value.uppercase()
        }
        is Int -> {
            helperTopLevel(2)
            value.toString()
        }
        else -> {
            helperTopLevel(3)
            "unknown"
        }
    }
}

fun whenWithConditions(x: Int): String {
    return when {
        x > 10 -> {
            helperTopLevel(1)
            "large"
        }
        x > 5 -> {
            helperTopLevel(2)
            "medium"
        }
        else -> {
            helperTopLevel(3)
            "small"
        }
    }
}

fun forLoops() {
    for (i in 1..5) {
        helperTopLevel(i)
    }
    
    for (i in 5 downTo 1) {
        helperTopLevel(i)
    }
    
    for (i in 1 until 10 step 2) {
        helperTopLevel(i)
    }
    
    val list = listOf("a", "b", "c")
    for (item in list) {
        helperTopLevel(item.length)
    }
    
    for ((index, item) in list.withIndex()) {
        helperTopLevel(index)
        helperTopLevel(item.length)
    }
}

fun whileLoops() {
    var x = 0
    while (x < 5) {
        helperTopLevel(x)
        x++
    }
    
    do {
        helperTopLevel(x)
        x--
    } while (x > 0)
}

fun breakAndContinue() {
    for (i in 1..10) {
        if (i == 5) {
            helperTopLevel(1)
            break
        }
        if (i % 2 == 0) {
            helperTopLevel(2)
            continue
        }
        helperTopLevel(i)
    }
}

fun labeledBreaks() {
    outer@ for (i in 1..3) {
        inner@ for (j in 1..3) {
            if (i * j > 4) {
                helperTopLevel(1)
                break@outer
            }
            helperTopLevel(i * j)
        }
    }
}

fun returnFromLambda() {
    listOf(1, 2, 3).forEach {
        if (it == 2) {
            helperTopLevel(1)
            return@forEach // 标签返回
        }
        helperTopLevel(it)
    }
}

fun tryCatchFinally() {
    try {
        helperTopLevel(1)
        throw RuntimeException("test")
    } catch (e: RuntimeException) {
        helperTopLevel(2)
    } catch (e: Exception) {
        helperTopLevel(3)
    } finally {
        helperTopLevel(4)
    }
}

fun elvisOperator(x: String?): String {
    return x ?: run {
        helperTopLevel(1)
        "default"
    }
}
