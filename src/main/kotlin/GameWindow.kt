package `fun`.baxt

import java.awt.event.WindowEvent
import java.awt.event.WindowFocusListener
import javax.swing.JFrame

class GameWindow(gamePanel: GamePanel) : JFrame() {
    init {
        title = Game.Properties.TITLE
        defaultCloseOperation = EXIT_ON_CLOSE

        add(gamePanel)

        setLocationRelativeTo(null)
        isResizable = false
        pack()
        // isVisible = true
        addWindowFocusListener(object : WindowFocusListener {
            override fun windowGainedFocus(e: WindowEvent?) {}

            override fun windowLostFocus(e: WindowEvent?) {
                gamePanel.player.resetMoving()
            }
        })
    }
}