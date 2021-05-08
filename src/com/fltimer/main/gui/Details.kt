package com.fltimer.main.gui

import com.fltimer.main.gui.JRelativeLayout.Constraints.MATCH_PARENT
import com.fltimer.main.gui.JRelativeLayout.relativeConstraints
import java.awt.FlowLayout
import javax.swing.*

class Details : JFrame() {

    var sessionMean = JButton("session mean: - -")
    var sessionAvg = JButton("session avg: - -")
    var best = JButton("best: - -")
    var worst = JButton("worst: - -")
    var best5 = JButton("best5: - -")
    var best12 = JButton("best12: - -")
    var best50 = JButton("best50: - -")
    var best100 = JButton("best100: - -")

    var nSolves = JLabel()
    var list = JList<String>()

    private val listScrollPane = JScrollPane()
    private val titleBorder = BorderFactory.createTitledBorder("")

    init {
        setSize(300, 500)
        defaultCloseOperation = DISPOSE_ON_CLOSE
        setLocationRelativeTo(null)

        listScrollPane.border = titleBorder
        setNumSolves(0)
        listScrollPane.setViewportView(list)

        layout = JRelativeLayout()
        add(sessionMean, relativeConstraints().parentStart().parentTop().marginTop(16).marginStart(8))
        add(sessionAvg, relativeConstraints().parentStart().below(sessionMean).marginTop(8).marginStart(8))
        add(best, relativeConstraints().parentStart().below(sessionAvg).marginTop(8).marginStart(8))
        add(worst, relativeConstraints().parentStart().below(best).marginTop(8).marginStart(8))
        add(best5, relativeConstraints().parentStart().below(worst).marginTop(8).marginStart(8))
        add(best12, relativeConstraints().parentStart().below(best5).marginTop(8).marginStart(8))
        add(best50, relativeConstraints().parentStart().below(best12).marginTop(8).marginStart(8))
        add(best100, relativeConstraints().parentStart().below(best50).marginTop(8).marginStart(8))
        add(
            listScrollPane,
            relativeConstraints()
                .parentBottom()
                .percentileWidth(MATCH_PARENT)
                .marginStart(16)
                .marginEnd(16)
                .marginBottom(16)
        )
    }

    fun start() {
        isVisible = true
    }

    fun setNumSolves(n: Int) {
        titleBorder.title = "${if (n == 0) "--" else n} solve(s)"
    }
}
