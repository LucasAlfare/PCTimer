package com.fltimer.main.scramble

import com.fltimer.main.Event
import com.fltimer.main.EventListener
import com.fltimer.main.Listenable
import kotlin.math.floor

class TestScrambler {
    fun getScramble() = "scramble-${floor(Math.random() * 100)}"
}

/**
 * In Events:
 * - SCRAMBLE_REQUEST_NEW;
 * - SCRAMBLE_REQUEST_LAST.
 *
 * Out Events:
 * - SCRAMBLE_CHANGED;
 * - SCRAMBLE_RESPONSE.
 */
class ScrambleManager : Listenable(), EventListener {

    private var current = ""
    private var last = ""

    private var scrambler = TestScrambler()

    override fun onEvent(event: Event, data: Any?) {
        when (event) {
            Event.SCRAMBLE_REQUEST_NEW -> {
                handleScrambleRequestNew()
            }

            Event.SCRAMBLE_REQUEST_LAST -> {
                handleScrambleRequestLast()
            }

            else -> {
            }
        }
    }

    fun handleScrambleRequestNew() {
        //TODO
        last = current
        current = scrambler.getScramble()
        notifyListeners(Event.SCRAMBLE_CHANGED, current)
    }

    fun handleScrambleRequestLast() {
        notifyListeners(Event.SCRAMBLE_RESPONSE_LAST, last)
    }
}