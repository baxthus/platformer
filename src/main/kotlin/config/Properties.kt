package `fun`.baxt.config

object GameProperties {
    const val TITLE = "Platformer"
    const val WIDTH = 1280
    const val HEIGHT = 800
    const val FPS_TARGET = 144
    const val UPS_TARGET = 200
    const val TIME_PER_TICK = 1_000_000_000L / FPS_TARGET
    const val TIME_PER_UPDATE = 1_000_000_000L / UPS_TARGET
}

object PlayerProperties {
    enum class Animations {
        IDLE,
        RUNNING,
        JUMPING,
        FALLING,
        GROUND,
        HIT,
        ATTACK_1,
        ATTACK_JUMP_1,
        ATTACK_JUMP_2
    }

    fun getSpriteAmount(playerAction: Animations): Int = when (playerAction) {
        Animations.IDLE -> 5
        Animations.RUNNING -> 6
        Animations.HIT -> 4
        Animations.JUMPING,
        Animations.ATTACK_1,
        Animations.ATTACK_JUMP_1,
        Animations.ATTACK_JUMP_2 -> 3
        Animations.GROUND -> 2
        Animations.FALLING -> 1
    }
}

enum class Direction {
    LEFT,
    UP,
    RIGHT,
    DOWN
}
