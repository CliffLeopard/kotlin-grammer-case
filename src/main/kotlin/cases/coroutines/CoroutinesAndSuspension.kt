@file:Suppress("unused", "DeferredResultUnused")

package cases.coroutines

import cases.core.helperTopLevel
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*

suspend fun suspendFunction(x: Int): Int {
    delay(100)
    return helperTopLevel(x) // 挂起函数中的调用
}

fun coroutineBuilderCalls(): Deferred<Int> {
    return CoroutineScope(Dispatchers.Default).async {
        suspendFunction(1) // 协程中调用挂起函数
    }
}

suspend fun flowBuilder(): Flow<Int> = flow {
    for (i in 1..3) {
        emit(helperTopLevel(i)) // flow 中调用
        delay(100)
    }
}

suspend fun flowCollector() {
    flowBuilder().collect { value ->
        helperTopLevel(value) // collect 回调中调用
    }
}

class CoroutineScopeExample {
    suspend fun memberSuspend(): String {
        delay(50)
        return "done"
    }
    
    fun launchMember() {
        CoroutineScope(Dispatchers.IO).launch {
            memberSuspend() // 成员挂起函数调用
            helperTopLevel(1)
        }
    }
}

fun withContextCall(): Deferred<Int> {
    return CoroutineScope(Dispatchers.Default).async {
        withContext(Dispatchers.IO) {
            suspendFunction(2) // withContext 中调用
        }
    }
}
