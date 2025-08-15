package `fun`.baxt.entities

import `fun`.baxt.Properties
import `fun`.baxt.utils.loadImage
import java.awt.Graphics
import java.awt.image.BufferedImage

class Player(initialX: Int = 100, initialY: Int = 100) {
    private val playerSprite = loadImage("player_sprites.png").getOrElse {
        error("Failed to load player sprite: ${it.message}")
    }

    private val animations: Array<Array<BufferedImage>> = Array(9) { row ->
        Array(6) { col ->
            playerSprite.getSubimage(col * 64, row * 40, 64, 40)
        }
    }

    var xPosition = initialX
        private set
    var yPosition = initialY
        private set

    private var animationTick = 0
    private var animationIndex = 0
    private val animationSpeed = 25
    private var currentAction = Properties.Player.Animations.IDLE
    private var direction: Properties.Directions? = null
    private var isMoving = false

    private val movementSpeed = 5

    fun setDirection(newDirection: Properties.Directions) {
        direction = newDirection
        isMoving = true
    }

    fun setMoving(moving: Boolean) {
        isMoving = moving
        if (!moving) {
            currentAction = Properties.Player.Animations.IDLE
            animationIndex = 0
        }
    }

    fun update() {
        updateAnimationTick()
        updateAnimation()
        updatePosition()
    }

    fun render(graphics: Graphics) {
        graphics.drawImage(
            animations[currentAction.ordinal][animationIndex],
            xPosition,
            yPosition,
            128,
            80,
            null
        )
    }

    private fun updateAnimationTick() {
        animationTick++
        if (animationTick >= animationSpeed) {
            animationTick = 0
            animationIndex++
            if (animationIndex >= Properties.Player.getSpriteAmount(currentAction)) {
                animationIndex = 0
            }
        }
    }

    private fun updateAnimation() {
        currentAction = if (isMoving) {
            Properties.Player.Animations.RUNNING
        } else {
            Properties.Player.Animations.IDLE
        }
    }

    private fun updatePosition() {
        if (!isMoving) return

        when (direction) {
            Properties.Directions.LEFT -> xPosition -= movementSpeed
            Properties.Directions.RIGHT -> xPosition += movementSpeed
            Properties.Directions.UP -> yPosition -= movementSpeed
            Properties.Directions.DOWN -> yPosition += movementSpeed
            null -> {}
        }
    }
}
