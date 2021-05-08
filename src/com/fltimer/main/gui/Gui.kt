package com.fltimer.main.gui

import com.fltimer.main.gui.JRelativeLayout.relativeConstraints
import com.fltimer.main.timestamp
import java.awt.EventQueue
import java.awt.Font
import javax.swing.JButton
import javax.swing.JFrame
import javax.swing.JLabel

class Gui : JFrame() {

    var scramble = JLabel("loading...")
    var display = JLabel(0L.timestamp())
    var details = JButton("details")
    var stats = JButton("details")

    init {
        setSize(400, 600)
        defaultCloseOperation = 3

        scramble.font = Font(scramble.font.name, scramble.font.style, 24)
        display.font = Font(scramble.font.name, scramble.font.style, 70)
        details.font = Font(scramble.font.name, scramble.font.style, 24)
        stats.font = Font(scramble.font.name, scramble.font.style, 24)

        this.isFocusable = false
        scramble.isFocusable = false
        display.isFocusable = false
        details.isFocusable = false
        stats.isFocusable = false
        this.contentPane.isFocusable = true

        layout = JRelativeLayout()
        add(scramble, relativeConstraints().parentTop().centerHorizontal().marginTop(16))
        add(display, relativeConstraints().centerHorizontal().centerVertical())
        add(details, relativeConstraints().parentStart().parentBottom().marginStart(16).marginBottom(16))
        add(stats, relativeConstraints().parentEnd().parentBottom().marginEnd(16).marginBottom(16))

        setLocationRelativeTo(null)
    }

    fun start() {
        EventQueue.invokeLater { this.isVisible = true }
    }
}