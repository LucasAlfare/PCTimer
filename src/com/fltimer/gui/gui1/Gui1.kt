package com.fltimer.gui.gui1

import com.fltimer.gui.model.GuiAdapter
import com.fltimer.gui.swing.layout.JRelativeLayout
import com.fltimer.gui.swing.layout.JRelativeLayout.relativeConstraints
import com.fltimer.timestamp
import java.awt.Container
import java.awt.Font
import java.awt.event.KeyAdapter
import java.awt.event.KeyEvent
import javax.swing.JButton
import javax.swing.JFrame
import javax.swing.JLabel

/**
 * A example of main class holding code for design.
 */
class Gui1 : JFrame() {

    var scramble = JLabel("loading...")
    var display = JLabel(0L.timestamp())
    var details = JButton("details")
    var options = JButton("options")

    init {
        setSize(400, 600)
        defaultCloseOperation = 3

        scramble.font = Font(scramble.font.name, scramble.font.style, 16)
        display.font = Font(scramble.font.name, scramble.font.style, 70)
        details.font = Font(scramble.font.name, scramble.font.style, 24)
        options.font = Font(scramble.font.name, scramble.font.style, 24)

        this.isFocusable = false
        scramble.isFocusable = false
        display.isFocusable = false
        details.isFocusable = false
        options.isFocusable = false
        this.contentPane.isFocusable = true

        layout = JRelativeLayout()
        add(scramble, relativeConstraints().parentTop().centerHorizontal().marginTop(16))
        add(display, relativeConstraints().centerHorizontal().centerVertical())
        add(details, relativeConstraints().parentStart().parentBottom().marginStart(16).marginBottom(16))
        add(options, relativeConstraints().parentEnd().parentBottom().marginEnd(16).marginBottom(16))

        setLocationRelativeTo(null)
    }

    fun start() {
        //EventQueue.invokeLater { this.isVisible = true }
        this.isVisible = true
    }
}

/**
 * Gui1 adapter class. This is extending GuiAdapter in order to fill its methods
 * with logic that controls the fields from the Gui1 class.
 */
class Gui1Adapter : GuiAdapter() {

    private lateinit var gui1: Gui1

    init {
        initGui()
    }

    override fun setupInteractionListener(onDown: () -> Unit, onUp: () -> Unit) {
        (root!! as Container).addKeyListener(object : KeyAdapter() {
            override fun keyPressed(e: KeyEvent?) {
                if (e?.keyCode == KeyEvent.VK_SPACE) {
                    /**
                     * any application logic is written here, but in the GuiManager class.
                     */
                    onDown()
                }
            }

            override fun keyReleased(e: KeyEvent?) {
                if (e?.keyCode == KeyEvent.VK_SPACE) {
                    /**
                     * any application logic is written here, but in the GuiManager class.
                     */
                    onUp()
                }
            }
        })
    }

    override fun setupCancelInteraction(onCancel: () -> Unit) {
        (root!! as Container).addKeyListener(object : KeyAdapter() {
            override fun keyReleased(e: KeyEvent?) {
                if (e?.keyCode == KeyEvent.VK_ESCAPE) {
                    onCancel()
                }
            }
        })
    }

    override fun initGui() {
        gui1 = Gui1()
        initRoot()
        initScramble()
        initDisplay()
        initAuxiliaryPane()
    }

    override fun initRoot() {
        root = gui1.contentPane
    }

    override fun initScramble() {
        scramble = gui1.scramble
    }

    override fun initDisplay() {
        display = gui1.display
    }

    override fun initAuxiliaryPane() {
        // pass?
    }

    override fun start() {
        gui1.start()
    }
}
