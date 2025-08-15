package `fun`.baxt.entities

import `fun`.baxt.model.Direction
import `fun`.baxt.utils.loadImage
import java.awt.Graphics
import java.awt.image.BufferedImage

class Player(initialX: Float = 100f, initialY: Float = 100f) : Entity(initialX, initialY) {
    enum class Animations {
        IDLE,
        RUNNING,
        JUMPING,
        FALLING,
        GROUND,
        HIT,
        ATTACK_1,
        ATTACK_JUMP_1,
        ATTACK_JUMP_2
    }

    private val playerSprite = loadImage("player_sprites.png").getOrElse {
        error("Failed to load player sprite: ${it.message}")
    }

    private val animations: Array<Array<BufferedImage>> = Array(9) { row ->
        Array(6) { col ->
            playerSprite.getSubimage(col * 64, row * 40, 64, 40)
        }
    }

    private var animationTick = 0
    private var animationIndex = 0
    private val animationSpeed = 25
    private var currentAction = Animations.IDLE
    private var direction: Direction? = null
    private var isMoving = false

    private val movementSpeed = 5

    fun setDirection(newDirection: Direction) {
        direction = newDirection
        isMoving = true
    }

    fun setMoving(moving: Boolean) {
        isMoving = moving
        if (!moving) {
            currentAction = Animations.IDLE
            animationIndex = 0
        }
    }

    override fun update() {
        updateAnimationTick()
        updateAnimation()
        updatePosition()
    }

    override fun render(graphics: Graphics) {
        graphics.drawImage(
            animations[currentAction.ordinal][animationIndex],
            xPosition.toInt(),
            yPosition.toInt(),
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
            if (animationIndex >= getSpriteAmount(currentAction)) {
                animationIndex = 0
            }
        }
    }

    private fun updateAnimation() {
        currentAction = if (isMoving) {
            Animations.RUNNING
        } else {
            Animations.IDLE
        }
    }

    private fun updatePosition() {
        if (!isMoving) return

        when (direction) {
            Direction.LEFT -> xPosition -= movementSpeed
            Direction.RIGHT -> xPosition += movementSpeed
            Direction.UP -> yPosition -= movementSpeed
            Direction.DOWN -> yPosition += movementSpeed
            null -> {}
        }
    }

    private fun getSpriteAmount(playerAction: Animations): Int = when (playerAction) {
        Animations.IDLE -> 5
        Animations.RUNNING -> 6
        Animations.HIT -> 4
        Animations.JUMPING,
        Animations.ATTACK_1,
        Animations.ATTACK_JUMP_1,
        Animations.ATTACK_JUMP_2 -> 3
        Animations.GROUND -> 2
        Animations.FALLING -> 1
    }
}
