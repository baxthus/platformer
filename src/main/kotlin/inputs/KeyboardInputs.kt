package `fun`.baxt.inputs

import `fun`.baxt.GamePanel
import `fun`.baxt.model.Direction
import java.awt.event.KeyEvent
import java.awt.event.KeyListener
import kotlin.system.exitProcess

class KeyboardInputs(private val gamePanel: GamePanel) : KeyListener {
    override fun keyTyped(e: KeyEvent?) {}

    override fun keyPressed(e: KeyEvent?) {
        when (e?.keyCode) {
            KeyEvent.VK_W, KeyEvent.VK_UP -> gamePanel.player.setDirection(Direction.UP)
            KeyEvent.VK_A, KeyEvent.VK_LEFT -> gamePanel.player.setDirection(Direction.LEFT)
            KeyEvent.VK_S, KeyEvent.VK_DOWN -> gamePanel.player.setDirection(Direction.DOWN)
            KeyEvent.VK_D, KeyEvent.VK_RIGHT -> gamePanel.player.setDirection(Direction.RIGHT)
            KeyEvent.VK_SPACE -> gamePanel.player.setAttacking(true)
            KeyEvent.VK_ESCAPE -> exitProcess(0)
        }
    }

    override fun keyReleased(e: KeyEvent?) {
        if (e?.keyCode == KeyEvent.VK_SPACE)
            gamePanel.player.setAttacking(false)
        else
            gamePanel.player.setMoving(false)

    }
}