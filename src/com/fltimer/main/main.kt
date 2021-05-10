package com.fltimer.main

import com.fltimer.main.data.DataManager
import com.fltimer.main.gui.GuiManager
import com.fltimer.main.scramble.ScrambleManager
import com.fltimer.main.timer.TimerManager

fun main() {
    val scrambleManager = ScrambleManager()

    //order matters?
    setupManagers(
        TimerManager(),
        DataManager(),
        scrambleManager,
        GuiManager()
    )

    scrambleManager.handleScrambleRequestNew()
}

fun setupManagers(vararg managers: Listenable) {
    managers.forEach { m1 ->
        managers.forEach { m2 ->
            if (m2 != m1) {
                m1.addListener(m2 as EventListener)
            }
        }
    }
}
