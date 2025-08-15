package `fun`.baxt.inputs

import `fun`.baxt.GamePanel
import `fun`.baxt.model.Direction
import java.awt.event.KeyEvent
import java.awt.event.KeyListener
import kotlin.system.exitProcess

class KeyboardInputs(private val gamePanel: GamePanel) : KeyListener {
    private val pressedKeys = mutableSetOf<Int>()

    override fun keyTyped(e: KeyEvent?) {}

    override fun keyPressed(e: KeyEvent?) {
        e?.keyCode?.let { keyCode ->
            if (!pressedKeys.contains(keyCode)) {
                pressedKeys.add(keyCode)
                updateMovement()
            }

            when (keyCode) {
                KeyEvent.VK_SPACE -> gamePanel.player.setAttacking(true)
                KeyEvent.VK_ESCAPE -> exitProcess(0)
            }
        }
    }

    override fun keyReleased(e: KeyEvent?) {
        e?.keyCode?.let { keyCode ->
            pressedKeys.remove(keyCode)

            when (keyCode) {
                KeyEvent.VK_SPACE -> gamePanel.player.setAttacking(false)
                KeyEvent.VK_W, KeyEvent.VK_UP,
                KeyEvent.VK_A, KeyEvent.VK_LEFT,
                KeyEvent.VK_S, KeyEvent.VK_DOWN,
                KeyEvent.VK_D, KeyEvent.VK_RIGHT -> {
                    updateMovement()
                }
            }
        }
    }

    private fun updateMovement() {
        // Clear current directions
        gamePanel.player.resetMoving()

        val isUpPressed = pressedKeys.contains(KeyEvent.VK_W) || pressedKeys.contains(KeyEvent.VK_UP)
        val isDownPressed = pressedKeys.contains(KeyEvent.VK_S) || pressedKeys.contains(KeyEvent.VK_DOWN)
        val isLeftPressed = pressedKeys.contains(KeyEvent.VK_A) || pressedKeys.contains(KeyEvent.VK_LEFT)
        val isRightPressed = pressedKeys.contains(KeyEvent.VK_D) || pressedKeys.contains(KeyEvent.VK_RIGHT)

        // Add directions based on pressed keys
        if (isUpPressed && isLeftPressed) {
            gamePanel.player.addDirection(Direction.UP_LEFT)
        } else if (isUpPressed && isRightPressed) {
            gamePanel.player.addDirection(Direction.UP_RIGHT)
        } else if (isDownPressed && isLeftPressed) {
            gamePanel.player.addDirection(Direction.DOWN_LEFT)
        } else if (isDownPressed && isRightPressed) {
            gamePanel.player.addDirection(Direction.DOWN_RIGHT)
        } else {
            // Single direction movement
            if (isUpPressed) gamePanel.player.addDirection(Direction.UP)
            if (isDownPressed) gamePanel.player.addDirection(Direction.DOWN)
            if (isLeftPressed) gamePanel.player.addDirection(Direction.LEFT)
            if (isRightPressed) gamePanel.player.addDirection(Direction.RIGHT)
        }
    }
}