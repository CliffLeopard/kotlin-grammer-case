@file:Suppress("unused", "RedundantOverride")

package cases.classes

import cases.core.helperTopLevel
import cases.types.ResultBox
import cases.types.ok

open class Animal(val name: String) {
    open fun makeSound(): String = "Some sound"
    
    fun getAnimalName(): String = name
    
    open fun callHelper(): Int = helperTopLevel(1)
}

class Dog(name: String) : Animal(name) {
    override fun makeSound(): String = "Woof!"
    
    override fun callHelper(): Int {
        val base = super.callHelper() // super CALLS
        return base + helperTopLevel(2)
    }
    
    fun bark(): String = makeSound() // 内部方法调用
}

interface Flyable {
    fun fly(): String
}

interface Swimmable {
    fun swim(): String
}

class Duck(name: String) : Animal(name), Flyable, Swimmable {
    override fun makeSound(): String = "Quack!"
    
    override fun fly(): String = "Flying high"
    
    override fun swim(): String = "Swimming"
    
    fun doBoth(): String = fly() + " and " + swim() // 接口方法调用
}

abstract class Shape {
    abstract fun area(): Double
    fun describe(): String = "Shape with area ${area()}" // 调用抽象方法
}

class Circle(val radius: Double) : Shape() {
    override fun area(): Double = kotlin.math.PI * radius * radius
}

class Rectangle(val width: Double, val height: Double) : Shape() {
    override fun area(): Double = width * height
}

fun polymorphicCalls(animal: Animal): String {
    val sound = animal.makeSound() // 多态调用
    val helper = animal.callHelper()
    return "$sound (helper=$helper)"
}

fun shapeAreaCall(shape: Shape): ResultBox<Double> {
    return shape.area().ok() // 链式调用
}
