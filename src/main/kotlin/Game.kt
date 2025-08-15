package `fun`.baxt

class Game : Runnable {
    object Properties {
        const val TITLE = "Platformer"
        const val WIDTH = 1280
        const val HEIGHT = 800
        const val FPS_TARGET = 144
        const val UPS_TARGET = 200
        const val TIME_PER_TICK = 1_000_000_000L / FPS_TARGET
        const val TIME_PER_UPDATE = 1_000_000_000L / UPS_TARGET
    }

    private val gamePanel = GamePanel()
    private val gameWindow = GameWindow(gamePanel)
    private var running = false

    fun start() {
        if (!running) {
            running = true
            val gameThread = Thread(this)
            gameWindow.isVisible = true
            gamePanel.requestFocus()
            gameThread.start()
        }
    }

    fun stop() {
        running = false
    }

    private fun update() {
        gamePanel.update()
    }

    override fun run() {
        var previousTime = System.nanoTime()
        var frames = 0
        var updates = 0
        var lastCheck = System.currentTimeMillis()
        var deltaU = 0.0
        var deltaF = 0.0

        while (running) {
            val currentTime = System.nanoTime()
            val deltaTime = currentTime - previousTime
            previousTime = currentTime

            deltaU += deltaTime.toDouble() / Properties.TIME_PER_UPDATE
            deltaF += deltaTime.toDouble() / Properties.TIME_PER_TICK

            if (deltaU >= 1.0) {
                update()
                updates++
                deltaU -= 1.0
            }

            if (deltaF >= 1.0) {
                gamePanel.repaint()
                frames++
                deltaF -= 1.0
            }

            // FPS/UPS counter (optional debug info)
            if (System.currentTimeMillis() - lastCheck >= 1000) {
                lastCheck = System.currentTimeMillis()
                // println("FPS: $frames | UPS: $updates")
                frames = 0
                updates = 0
            }
        }
    }
}