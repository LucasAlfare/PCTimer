package com.fltimer.gui.gui2

import com.fltimer.data.Solves
import com.fltimer.gui.model.GuiAdapter
import com.fltimer.timestamp
import java.awt.Container
import java.awt.Font
import java.awt.event.KeyAdapter
import java.awt.event.KeyEvent
import javax.swing.*

class Gui2 : JFrame() {

    init {
        initComponents()
        setLocationRelativeTo(null)
        contentPane.isFocusable = true
        contentPane.requestFocus()
    }

    private fun initComponents() {
        jScrollPane1 = JScrollPane()
        solves = JList()
        editSelected = JButton()
        clear = JButton()
        deleteSelected = JButton()
        addSolve = JButton()
        scramble = JLabel()
        display = JLabel()
        defaultCloseOperation = EXIT_ON_CLOSE
        solves!!.border = BorderFactory.createTitledBorder("Solves: - -")
        solves!!.model = object : AbstractListModel<String?>() {
            var strings = arrayOf("Item 1", "Item 2", "Item 3", "Item 4", "Item 5")
            override fun getSize(): Int {
                return strings.size
            }

            override fun getElementAt(i: Int): String {
                return strings[i]
            }
        }
        solves!!.isFocusable = false
        jScrollPane1!!.setViewportView(solves)
        editSelected!!.text = "Edit selected..."
        editSelected!!.isFocusable = false
        clear!!.text = "Clear"
        clear!!.isFocusable = false
        deleteSelected!!.text = "Delete selected..."
        deleteSelected!!.isFocusable = false
        addSolve!!.text = "Add solve..."
        addSolve!!.isFocusable = false
        scramble!!.text = "jLabel2"
        scramble!!.isFocusable = false
        scramble!!.font = Font("Courier New", 0, 18) // NOI18N
        scramble!!.horizontalAlignment = SwingConstants.CENTER
        display!!.text = 0L.timestamp()
        display!!.isFocusable = false
        display!!.font = Font("Impact", Font.PLAIN, 70) // NOI18N
        display!!.horizontalAlignment = SwingConstants.CENTER
        val layout = GroupLayout(contentPane)
        contentPane.layout = layout
        layout.setHorizontalGroup(
            layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                .addGroup(
                    layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(
                            layout.createParallelGroup(GroupLayout.Alignment.LEADING, false)
                                .addComponent(
                                    clear,
                                    GroupLayout.DEFAULT_SIZE,
                                    GroupLayout.DEFAULT_SIZE,
                                    Short.MAX_VALUE.toInt()
                                )
                                .addComponent(deleteSelected, GroupLayout.DEFAULT_SIZE, 184, Short.MAX_VALUE.toInt())
                                .addComponent(
                                    addSolve,
                                    GroupLayout.DEFAULT_SIZE,
                                    GroupLayout.DEFAULT_SIZE,
                                    Short.MAX_VALUE.toInt()
                                )
                                .addComponent(
                                    editSelected,
                                    GroupLayout.DEFAULT_SIZE,
                                    GroupLayout.DEFAULT_SIZE,
                                    Short.MAX_VALUE.toInt()
                                )
                                .addComponent(jScrollPane1)
                        )
                        .addGap(18, 18, 18)
                        .addGroup(
                            layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                .addComponent(scramble, GroupLayout.DEFAULT_SIZE, 605, Short.MAX_VALUE.toInt())
                                .addComponent(
                                    display,
                                    GroupLayout.DEFAULT_SIZE,
                                    GroupLayout.DEFAULT_SIZE,
                                    Short.MAX_VALUE.toInt()
                                )
                        )
                        .addContainerGap()
                )
        )
        layout.setVerticalGroup(
            layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                .addGroup(
                    layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(
                            layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                .addComponent(jScrollPane1, GroupLayout.DEFAULT_SIZE, 482, Short.MAX_VALUE.toInt())
                                .addGroup(
                                    layout.createSequentialGroup()
                                        .addComponent(scramble)
                                        .addGap(18, 18, 18)
                                        .addComponent(
                                            display,
                                            GroupLayout.DEFAULT_SIZE,
                                            GroupLayout.DEFAULT_SIZE,
                                            Short.MAX_VALUE.toInt()
                                        )
                                )
                        )
                        .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(editSelected)
                        .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(addSolve)
                        .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(deleteSelected)
                        .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(clear)
                        .addContainerGap()
                )
        )
        pack()
    }

    var addSolve: JButton? = null
    var clear: JButton? = null
    var deleteSelected: JButton? = null
    var display: JLabel? = null
    var editSelected: JButton? = null
    var jScrollPane1: JScrollPane? = null
    var scramble: JLabel? = null
    var solves: JList<String>? = null

    fun start() {
        isVisible = true
    }
}

class Gui2Adapter : GuiAdapter() {

    private lateinit var gui2: Gui2

    init {
        initGui()
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
        gui2.deleteSelected!!.addActionListener {
            val l = solvesList as JList<String>
            if (l.selectedIndex != -1) {
                val dialogResult = JOptionPane.showConfirmDialog(
                    gui2,
                    "Delete the selected solve?"
                )
                if (dialogResult == JOptionPane.YES_OPTION) {
                    action(l.selectedIndex)
                }
            }
        }
    }

    override fun setClearAction(action: () -> Unit) {
        gui2.clear!!.addActionListener {
            val dialogResult = JOptionPane.showConfirmDialog(
                gui2,
                "Clear all solves?"
            )
            if (dialogResult == JOptionPane.YES_OPTION) {
                action()
            }
        }
    }

    override fun setScrambleText(text: String) {
        (this.scramble!! as JLabel).text = text
    }

    override fun setDisplayText(text: String) {
        (this.display!! as JLabel).text = text
    }

    override fun setSolvesListData(data: Any) {
        val model = (solvesList!! as JList<*>).model
        (model as DefaultListModel<String>).clear()
        (data as Solves).forEach { key, value ->
            model.addElement("${model.size() + 1}) ${value.time.timestamp()}")
        }
    }

    override fun initGui() {
        gui2 = Gui2()
        initRoot()
        initScramble()
        initDisplay()
        initSolvesList()
        initStatisticList()
        initAuxiliaryPane()
    }

    override fun initRoot() {
        root = gui2.contentPane
    }

    override fun initScramble() {
        scramble = gui2.scramble
    }

    override fun initDisplay() {
        display = gui2.display
    }

    override fun initSolvesList() {
        solvesList = gui2.solves
        (solvesList as JList<*>).model = DefaultListModel()
    }

    override fun initStatisticList() {
        // pass...
    }

    override fun initAuxiliaryPane() {
        // pass?
    }

    override fun start() {
        gui2.start()
    }
}