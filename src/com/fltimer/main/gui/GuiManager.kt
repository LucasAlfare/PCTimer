package com.fltimer.main.gui

import com.fltimer.main.Event
import com.fltimer.main.EventListener
import com.fltimer.main.Listenable
import com.fltimer.main.data.Solve
import com.fltimer.main.statistics.Statistic
import com.fltimer.main.statistics.StatisticId
import com.fltimer.main.statistics.StatisticResult
import com.fltimer.main.timestamp
import java.awt.FlowLayout
import java.awt.Font
import java.awt.event.KeyAdapter
import java.awt.event.KeyEvent
import javax.swing.JComponent
import javax.swing.JFrame
import javax.swing.JLabel

//compatibility only with Java/Swing. Android coming soon
interface GuiModel {

    fun getScramble(): JComponent
    fun getDisplay(): JComponent
}

class TestGui : JFrame(), GuiModel {

    var scramble: JLabel? = null
    var display: JLabel? = null

    init {
        setSize(600, 400)
        defaultCloseOperation = 3
        setLocationRelativeTo(null)
        layout = FlowLayout()

        display = JLabel(0L.timestamp())
        display!!.font = Font(display!!.font.name, Font.BOLD, 45)

        scramble = JLabel()

        add(scramble)
        add(display)
    }

    override fun getScramble(): JComponent {
        return scramble!!
    }

    override fun getDisplay(): JComponent {
        return display!!
    }
}

@Suppress("MemberVisibilityCanBePrivate")
class GuiManager : Listenable(), EventListener {

    //private val gui = TestGui()
    private val gui = Gui()
    private val details = Details()
    private var solvesRef: ArrayList<*>? = null

    private var tmpTime = 0L
    private var tmpScramble = ""
    private var tmpPenalty = ""

    init {
        setupDetails()
        setupGui()
    }

    private fun setupDetails() {
        details.sessionMean.addActionListener {
            notifyListeners(Event.STATISTIC_GET_RESULT_OF, arrayOf(StatisticId.MEAN, solvesRef))
        }
    }

    private fun setupGui() {
        gui.contentPane.addKeyListener(object : KeyAdapter() {
            override fun keyPressed(e: KeyEvent?) {
//                if (e!!.keyCode == KeyEvent.VK_SPACE) {
//                    notifyListeners(Event.TIMER_TOGGLE_DOWN, System.currentTimeMillis())
//                }

                notifyListeners(Event.TIMER_TOGGLE_DOWN, System.currentTimeMillis())
            }

            override fun keyReleased(e: KeyEvent?) {
                if (e!!.keyCode == KeyEvent.VK_SPACE) {
                    notifyListeners(Event.TIMER_TOGGLE_UP, System.currentTimeMillis())
                }
            }
        })

        gui.details.addActionListener {

            details.start()
        }

        gui.start()
    }

    override fun onEvent(event: Event, data: Any?) {
        when (event) {
            Event.TIMER_UPDATE -> handleTimerUpdate(data as Long)
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

    fun handleTimerUpdate(time: Long) {
        (gui.display as JLabel).text = time.timestamp()
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
        println("[DATA_RESPONSE] $solves")
        solvesRef = solves
    }

    fun handleDataChanged(solves: ArrayList<*>) {
        println("[DATA_CHANGED] $solves")
        solvesRef = solves
    }

    fun handleScrambleChanged(scramble: String) {
        tmpScramble = scramble
        (gui.scramble as JLabel).text = tmpScramble
        println("[SCRAMBLE_CHANGED] $tmpScramble")
    }
}
