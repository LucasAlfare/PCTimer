package com.fltimer.gui

import com.fltimer.data.Solves
import com.fltimer.statistics.StatisticResult

/**
 * Base class for injecting multi front-ends in this application.
 *
 * All front-end operations supported for the project are defined here in order
 * to tell its extensions to create its implementations.
 *
 * The mains architecture for distinct Gui should follow the path:
 * - A custom "GuiSomething" class/file: this is the file that contains all the logic related
 * to the build of the visual interface. This includes laying out, design and some interactions
 * behaviors;
 * - The correspondent "GuiSomethingAdapter": the only class that directly interacts with the
 * "GuiSomething" class. The "GuiSomethingAdapter" should inherits from GuiAdapter and fill its
 * methods with Graphical User Interface logic. Note that any application logic should be made
 * here, only the sufficient to make the "GuiSomethingAdapter" widgets performs some actions.
 *
 * Doing this, all the logic related to the application is handled in the GuiManager class,
 * which is a fixed class in the application project.
 *
 * TODO: create more abstractions that any visual interface can have.
 */
abstract class GuiAdapter {

    /**
     * The main fields that can exists in a visual interface.
     * All of then are typed to Any? (can be null) in order to keep this compatible with
     * distinct platforms. E.g., this will could used even in a desktop, via Java Swing
     * elements, or in a Android device, though its View components.
     *
     * TODO: is possible to describe this architecture to a reactive project standard? (React, Jetpack Compose)
     */
    var root: Any? = null
    var scramble: Any? = null
    var display: Any? = null
    var solvesList: Any? = null
    var statisticsList: Any? = null
    var auxiliaryPane: Any? = null

    /**
     * Used to get and offer the two main interaction that the user can perform with
     * any graphic user interface.
     *
     * Those actions are named down and up, in order to match the "press" and the
     * "release" actions.
     *
     * Both callbacks are automatic called inside the direct implementation of this
     * class.
     */
    abstract fun setInteractionListener(onDown: () -> Unit, onUp: () -> Unit)

    abstract fun setCancelAction(onCancel: () -> Unit)

    abstract fun setEditSelectedSolveAction(action: () -> Unit)

    abstract fun setAddSolveAction(action: () -> Unit)

    abstract fun setDeleteSelectedAction(action: (Int) -> Unit)

    abstract fun setClearAction(action: () -> Unit)

    abstract fun setStatistics(originalData: Solves, resultsData: ArrayList<StatisticResult>)

    abstract fun setScrambleText(text: String)

    abstract fun setDisplayText(text: String)

    abstract fun setSolvesListData(data: Any)

    /**
     * Below some helper methods used to individually initializes fields related to the
     * visual interface. Once any of then can have its own behaviors those methods are
     * required to offer this inside the children of this base class.
     */
    abstract fun initGui()
    abstract fun initRoot()
    abstract fun initScramble()
    abstract fun initDisplay()
    abstract fun initSolvesList()
    abstract fun initStatisticList()
    abstract fun initAuxiliaryPane()

    /**
     * A main method to describe how the visual interface should be started.
     */
    abstract fun start()
}
