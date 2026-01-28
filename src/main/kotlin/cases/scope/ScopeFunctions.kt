@file:Suppress("unused")

package cases.scope

import cases.core.helperTopLevel
import cases.types.ResultBox
import cases.types.ok

class Resource {
    fun configure(): Resource {
        helperTopLevel(1)
        return this
    }
    
    fun use(): String {
        helperTopLevel(2)
        return "used"
    }
    
    fun close() {
        helperTopLevel(3)
    }
}

fun letScope() {
    val result = "test".let { str ->
        helperTopLevel(1) // let 中的调用
        str.uppercase()
    }
}

fun runScope() {
    val result = "test".run {
        helperTopLevel(2) // run 中的调用
        this.uppercase()
    }
}

fun withScope() {
    val resource = Resource()
    with(resource) {
        configure() // with 中的调用
        use()
    }
}

fun applyScope() {
    val resource = Resource().apply {
        configure() // apply 中的调用
    }
    resource.use()
}

fun alsoScope() {
    val resource = Resource().also {
        it.configure() // also 中的调用
    }
    resource.use()
}

fun takeIfScope() {
    val result = "test".takeIf { it.length > 0 }?.let {
        helperTopLevel(4) // takeIf + let 链式调用
        it.uppercase()
    }
}

fun takeUnlessScope() {
    val result = "test".takeUnless { it.isEmpty() }?.let {
        helperTopLevel(5)
        it.uppercase()
    }
}

fun repeatScope() {
    repeat(3) { i ->
        helperTopLevel(i) // repeat 回调中的调用
    }
}

fun scopeChain(): ResultBox<String> {
    return Resource()
        .apply { configure() }
        .let { it.use() }
        .ok()
}
