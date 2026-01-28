@file:Suppress("unused")

package cases.calls

import cases.core.AnnotatedClass
import cases.core.CoreSingleton
import cases.core.UserId
import cases.core.defaultAndNamedArgs
import cases.core.helperTopLevel
import cases.core.topLevelFun
import cases.types.BaseRepo
import cases.types.Repo
import cases.types.User

interface Greeter {
    fun greet(name: String): String
}

open class BaseGreeter : Greeter {
    override fun greet(name: String): String = "hello $name"
    open fun greetMany(vararg names: String): List<String> = names.map { greet(it) }
}

class FancyGreeter : BaseGreeter() {
    override fun greet(name: String): String = super.greet(name).uppercase() // super 调用
    override fun greetMany(vararg names: String): List<String> =
        super.greetMany(*names).map { it + "!" } // vararg spread + super
}

class Service(private val repo: Repo<User>) {
    fun create(id: String, name: String): User {
        val u = User(id, name)
        repo.put(id, u) // interface dispatch
        return u
    }

    fun findUpper(id: String): String? {
        return repo.get(id)?.name?.uppercase() // safe call + member call chain
    }
}

fun runDispatchScenarios(): String {
    val g: Greeter = FancyGreeter()
    val s = g.greet("kotlin") // interface -> override

    val repo = BaseRepo<User>()
    val svc = Service(repo)
    svc.create("1", "Alice")
    val u = svc.findUpper("1")

    val a = AnnotatedClass(name = "n", count = 1)
    a.mutable = 2 // setter call
    val m = a.mutable // getter call

    val uid = UserId("  XyZ  ").normalized() // value class method call
    val d = defaultAndNamedArgs()
    val tl = topLevelFun(1) + helperTopLevel(2)
    val obj = CoreSingleton.staticLikeCall()

    return listOfNotNull(s, u, uid, m.toString(), d.toString(), tl.toString(), obj).joinToString("|")
}

