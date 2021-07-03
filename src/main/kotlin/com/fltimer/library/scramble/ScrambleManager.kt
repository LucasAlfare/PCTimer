package com.fltimer.library.scramble

import com.fltimer.library.Listenable
import com.fltimer.library.EventListener
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

    override fun onEvent(event: com.fltimer.library.Event, data: Any?) {
        when (event) {
            com.fltimer.library.Event.SCRAMBLE_REQUEST_NEW -> {
                handleScrambleRequestNew()
            }

            com.fltimer.library.Event.SCRAMBLE_REQUEST_LAST -> {
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
        notifyListeners(com.fltimer.library.Event.SCRAMBLE_CHANGED, current)
    }

    fun handleScrambleRequestLast() {
        notifyListeners(com.fltimer.library.Event.SCRAMBLE_RESPONSE_LAST, last)
    }
}
