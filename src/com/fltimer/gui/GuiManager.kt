package com.fltimer.gui

import com.fltimer.Event
import com.fltimer.EventListener
import com.fltimer.Listenable
import com.fltimer.gui.model.GuiAdapter
import com.fltimer.statistics.StatisticResult
import javax.swing.JLabel

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

    private var solvesRef: ArrayList<*>? = null

    private var tmpTime = 0L
    private var tmpScramble = ""
    private var tmpPenalty = ""

    init {
        setupGui()
    }

    private fun setupGui() {
        /**
         * For this first implementations, the main gui adapter is set to a instance of
         * a adapter made for a test.
         */
        guiAdapter = Gui1Adapter()

        guiAdapter.setupInteractionListener(
            { notifyListeners(Event.TIMER_TOGGLE_DOWN, System.currentTimeMillis()) },
            { notifyListeners(Event.TIMER_TOGGLE_UP, System.currentTimeMillis()) })

        guiAdapter.setupCancelInteraction {
            notifyListeners(Event.TIMER_CANCEL)
        }

        guiAdapter.start()
    }


    override fun onEvent(event: Event, data: Any?) {
        when (event) {
            Event.TIMER_UPDATE -> handleTimerUpdate(data as String)
            Event.TIMER_STOPPED -> handleTimerStopped(data as Long)
            Event.DATA_RESPONSE -> handleDataResponse(data as ArrayList<*>)
            Event.DATA_CHANGED -> handleDataChanged(data as ArrayList<*>)
            Event.SCRAMBLE_CHANGED -> handleScrambleChanged(data as String)
            Event.STATISTIC_RESPONSE_RESULT_OF -> handleStatisticResponseResultOf(data as StatisticResult)

            else -> {
            }
        }
    }

    private fun handleStatisticResponseResultOf(statisticResult: StatisticResult) {
        println(statisticResult)
    }

    fun handleTimerUpdate(value: String) {
        (guiAdapter.display as JLabel).text = value
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

    fun handleDataResponse(solves: ArrayList<*>) {
        solvesRef = solves
    }

    fun handleDataChanged(solves: ArrayList<*>) {
        solvesRef = solves
    }

    fun handleScrambleChanged(scramble: String) {
        tmpScramble = scramble
        (guiAdapter.scramble as JLabel).text = tmpScramble
    }
}
