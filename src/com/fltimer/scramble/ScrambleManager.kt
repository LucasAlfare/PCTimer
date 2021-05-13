package com.fltimer.scramble

import com.fltimer.Event
import com.fltimer.EventListener
import com.fltimer.Listenable
import java.util.*

interface Scrambler {

    val rand: Random
        get() = Random()

    fun getScramble(): String
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

    private var scrambler = PocketCubeScrambler()

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
