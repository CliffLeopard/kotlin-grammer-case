@file:Suppress("unused")

package cases.modules

import cases.core.helperTopLevel
import cases.core.CoreSingleton
import cases.types.ResultBox
import cases.types.ok

// 模块接口
interface UserModule {
    fun getUser(id: String): ResultBox<String>
}

// 模块实现
class UserModuleImpl : UserModule {
    override fun getUser(id: String): ResultBox<String> {
        helperTopLevel(1)
        return id.ok()
    }
}

// 使用模块的类
class UserService(private val userModule: UserModule) {
    fun findUser(id: String): ResultBox<String> {
        return userModule.getUser(id) // 模块方法调用
    }
}

// 导出函数
fun exportedFunction(x: Int): Int {
    return helperTopLevel(x)
}

// 导出类
class ExportedClass {
    fun method(): Int {
        return helperTopLevel(2)
    }
}

// 导入并使用其他模块
fun crossModuleCall(): Int {
    val fromCore = helperTopLevel(3)
    val fromSingleton = CoreSingleton.staticLikeCall()
    return fromCore + fromSingleton.length
}

// 模块工厂
object ModuleFactory {
    fun createUserModule(): UserModule {
        helperTopLevel(4)
        return UserModuleImpl()
    }
}

fun moduleUsage() {
    val module = ModuleFactory.createUserModule()
    val service = UserService(module)
    service.findUser("123")
    crossModuleCall()
}
