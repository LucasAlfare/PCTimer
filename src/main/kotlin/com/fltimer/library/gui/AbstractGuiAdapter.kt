package com.fltimer.library.gui

import com.fltimer.library.data.Solves
import com.fltimer.library.statistics.StatisticResult

/**
 * Base class for injecting multi front-ends in this application.
 *
 * All front-end operations supported for the project are defined here in order
 * to tell its extensions to create its implementations.
 *
 * The main architecture for distinct Gui should follow the path:
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
abstract class AbstractGuiAdapter {

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
     * Both callbacks must be automatic called inside the direct implementation of this
     * class.
     */
    abstract fun setInteractionListener(onDown: () -> Unit, onUp: () -> Unit)

    /**
     * Defines what should occur in a Cancel action. Normally, this cancel
     * refers to the act of interrupt and reset the state of the timer,
     * making it in a raw state again.
     *
     * The callback function is called onCancel and must be called inside the
     * direct implementations of this class.
     */
    abstract fun setCancelAction(onCancel: () -> Unit)

    /**
     * Defines what should occur when the user interface got actions
     * related to editing results.
     *
     * The callback function is called action and must be called inside the
     * direct implementations of this class.
     */
    abstract fun setEditSelectedSolveAction(action: () -> Unit)

    /**
     * Defines what should occur when the user perform a request
     * to create a new item to be stored in the timer objects database.
     *
     * The callback function is called action and must be called inside the
     * direct implementations of this class.
     */
    abstract fun setAddSolveAction(action: () -> Unit)

    /**
     * Defines what should occur when the user perform a request
     * to remove a existing item object from the timer database.
     *
     * The callback function is called action and must be called inside the
     * direct implementations of this class.
     */
    abstract fun setDeleteSelectedAction(action: (Int) -> Unit)

    /**
     * Defines what should occur when the user perform a request
     * to clear all the existing data of the application.
     *
     * The callback function is called action and must be called inside the
     * direct implementations of this class.
     */
    abstract fun setClearAction(action: () -> Unit)

    /**
     * Method used to describe what and when update the statistics over the
     * user interface.
     *
     * This comes with the root data and with a array of results based on that
     * data.
     */
    abstract fun setStatistics(originalData: Solves, resultsData: ArrayList<StatisticResult>)

    /**
     * Method used to describe how to set the scramble text/data over the
     * user interface.
     */
    abstract fun setScrambleText(text: String)

    /**
     * Method used to describe how to set the display text/data over the
     * user interface.
     */
    abstract fun setDisplayText(text: String)

    /**
     * Method to describe how to set the current application objects/data
     * over the user interface.
     */
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
