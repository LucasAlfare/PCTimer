package com.fltimer.library

import com.fltimer.library.data.DataManager
import com.fltimer.library.gui.GuiManager
import com.fltimer.library.scramble.ScrambleManager
import com.fltimer.library.timer.TimerManager

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

private fun setupManagers(vararg managers: com.fltimer.library.Listenable) {
    managers.forEach { m1 ->
        managers.forEach { m2 ->
            if (m2 != m1) {
                m1.addListener(m2 as com.fltimer.library.EventListener)
            }
        }
    }
}
