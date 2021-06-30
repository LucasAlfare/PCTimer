package com.fltimer.gui.light

import com.fltimer.data.Solves
import com.fltimer.gui.AbstractGuiAdapter
import com.fltimer.gui.swing.layout.JRelativeLayout
import com.fltimer.gui.swing.layout.JRelativeLayout.Constraints.MATCH_PARENT
import com.fltimer.gui.swing.layout.JRelativeLayout.relativeConstraints
import com.fltimer.statistics.StatisticResult
import com.fltimer.timestamp
import java.awt.Container
import java.awt.event.KeyAdapter
import java.awt.event.KeyEvent
import javax.swing.*

class LightWeightGui : JFrame() {

    var scramble: JLabel? = null
    var display: JLabel? = null
    var solves: JTextArea? = null
    var statistics: JTextArea? = null

    init {
        setSize(800, 600)
        defaultCloseOperation = EXIT_ON_CLOSE

        scramble = JLabel("loading...")
        scramble!!.horizontalAlignment = SwingConstants.CENTER
        scramble!!.verticalAlignment = SwingConstants.TOP
        scramble!!.isFocusable = false

        display = JLabel(0L.timestamp())
        display!!.horizontalAlignment = SwingConstants.CENTER
        display!!.verticalAlignment = SwingConstants.CENTER
        display!!.isFocusable = false

        solves = JTextArea()
        solves!!.border = BorderFactory.createTitledBorder("Solves")
        solves!!.isFocusable = false

        statistics = JTextArea()
        statistics!!.border = BorderFactory.createTitledBorder("Statistics")
        statistics!!.isFocusable = false

        contentPane.isFocusable = true
        contentPane.requestFocus()

        layout = JRelativeLayout()
        add(
            solves,
            relativeConstraints().parentStart().parentTop().percentileHeight(MATCH_PARENT).percentileWidth(25.0)
        )
        add(scramble, relativeConstraints().centerHorizontal().parentTop())
        add(display, relativeConstraints().centerHorizontal().centerVertical())
        add(
            statistics,
            relativeConstraints().parentEnd().parentTop().percentileHeight(MATCH_PARENT).percentileWidth(25.0)
        )
    }

    fun start() {
        isVisible = true
    }
}

class LightWeightGuiAdapter : AbstractGuiAdapter() {

    private val gui = LightWeightGui()

    init {
        initGui()
        println("constructor")
    }

    override fun setInteractionListener(onDown: () -> Unit, onUp: () -> Unit) {
        (root!! as Container).addKeyListener(object : KeyAdapter() {
            override fun keyPressed(e: KeyEvent?) {
                if (e?.keyCode == KeyEvent.VK_SPACE) {
                    /**
                     * any application logic is not written here, but in the GuiManager class.
                     */
                    onDown()
                    val l = display as JLabel
                    l.font = l.font.deriveFont(55f)
                }
            }

            override fun keyReleased(e: KeyEvent?) {
                if (e?.keyCode == KeyEvent.VK_SPACE) {
                    /**
                     * any application logic is not written here, but in the GuiManager class.
                     */
                    onUp()
                    val l = display as JLabel
                    l.font = l.font.deriveFont(70f)
                }
            }
        })
    }

    override fun setCancelAction(onCancel: () -> Unit) {
        (root!! as Container).addKeyListener(object : KeyAdapter() {
            override fun keyReleased(e: KeyEvent?) {
                if (e?.keyCode == KeyEvent.VK_ESCAPE) {
                    onCancel()
                }
            }
        })
    }

    override fun setEditSelectedSolveAction(action: () -> Unit) {

    }

    override fun setAddSolveAction(action: () -> Unit) {

    }

    override fun setDeleteSelectedAction(action: (Int) -> Unit) {

    }

    override fun setClearAction(action: () -> Unit) {

    }

    override fun setStatistics(originalData: Solves, resultsData: ArrayList<StatisticResult>) {
        if (resultsData.size > 0) {
            var s = ""
            resultsData.forEach { result ->
                s += "${result.statisticId}: ${result.result.timestamp()}\n"
            }
            (statisticsList as JTextArea).text = s
        }
    }

    override fun setScrambleText(text: String) {
        (this.scramble!! as JLabel).text = text
    }

    override fun setDisplayText(text: String) {
        (this.display!! as JLabel).text = text
    }

    override fun setSolvesListData(solves: Any) {
        var s = ""
        var counter = 1
        (solves as Solves).forEach { key, value ->
            s += "$counter) ${value.time.timestamp()}\n"
            counter++
        }
        (solvesList as JTextArea).text = s
    }

    override fun initGui() {
        initRoot()
        initScramble()
        initDisplay()
        initSolvesList()
        initStatisticList()
    }

    override fun initRoot() {
        this.root = gui.contentPane
    }

    override fun initScramble() {
        this.scramble = gui.scramble
    }

    override fun initDisplay() {
        this.display = gui.display
    }

    override fun initSolvesList() {
        this.solvesList = gui.solves
    }

    override fun initStatisticList() {
        this.statisticsList = gui.statistics
    }

    override fun initAuxiliaryPane() {

    }

    override fun start() {
        gui.isVisible = true
    }
}