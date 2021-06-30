package com.fltimer

import com.fltimer.data.DataManager
import com.fltimer.gui.GuiManager
import com.fltimer.scramble.ScrambleManager
import com.fltimer.timer.TimerManager

fun main() {
    val scrambleManager = ScrambleManager()

    //order matters?
    setupManagers(
        scrambleManager,
        TimerManager(),
        DataManager(),
        GuiManager()
    )

    scrambleManager.handleScrambleRequestNew()
}

private fun setupManagers(vararg managers: Listenable) {
    managers.forEach { m1 ->
        managers.forEach { m2 ->
            if (m2 != m1) {
                m1.addListener(m2 as EventListener)
            }
        }
    }
}
