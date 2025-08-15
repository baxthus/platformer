package `fun`.baxt

import `fun`.baxt.config.GameProperties
import javax.swing.JFrame

class GameWindow(gamePanel: GamePanel) : JFrame() {
    init {
        title = GameProperties.TITLE
        defaultCloseOperation = EXIT_ON_CLOSE

        add(gamePanel)

        setLocationRelativeTo(null)
        isResizable = false
        pack()
        // isVisible = true
    }
}