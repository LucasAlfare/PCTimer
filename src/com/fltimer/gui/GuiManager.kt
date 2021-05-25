package com.fltimer.gui

import com.fltimer.Event
import com.fltimer.EventListener
import com.fltimer.Listenable
import com.fltimer.data.Penalty
import com.fltimer.data.Solves
import com.fltimer.gui.gui2.Gui2Adapter
import com.fltimer.gui.model.GuiAdapter
import com.fltimer.statistics.StatisticResult

@Suppress("MemberVisibilityCanBePrivate")
class GuiManager : Listenable(), EventListener {

    /**
     * The main GuiAdapter instance of the GuiManager.
     * The field is typed to the abstract type GuiAdapter in order to leave the user
     * switch its time over the time.
     *
     * TODO: restrict this switching only for development level?
     */
    private lateinit var guiAdapter: GuiAdapter

    private var solvesRef: Solves? = null

    private var tmpTime = 0L
    private var tmpScramble = ""
    private var tmpPenalty = Penalty.OK

    init {
        setupGui()
    }

    private fun setupGui() {
        /**
         * For this first implementations experiments, the main gui adapter is set
         * to a instance of a adapter made for a test.
         */
        guiAdapter = Gui2Adapter()

        guiAdapter.setInteractionListener(
            onDown = { notifyListeners(Event.TIMER_TOGGLE_DOWN, System.currentTimeMillis()) },
            onUp = { notifyListeners(Event.TIMER_TOGGLE_UP, System.currentTimeMillis()) })

        guiAdapter.setCancelAction {
            notifyListeners(Event.TIMER_CANCEL)
        }

        guiAdapter.setDeleteSelectedAction {
            val id = solvesRef!!.keys.toTypedArray()[it]
            notifyListeners(Event.DATA_ITEM_REMOVE, id)
        }

        guiAdapter.setClearAction {
            notifyListeners(Event.DATA_CLEAR)
        }

        guiAdapter.start()
    }

    override fun onEvent(event: Event, data: Any?) {
        when (event) {
            Event.TIMER_UPDATE -> handleDisplayUpdate(data as String)
            Event.TIMER_STOPPED -> handleTimerStopped(data as Long)
            Event.DATA_RESPONSE -> solvesRef = data as Solves
            Event.DATA_CHANGED -> {
                handleDataChanged(data as Solves)
            }
            Event.SCRAMBLE_CHANGED -> handleScrambleChanged(data as String)
            Event.STATISTIC_RESPONSE_RESULT_OF -> handleStatisticResponseResultOf(data as StatisticResult)
            Event.DATA_PENALTY_UPDATE -> tmpPenalty = data as Penalty

            else -> {
            }
        }
    }

    private fun handleDataChanged(solves: Solves) {
        solvesRef = solves
        guiAdapter.setSolvesListData(solvesRef!!)
    }

    private fun handleDisplayUpdate(value: String) {
        guiAdapter.setDisplayText(value)
    }

    private fun handleStatisticResponseResultOf(statisticResult: StatisticResult) {
        println(statisticResult)
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
        guiAdapter.setScrambleText(tmpScramble)
    }
}
