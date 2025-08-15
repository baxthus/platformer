package `fun`.baxt.entities

import `fun`.baxt.Game
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
        private const val RENDER_WIDTH = (SPRITE_WIDTH * Game.Properties.SCALE).toInt()
        private const val RENDER_HEIGHT = (SPRITE_HEIGHT * Game.Properties.SCALE).toInt()
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
    private val activeDirections = mutableSetOf<Direction>()
    private var isMoving = false
    private var isAttacking = false

    fun addDirection(direction: Direction) {
        if (!isAttacking) {
            activeDirections.add(direction)
            isMoving = activeDirections.isNotEmpty()
        }
    }

    fun removeDirection(direction: Direction) {
        if (!isAttacking) {
            activeDirections.remove(direction)
            isMoving = activeDirections.isNotEmpty()
            if (!isMoving) {
                resetAnimation(Animations.IDLE)
            }
        }
    }

    fun setDirection(newDirection: Direction) {
        if (!isAttacking) {
            activeDirections.clear()
            activeDirections.add(newDirection)
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

    fun resetMoving() {
        isMoving = false
        activeDirections.clear()
        resetAnimation(Animations.IDLE)
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
        if (!isMoving || isAttacking || activeDirections.isEmpty()) return

        var deltaX = 0f
        var deltaY = 0f

        // Calculate movement vector from active directions
        for (direction in activeDirections) {
            when (direction) {
                Direction.LEFT -> deltaX -= 1f
                Direction.RIGHT -> deltaX += 1f
                Direction.UP -> deltaY -= 1f
                Direction.DOWN -> deltaY += 1f
                Direction.UP_LEFT -> {
                    deltaX -= 1f
                    deltaY -= 1f
                }
                Direction.UP_RIGHT -> {
                    deltaX += 1f
                    deltaY -= 1f
                }
                Direction.DOWN_LEFT -> {
                    deltaX -= 1f
                    deltaY += 1f
                }
                Direction.DOWN_RIGHT -> {
                    deltaX += 1f
                    deltaY += 1f
                }
            }
        }

        // Normalize diagonal movement to maintain consistent speed
        if (deltaX != 0f && deltaY != 0f) {
            val normalizer = 0.7071f // 1/sqrt(2) to normalize diagonal movement
            deltaX *= normalizer
            deltaY *= normalizer
        }

        // Apply movement
        xPosition += deltaX * MOVEMENT_SPEED
        yPosition += deltaY * MOVEMENT_SPEED
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
