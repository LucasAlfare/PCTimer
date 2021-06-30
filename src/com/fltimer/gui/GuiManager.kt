package com.fltimer.gui

import com.fltimer.Event
import com.fltimer.EventListener
import com.fltimer.Listenable
import com.fltimer.data.Penalty
import com.fltimer.data.Solves
import com.fltimer.gui.light.LightWeightGuiAdapter
import com.fltimer.statistics.getAllStatisticsFor
import com.fltimer.toStatisticData

@Suppress("MemberVisibilityCanBePrivate")
class GuiManager : Listenable(), EventListener {

    /**
     * The main GuiAdapter instance of the GuiManager.
     * The field is typed to the abstract type GuiAdapter in order to leave the user
     * switch its time over the time.
     *
     * For this first implementations experiments, the main gui adapter is set
     * to a instance of a adapter made for a test.
     *
     * TODO: restrict this switching only for development level?
     */
    private var abstractGuiAdapter: AbstractGuiAdapter = LightWeightGuiAdapter()

    private var solvesRef: Solves? = null

    private var tmpTime = 0L
    private var tmpScramble = ""
    private var tmpPenalty = Penalty.OK

    init {
        abstractGuiAdapter.setInteractionListener(
            onDown = { notifyListeners(Event.TIMER_TOGGLE_DOWN, System.currentTimeMillis()) },
            onUp = { notifyListeners(Event.TIMER_TOGGLE_UP, System.currentTimeMillis()) })

        abstractGuiAdapter.setCancelAction {
            notifyListeners(Event.TIMER_CANCEL)
        }

        abstractGuiAdapter.setDeleteSelectedAction { selectedIndex ->
            val id = solvesRef!!.keys.toTypedArray()[selectedIndex]
            notifyListeners(Event.DATA_ITEM_REMOVE, id)
        }

        abstractGuiAdapter.setClearAction {
            notifyListeners(Event.DATA_CLEAR)
        }

        abstractGuiAdapter.start()
    }

    override fun onEvent(event: Event, data: Any?) = when (event) {
        Event.TIMER_UPDATE -> handleDisplayUpdate(data as String)
        Event.TIMER_STOPPED -> handleTimerStopped(data as Long)
        Event.DATA_CHANGED -> handleDataChanged(data as Solves)
        Event.SCRAMBLE_CHANGED -> handleScrambleChanged(data as String)
        Event.DATA_PENALTY_UPDATE -> tmpPenalty = data as Penalty

        else -> {
        }
    }

    private fun handleDataChanged(solves: Solves) {
        solvesRef = solves
        abstractGuiAdapter.setSolvesListData(solvesRef!!)
        abstractGuiAdapter.setStatistics(solvesRef!!, getAllStatisticsFor(solvesRef!!.toStatisticData()))
    }

    private fun handleDisplayUpdate(value: String) {
        abstractGuiAdapter.setDisplayText(value)
    }

    fun handleTimerStopped(time: Long) {
        tmpTime = time
        notifyListeners(
            Event.DATA_ITEM_CREATE, arrayOf(
                tmpTime,
                tmpScramble,
                tmpPenalty
            )
        )
        notifyListeners(Event.SCRAMBLE_REQUEST_NEW)
    }

    fun handleScrambleChanged(scramble: String) {
        tmpScramble = scramble
        abstractGuiAdapter.setScrambleText(tmpScramble)
    }
}
