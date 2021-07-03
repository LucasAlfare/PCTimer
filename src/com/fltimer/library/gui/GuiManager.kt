package com.fltimer.library.gui

import com.fltimer.library.data.Penalty
import com.fltimer.library.data.Solves
import com.fltimer.library.gui.light.LightWeightGuiAdapter
import com.fltimer.library.statistics.getAllStatisticsFor
import com.fltimer.library.toStatisticData

@Suppress("MemberVisibilityCanBePrivate")
class GuiManager : com.fltimer.library.Listenable(), com.fltimer.library.EventListener {

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
    private var abstractGuiAdapter: LightWeightGuiAdapter = LightWeightGuiAdapter()

    private var solvesRef: Solves? = null

    private var tmpTime = 0L
    private var tmpScramble = ""
    private var tmpPenalty = Penalty.OK

    init {
        abstractGuiAdapter.setInteractionListener(
            onDown = { notifyListeners(com.fltimer.library.Event.TIMER_TOGGLE_DOWN, System.currentTimeMillis()) },
            onUp = { notifyListeners(com.fltimer.library.Event.TIMER_TOGGLE_UP, System.currentTimeMillis()) })

        abstractGuiAdapter.setCancelAction {
            notifyListeners(com.fltimer.library.Event.TIMER_CANCEL)
        }

        abstractGuiAdapter.setDeleteSelectedAction { selectedIndex ->
            val id = solvesRef!!.keys.toTypedArray()[selectedIndex]
            notifyListeners(com.fltimer.library.Event.DATA_ITEM_REMOVE, id)
        }

        abstractGuiAdapter.setClearAction {
            notifyListeners(com.fltimer.library.Event.DATA_CLEAR)
        }

        abstractGuiAdapter.start()
    }

    override fun onEvent(event: com.fltimer.library.Event, data: Any?) = when (event) {
        com.fltimer.library.Event.TIMER_UPDATE -> handleDisplayUpdate(data as String)
        com.fltimer.library.Event.TIMER_STOPPED -> handleTimerStopped(data as Long)
        com.fltimer.library.Event.DATA_CHANGED -> handleDataChanged(data as Solves)
        com.fltimer.library.Event.SCRAMBLE_CHANGED -> handleScrambleChanged(data as String)
        com.fltimer.library.Event.DATA_PENALTY_UPDATE -> tmpPenalty = data as Penalty

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
            com.fltimer.library.Event.DATA_ITEM_CREATE, arrayOf(
                tmpTime,
                tmpScramble,
                tmpPenalty
            )
        )
        notifyListeners(com.fltimer.library.Event.SCRAMBLE_REQUEST_NEW)
    }

    fun handleScrambleChanged(scramble: String) {
        tmpScramble = scramble
        abstractGuiAdapter.setScrambleText(tmpScramble)
    }
}
