package `fun`.baxt

import `fun`.baxt.entities.Player
import `fun`.baxt.inputs.KeyboardInputs
import `fun`.baxt.inputs.MouseInputs
import java.awt.Color
import java.awt.Dimension
import java.awt.Graphics
import javax.swing.JPanel

class GamePanel : JPanel() {
    val player = Player()

    init {
        setupPanel()
        setupInputHandlers()
    }

    private fun setupPanel() {
        Dimension(Game.Properties.WIDTH, Game.Properties.HEIGHT).let {
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

    override fun paintComponent(g: Graphics) {
        super.paintComponent(g)
        player.render(g)
    }

    fun update() {
        player.update()
    }
}