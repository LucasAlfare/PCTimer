package com.fltimer.main.gui

import com.fltimer.main.Event
import com.fltimer.main.EventListener
import com.fltimer.main.Listenable
import com.fltimer.main.timestamp
import java.awt.FlowLayout
import java.awt.Font
import java.awt.event.KeyAdapter
import java.awt.event.KeyEvent
import javax.swing.JFrame
import javax.swing.JLabel

class TestGui : JFrame() {

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
}

class GuiManager : Listenable(), EventListener {

    private val gui = TestGui()
    private var solvesRef: ArrayList<*>? = null

    private var tmpTime = 0L
    private var tmpScramble = ""
    private var tmpPenalty = ""


    init {
        gui.addKeyListener(object : KeyAdapter() {
            override fun keyReleased(e: KeyEvent?) {
                if (e!!.keyCode == KeyEvent.VK_SPACE) {
                    notifyListeners(Event.TIMER_TOGGLE_UP, System.currentTimeMillis())
                } else if (e.keyCode == KeyEvent.VK_S) {
                    notifyListeners(Event.SCRAMBLE_REQUEST_NEW)
                }
            }
        })

        gui.isVisible = true
    }

    override fun onEvent(event: Event, data: Any?) {
        when (event) {
            Event.TIMER_UPDATE -> {
                handleTimerUpdate(data as Long)
            }

            Event.TIMER_STOPPED -> {
                handleTimerStopped(data as Long)
            }

            Event.DATA_RESPONSE -> {
                handleDataResponse(data as ArrayList<*>)
            }

            Event.DATA_CHANGED -> {
                handleDataChanged(data as ArrayList<*>)
            }

            Event.SCRAMBLE_CHANGED -> {
                handleScrambleChanged(data as String)
            }

            else -> {
            }
        }
    }

    fun handleTimerUpdate(time: Long) {
        gui.display!!.text = time.timestamp()
    }

    fun handleTimerStopped(time: Long) {
        tmpTime = time
        notifyListeners(Event.DATA_ITEM_CREATE, arrayOf(tmpTime, tmpScramble, tmpPenalty))
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
        gui.scramble!!.text = tmpScramble
        println("[SCRAMBLE_CHANGED] $tmpScramble")
    }
}