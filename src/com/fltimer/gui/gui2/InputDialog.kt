package com.fltimer.gui.gui2

import com.fltimer.data.Penalty
import com.fltimer.data.Solve
import javax.swing.*

class InputDialog(solve: Solve) : JFrame() {

    init {
        initComponents()
    }

    fun setCreateDataAction(action: (Solve) -> Unit) {
        commit!!.addActionListener {
            if (time!!.text.isNotEmpty() && scramble!!.text.isNotEmpty()) {
                val solve = Solve(
                    time = 99999L,
                    scramble = scramble!!.text!!,
                    penalty = penalties!!.selectedItem as Penalty
                )
                action(solve)
            }
        }
    }

    fun setUpdateDataAction(solve: Solve, action: (Solve) -> Unit) {
        commit!!.addActionListener {
            if (time!!.text.isNotEmpty() && scramble!!.text.isNotEmpty()) {
                solve.time = 99999L
                solve.scramble = scramble!!.text!!
                solve.penalty = penalties!!.selectedItem as Penalty
                action(solve)
            }
        }
    }

    private fun initComponents() {
        jLabel1 = JLabel()
        time = JTextField()
        jLabel2 = JLabel()
        scramble = JTextField()
        jLabel3 = JLabel()
        penalties = JComboBox()
        jLabel4 = JLabel()
        jScrollPane1 = JScrollPane()
        comments = JTextArea()
        commit = JButton()
        defaultCloseOperation = EXIT_ON_CLOSE
        jLabel1!!.text = "Time:"
        time!!.text = "xx:xx.xxx"
        jLabel2!!.text = "Scramble"
        scramble!!.text = "- -"
        jLabel3!!.text = "Penalty"
        penalties!!.model =
            DefaultComboBoxModel(arrayOf(Penalty.OK, Penalty.PLUS_TWO, Penalty.DNF))
        jLabel4!!.text = "Comments:"
        jLabel4!!.isEnabled = false
        comments!!.columns = 20
        comments!!.rows = 5
        comments!!.isEnabled = false
        jScrollPane1!!.setViewportView(comments)
        commit!!.text = "Commit"
        val layout = GroupLayout(contentPane)
        contentPane.layout = layout
        layout.setHorizontalGroup(
            layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                .addGroup(
                    layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(
                            layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                .addGroup(
                                    layout.createSequentialGroup()
                                        .addComponent(jLabel3)
                                        .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(penalties, 0, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE.toInt())
                                )
                                .addComponent(
                                    jLabel4,
                                    GroupLayout.DEFAULT_SIZE,
                                    GroupLayout.DEFAULT_SIZE,
                                    Short.MAX_VALUE.toInt()
                                )
                                .addGroup(
                                    layout.createSequentialGroup()
                                        .addGroup(
                                            layout.createParallelGroup(GroupLayout.Alignment.TRAILING, false)
                                                .addComponent(
                                                    jLabel1,
                                                    GroupLayout.DEFAULT_SIZE,
                                                    GroupLayout.DEFAULT_SIZE,
                                                    Short.MAX_VALUE.toInt()
                                                )
                                                .addComponent(
                                                    jLabel2,
                                                    GroupLayout.DEFAULT_SIZE,
                                                    GroupLayout.DEFAULT_SIZE,
                                                    Short.MAX_VALUE.toInt()
                                                )
                                        )
                                        .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                        .addGroup(
                                            layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                                .addComponent(scramble)
                                                .addComponent(time)
                                        )
                                )
                                .addGroup(
                                    layout.createSequentialGroup()
                                        .addComponent(
                                            jScrollPane1,
                                            GroupLayout.PREFERRED_SIZE,
                                            GroupLayout.DEFAULT_SIZE,
                                            GroupLayout.PREFERRED_SIZE
                                        )
                                        .addGap(0, 0, Short.MAX_VALUE.toInt())
                                )
                                .addComponent(
                                    commit,
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
                            layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                .addComponent(jLabel1)
                                .addComponent(
                                    time,
                                    GroupLayout.PREFERRED_SIZE,
                                    GroupLayout.DEFAULT_SIZE,
                                    GroupLayout.PREFERRED_SIZE
                                )
                        )
                        .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(
                            layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                .addComponent(jLabel2)
                                .addComponent(
                                    scramble,
                                    GroupLayout.PREFERRED_SIZE,
                                    GroupLayout.DEFAULT_SIZE,
                                    GroupLayout.PREFERRED_SIZE
                                )
                        )
                        .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(
                            layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                .addComponent(jLabel3)
                                .addComponent(
                                    penalties,
                                    GroupLayout.PREFERRED_SIZE,
                                    GroupLayout.DEFAULT_SIZE,
                                    GroupLayout.PREFERRED_SIZE
                                )
                        )
                        .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel4)
                        .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(
                            jScrollPane1,
                            GroupLayout.PREFERRED_SIZE,
                            GroupLayout.DEFAULT_SIZE,
                            GroupLayout.PREFERRED_SIZE
                        )
                        .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(commit)
                        .addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE.toInt())
                )
        )
        pack()
    }

    // Variables declaration - do not modify
    private var commit: JButton? = null
    private var penalties: JComboBox<Penalty>? = null
    private var jLabel1: JLabel? = null
    private var jLabel2: JLabel? = null
    private var jLabel3: JLabel? = null
    private var jLabel4: JLabel? = null
    private var jScrollPane1: JScrollPane? = null
    private var comments: JTextArea? = null
    private var time: JTextField? = null
    private var scramble: JTextField? = null
    // End of variables declaration
}