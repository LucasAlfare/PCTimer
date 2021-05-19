package com.fltimer.gui

import com.fltimer.Event
import com.fltimer.EventListener
import com.fltimer.Listenable
import com.fltimer.data.Penalty
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
    private var tmpPenalty = Penalty.OK

    init {
        setupGui()
    }

    private fun setupGui() {
        /**
         * For this first implementations experiments, the main gui adapter is set
         * to a instance of a adapter made for a test.
         */
        guiAdapter = Gui1Adapter()

        guiAdapter.setupInteractionListener(
            onDown = { notifyListeners(Event.TIMER_TOGGLE_DOWN, System.currentTimeMillis()) },
            onUp = { notifyListeners(Event.TIMER_TOGGLE_UP, System.currentTimeMillis()) })

        guiAdapter.setupCancelInteraction {
            notifyListeners(Event.TIMER_CANCEL)
        }

        guiAdapter.start()
    }


    override fun onEvent(event: Event, data: Any?) {
        when (event) {
            Event.TIMER_UPDATE -> handleDisplayUpdate(data as String)
            Event.TIMER_STOPPED -> handleTimerStopped(data as Long)
            Event.DATA_RESPONSE -> solvesRef = data as ArrayList<*>
            Event.DATA_CHANGED -> {
                solvesRef = data as ArrayList<*>
                println(solvesRef)
            }
            Event.SCRAMBLE_CHANGED -> handleScrambleChanged(data as String)
            Event.STATISTIC_RESPONSE_RESULT_OF -> handleStatisticResponseResultOf(data as StatisticResult)
            Event.DATA_PENALTY_UPDATE -> tmpPenalty = data as Penalty

            else -> {
            }
        }
    }

    private fun handleDisplayUpdate(value: String) {
        (guiAdapter.display as JLabel).text = value
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
        (guiAdapter.scramble as JLabel).text = tmpScramble
    }
}
