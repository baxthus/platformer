package `fun`.baxt.utils

import java.awt.image.BufferedImage
import javax.imageio.ImageIO

fun loadImage(path: String): Result<BufferedImage> = runCatching {
    val input = object {}.javaClass.getResourceAsStream("/$path")
        ?: error("Resource not found: $path")
    try {
        ImageIO.read(input) ?: error("Could not decode image: $path")
    } finally {
        runCatching { input.close() }
    }
}