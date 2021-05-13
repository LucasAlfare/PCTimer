package com.fltimer.gui

import com.fltimer.gui.layout.JRelativeLayout
import com.fltimer.gui.layout.JRelativeLayout.Constraints.MATCH_PARENT
import com.fltimer.gui.layout.JRelativeLayout.relativeConstraints
import javax.swing.*

class Details : JFrame() {

    val sessionMean = JButton("session mean: - -")
    val sessionAvg = JButton("session avg: - -")
    val best = JButton("best: - -")
    val worst = JButton("worst: - -")
    val best5 = JButton("best5: - -")
    val best12 = JButton("best12: - -")
    val best50 = JButton("best50: - -")
    val best100 = JButton("best100: - -")

    var resultArea = JTextArea()

    private val titleBorder = BorderFactory.createTitledBorder("")

    init {
        setSize(300, 500)
        defaultCloseOperation = DISPOSE_ON_CLOSE
        setLocationRelativeTo(null)

        resultArea.border = titleBorder
        setNumSolves(0)

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
            resultArea,
            relativeConstraints()
                .parentBottom()
                .percentileWidth(MATCH_PARENT)
                .percentileHeight(30.0)
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
