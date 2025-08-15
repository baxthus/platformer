package `fun`.baxt.entities

import `fun`.baxt.model.Direction
import `fun`.baxt.utils.loadImage
import java.awt.Graphics
import java.awt.image.BufferedImage

class Player(initialX: Float = 100f, initialY: Float = 100f) : Entity(initialX, initialY) {
    enum class Animations(val spriteCount: Int) {
        IDLE(5),
        RUNNING(6),
        JUMPING(3),
        FALLING(1),
        GROUND(2),
        HIT(4),
        ATTACK_1(3),
        ATTACK_JUMP_1(3),
        ATTACK_JUMP_2(3)
    }

    companion object {
        private const val SPRITE_WIDTH = 64
        private const val SPRITE_HEIGHT = 40
        private const val RENDER_WIDTH = 128
        private const val RENDER_HEIGHT = 80
        private const val ANIMATION_SPEED = 25
        private const val MOVEMENT_SPEED = 5f
        private const val SPRITE_COLS = 6
        private const val SPRITE_ROWS = 9
    }

    private val playerSprite: BufferedImage = loadImage("player_sprites.png").getOrElse {
        error("Failed to load player sprite: ${it.message}")
    }

    private val animations: Array<Array<BufferedImage>> = Array(SPRITE_ROWS) { row ->
        Array(SPRITE_COLS) { col ->
            playerSprite.getSubimage(
                col * SPRITE_WIDTH,
                row * SPRITE_HEIGHT,
                SPRITE_WIDTH,
                SPRITE_HEIGHT
            )
        }
    }

    private var animationTick = 0
    private var animationIndex = 0
    private var currentAction = Animations.IDLE
    private var direction: Direction? = null
    private var isMoving = false
    private var isAttacking = false

    fun setDirection(newDirection: Direction) {
        if (!isAttacking) { // Prevent movement during attack
            direction = newDirection
            isMoving = true
        }
    }

    fun setMoving(moving: Boolean) {
        if (!isAttacking) { // Prevent stopping movement during attack
            isMoving = moving
            if (!moving) {
                resetAnimation(Animations.IDLE)
            }
        }
    }

    fun setAttacking(attacking: Boolean) {
        isAttacking = attacking
        if (attacking) {
            resetAnimation(Animations.ATTACK_1)
        } else {
            // Return to appropriate state after attack
            currentAction = if (isMoving) Animations.RUNNING else Animations.IDLE
            resetAnimationCounters()
        }
    }

    override fun update() {
        updateAnimationTick()
        updateAnimation()
        updatePosition()
    }

    override fun render(graphics: Graphics) {
        val currentFrame = getCurrentFrame()
        graphics.drawImage(
            currentFrame,
            xPosition.toInt(),
            yPosition.toInt(),
            RENDER_WIDTH,
            RENDER_HEIGHT,
            null
        )
    }

    private fun getCurrentFrame(): BufferedImage = animations[currentAction.ordinal][animationIndex]

    private fun updateAnimationTick() {
        animationTick++
        if (animationTick >= ANIMATION_SPEED) {
            animationTick = 0
            animationIndex++
            if (animationIndex >= currentAction.spriteCount) {
                animationIndex = 0
                handleAnimationLoop()
            }
        }
    }

    private fun handleAnimationLoop() {
        // Handle one-time animations
        when (currentAction) {
            Animations.ATTACK_1 -> {
                isAttacking = false
                currentAction = if (isMoving) Animations.RUNNING else Animations.IDLE
            }
            // Add other one-time animations here
            else -> { /* Looping animations don't need special handling */
            }
        }
    }

    private fun updateAnimation() {
        if (isAttacking) return // Don't change animation during attack

        val newAction = determineAnimation()
        if (newAction != currentAction) {
            currentAction = newAction
            resetAnimationCounters()
        }
    }

    private fun determineAnimation(): Animations {
        return when {
            isAttacking -> Animations.ATTACK_1
            isMoving -> Animations.RUNNING
            else -> Animations.IDLE
        }
    }

    private fun updatePosition() {
        if (!isMoving || isAttacking) return

        direction?.let { dir ->
            when (dir) {
                Direction.LEFT -> xPosition -= MOVEMENT_SPEED
                Direction.RIGHT -> xPosition += MOVEMENT_SPEED
                Direction.UP -> yPosition -= MOVEMENT_SPEED
                Direction.DOWN -> yPosition += MOVEMENT_SPEED
            }
        }
    }

    private fun resetAnimation(newAction: Animations) {
        currentAction = newAction
        resetAnimationCounters()
    }

    private fun resetAnimationCounters() {
        animationIndex = 0
        animationTick = 0
    }
}
