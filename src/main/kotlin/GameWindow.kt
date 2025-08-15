package `fun`.baxt

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
    }
}