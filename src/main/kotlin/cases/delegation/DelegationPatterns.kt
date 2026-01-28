@file:Suppress("unused")

package cases.delegation

import cases.core.helperTopLevel
import kotlin.properties.Delegates

interface Repository {
    fun save(data: String): Boolean
    fun load(id: String): String?
}

class DatabaseRepository : Repository {
    override fun save(data: String): Boolean {
        helperTopLevel(1)
        return true
    }
    
    override fun load(id: String): String? {
        helperTopLevel(2)
        return "data"
    }
}

class CachedRepository(private val delegate: Repository) : Repository by delegate {
    private val cache = mutableMapOf<String, String>()
    
    override fun load(id: String): String? {
        return cache[id] ?: delegate.load(id)?.also { cache[id] = it } // 委托调用
    }
}

class LazyProperty {
    val expensive: String by lazy {
        helperTopLevel(1)
        "expensive"
    }
    
    fun useExpensive(): String {
        return expensive // 触发 lazy 初始化
    }
}

class ObservableProperty {
    var value: String by Delegates.observable("initial") { prop, old, new ->
        helperTopLevel(1) // 观察者回调调用
    }
}

class VetoableProperty {
    var value: Int by Delegates.vetoable(0) { prop, old, new ->
        helperTopLevel(new) // vetoable 回调
        new > 0
    }
}

fun delegateUsage() {
    val repo = CachedRepository(DatabaseRepository())
    repo.save("test") // 委托调用
    repo.load("id") // 委托调用
    
    val lazy = LazyProperty()
    lazy.useExpensive() // 触发 lazy
    
    val obs = ObservableProperty()
    obs.value = "new" // 触发 observable
    
    val veto = VetoableProperty()
    veto.value = 5 // 触发 vetoable
}
