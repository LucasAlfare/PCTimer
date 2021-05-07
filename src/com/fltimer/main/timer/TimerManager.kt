package com.fltimer.main.timer

import com.fltimer.main.Event
import com.fltimer.main.EventListener
import com.fltimer.main.Listenable
import java.util.*

/**
 * In Events:
 *
 * - TIMER_TOGGLE_UP(params: current time);
 * - TIMER_TOGGLE_DOWN(params: current time);
 * - TIMER_SET_WCA_INSPECTION(params: boolean?);
 * - TIMER_CANCEL.
 *
 * Out Events:
 * - TIMER_UPDATE(carries: current elapsed time);
 * - TIMER_INSPECTION_STARTED;
 * - TIMER_INSPECTION_STOPPED;
 * - TIMER_STARTED;
 * - TIMER_STOPPED(carries: the total elapsed time).
 *
 * TODO: handle automatic penalty detection, through inspection
 */
class TimerManager : Listenable(), EventListener {

    private var useInspection = false
    private var solving = false
    private var startTime = 0L

    var elapsedTime = 0L

    private var repeater: Timer? = null

    override fun onEvent(event: Event, data: Any?) {
        when (event) {
            Event.TIMER_TOGGLE_DOWN -> handleTimerToggle(false, data as Long)
            Event.TIMER_TOGGLE_UP -> handleTimerToggle(true, data as Long)
            Event.TIMER_SET_WCA_INSPECTION -> handleTimerSetWcaInspection(data as Boolean)
            Event.TIMER_CANCEL -> { /*TODO*/ }
            else -> {
            }
        }
    }

    fun handleTimerSetWcaInspection(useInspection: Boolean) {
        this.useInspection = useInspection
    }

    fun handleTimerToggle(up: Boolean, time: Long) {
        if (up) { // releases the toggle "key" (any trigger)
            if (!solving) {
                startTimer(time)
            } else {
                stopTimer()
            }
        } else {

        }
    }

    private fun startTimer(currentTime: Long) {
        startTime = currentTime

        repeater = Timer()
        repeater!!.scheduleAtFixedRate(object : TimerTask() {
            override fun run() {
                elapsedTime = System.currentTimeMillis() - startTime
                notifyListeners(Event.TIMER_UPDATE, elapsedTime)
            }
        }, 0, 1)

        solving = true
        notifyListeners(Event.TIMER_STARTED)
    }

    private fun stopTimer() {
        repeater!!.cancel()
        repeater = null
        solving = false

        notifyListeners(Event.TIMER_STOPPED, elapsedTime)
    }
}
