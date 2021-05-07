package com.fltimer.main.gui

import com.fltimer.main.Event
import com.fltimer.main.EventListener
import com.fltimer.main.Listenable
import java.util.*
import kotlin.collections.ArrayList

/**
 * Basic custom "gui" manager that works in console.
 *
 * This should be fine to test functionality of other managers.
 */
class ConsoleManager : Listenable(), EventListener {

    init {
        val scanner = Scanner(System.`in`)

        Thread {
            while (true) {
                val next = scanner.nextLine().trim().toUpperCase()

                if (next.contains("DATA_ITEM_CREATE")) {
                    val params = next.split(",")
                    notifyListeners(Event.DATA_ITEM_CREATE, arrayOf(params[1].toLong(), params[2], params[3]))
                }
            }
        }.start()

        println("console started...")
    }

    override fun onEvent(event: Event, data: Any?) {
        when (event) {
            Event.DATA_RESPONSE -> {
                println("[DATA_RESPONSE] $data")
            }

            Event.DATA_CHANGED -> {
                println("[DATA_CHANGED] ${data as ArrayList<*>}")
            }
        }
    }
}