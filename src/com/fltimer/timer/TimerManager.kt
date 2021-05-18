package com.fltimer.timer

import com.fltimer.Event
import com.fltimer.EventListener
import com.fltimer.Listenable
import com.fltimer.timestamp
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
 * - TIMER_UPDATE(carries: current formatted timer value);
 * - TIMER_INSPECTION_STARTED;
 * - TIMER_INSPECTION_STOPPED;
 * - TIMER_STARTED;
 * - TIMER_STOPPED(carries: the total elapsed time).
 *
 * TODO: handle automatic penalty detection, through inspection
 */
class TimerManager : Listenable(), EventListener {

    private var useInspection = true
    private var inspecting = false
    private var numUps = 0
    private var startTime = 0L
    private var elapsedTime = 0L
    private var repeater: Timer? = null
    private var inspectionRepeater: Timer? = null

    override fun onEvent(event: Event, data: Any?) {
        when (event) {
            Event.TIMER_TOGGLE_DOWN -> handleTimerToggle(false, data as Long)
            Event.TIMER_TOGGLE_UP -> handleTimerToggle(true, data as Long)
            Event.TIMER_SET_WCA_INSPECTION -> handleTimerSetWcaInspection(data as Boolean)
            Event.TIMER_CANCEL -> { /*TODO*/
            }
            else -> {
            }
        }
    }

    fun handleTimerSetWcaInspection(useInspection: Boolean) {
        this.useInspection = useInspection
    }

    fun handleTimerToggle(up: Boolean, time: Long) {
        if (up) { // releases the toggle "key" (any trigger)
            startTimer(time)
        } else {
            stopTimer(time)
        }
    }

    private fun startInspection() {
        inspectionRepeater = Timer()
        inspectionRepeater!!.scheduleAtFixedRate(object : TimerTask() {
            var seconds = 16
            override fun run() {
                seconds -= if (seconds >= 1) 1 else 0
                when (seconds) {
                    in 1..3 -> {
                        notifyListeners(Event.TIMER_UPDATE, "+$seconds")
                    }
                    0 -> {
                        notifyListeners(Event.TIMER_UPDATE, "DNF")
                    }
                    else -> {
                        notifyListeners(Event.TIMER_UPDATE, seconds.toString())
                    }
                }
            }
        }, 0L, 1000L)
        inspecting = true
    }

    private fun startTimer(time: Long) {
        fun start() {
            repeater = Timer()
            startTime = time
            repeater!!.scheduleAtFixedRate(object : TimerTask() {
                override fun run() {
                    elapsedTime = System.currentTimeMillis() - startTime
                    notifyListeners(Event.TIMER_UPDATE, elapsedTime.timestamp())
                }
            }, 0, 1)
            notifyListeners(Event.TIMER_STARTED)
            inspecting = false
        }

        //TODO: receive key code to increment field on any key up
        numUps++
        if (repeater == null && (numUps % 2 != 0)) {
            if (useInspection) {
                if (!inspecting) {
                    numUps++
                    startInspection()
                } else {
                    inspectionRepeater!!.cancel()
                    inspectionRepeater = null
                    start()
                }
            } else {
                start()
            }
        }
    }

    private fun stopTimer(time: Long) {
        if (repeater != null) {
            repeater!!.cancel()
            repeater = null
            notifyListeners(Event.TIMER_STOPPED, time - startTime)
            numUps = -1
        }
    }
}
