package `fun`.baxt.inputs

import `fun`.baxt.GamePanel
import java.awt.event.MouseEvent
import java.awt.event.MouseListener
import java.awt.event.MouseMotionListener

class MouseInputs(private val gamePanel: GamePanel) : MouseListener, MouseMotionListener {
    override fun mouseClicked(e: MouseEvent?) {}

    override fun mousePressed(e: MouseEvent?) {}

    override fun mouseReleased(e: MouseEvent?) {}

    override fun mouseEntered(e: MouseEvent?) {}

    override fun mouseExited(e: MouseEvent?) {}

    override fun mouseDragged(e: MouseEvent?) {}

    override fun mouseMoved(e: MouseEvent?) {}
}