package `fun`.baxt.inputs

import `fun`.baxt.GamePanel
import `fun`.baxt.Properties
import java.awt.event.KeyEvent
import java.awt.event.KeyListener
import kotlin.system.exitProcess

class KeyboardInputs(private val gamePanel: GamePanel) : KeyListener {
    override fun keyTyped(e: KeyEvent?) {}

    override fun keyPressed(e: KeyEvent?) {
        when (e?.keyCode) {
            KeyEvent.VK_W, KeyEvent.VK_UP -> gamePanel.setDirection(Properties.Directions.UP)
            KeyEvent.VK_A, KeyEvent.VK_LEFT -> gamePanel.setDirection(Properties.Directions.LEFT)
            KeyEvent.VK_S, KeyEvent.VK_DOWN -> gamePanel.setDirection(Properties.Directions.DOWN)
            KeyEvent.VK_D, KeyEvent.VK_RIGHT -> gamePanel.setDirection(Properties.Directions.RIGHT)
            KeyEvent.VK_ESCAPE -> exitProcess(0)
        }
    }

    override fun keyReleased(e: KeyEvent?) {
        gamePanel.setMoving(false)
    }
}