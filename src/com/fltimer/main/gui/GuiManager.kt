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

    var display: JLabel? = null

    init {
        setSize(600, 400)
        defaultCloseOperation = 3
        setLocationRelativeTo(null)
        layout = FlowLayout()
        display = JLabel(0L.timestamp())
        display!!.font = Font(display!!.font.name, Font.BOLD, 45)
        add(display)
    }
}

class GuiManager : Listenable(), EventListener {

    private val gui = TestGui()

    init {
        gui.addKeyListener(object : KeyAdapter() {
            override fun keyReleased(e: KeyEvent?) {
                if (e!!.keyCode == KeyEvent.VK_SPACE) {
                    notifyListeners(Event.TIMER_TOGGLE_UP, System.currentTimeMillis())
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

            Event.DATA_RESPONSE -> {
                handleDataResponse(data as ArrayList<*>)
            }

            Event.DATA_CHANGED -> {
                handleDataChanged(data as ArrayList<*>)
            }
            else -> {}
        }
    }

    fun handleTimerUpdate(time: Long) {
        gui.display!!.text = time.timestamp()
    }

    fun handleDataResponse(solves: ArrayList<*>) {
        println("[DATA_RESPONSE] $solves")
    }

    fun handleDataChanged(solves: ArrayList<*>) {
        println("[DATA_CHANGED] $solves")
    }
}
