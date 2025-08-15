package `fun`.baxt.entities

import java.awt.Graphics

abstract class Entity(initialX: Float, initialY: Float) {
    var xPosition: Float = initialX
        protected set
    var yPosition: Float = initialY
        protected set

    abstract fun update()

    abstract fun render(graphics: Graphics)
}