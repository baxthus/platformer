package `fun`.baxt

import `fun`.baxt.config.Direction
import `fun`.baxt.config.GameProperties
import `fun`.baxt.entities.Player
import `fun`.baxt.inputs.KeyboardInputs
import `fun`.baxt.inputs.MouseInputs
import java.awt.Color
import java.awt.Dimension
import java.awt.Graphics
import javax.swing.JPanel

class GamePanel : JPanel() {
    private val player = Player()

    init {
        setupPanel()
        setupInputHandlers()
    }

    private fun setupPanel() {
        Dimension(GameProperties.WIDTH, GameProperties.HEIGHT).let {
            minimumSize = it
            preferredSize = it
            maximumSize = it
        }
        background = Color.magenta
    }

    private fun setupInputHandlers() {
        addKeyListener(KeyboardInputs(this))
        MouseInputs(this).let {
            addMouseListener(it)
            addMouseMotionListener(it)
        }
    }

    fun setDirection(direction: Direction) {
        player.setDirection(direction)
    }

    fun setMoving(moving: Boolean) {
        player.setMoving(moving)
    }

    override fun paintComponent(g: Graphics) {
        super.paintComponent(g)
        player.render(g)
    }

    fun update() {
        player.update()
    }
}