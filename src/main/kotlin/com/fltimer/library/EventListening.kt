/**
 * This file contains the main code related to the Observer pattern.
 *
 * All the managers uses this to establish communication with the others, in order to
 * keep then not plugged to themselves.
 *
 * @author Francisco Lucas, 2021.
 */
package com.fltimer.library

/**
 * The main enumeration holding all labels of events used through the application process.
 *
 * TODO: remove statistics events, once they are not necessary because can be used though static calls.
 */
enum class Event {
    TIMER_TOGGLE_DOWN,
    TIMER_TOGGLE_UP,
    TIMER_SET_WCA_INSPECTION,
    TIMER_CANCEL,
    TIMER_UPDATE,
    TIMER_INSPECTION_STARTED,
    TIMER_INSPECTION_STOPPED,
    TIMER_STARTED,
    TIMER_STOPPED,

    DATA_CLEAR,
    DATA_ITEM_CREATE,
    DATA_ITEM_UPDATE,
    DATA_ITEM_REMOVE,
    DATA_GET,
    DATA_RESPONSE,
    DATA_PENALTY_UPDATE,
    DATA_CHANGED,

    SCRAMBLE_REQUEST_NEW,
    SCRAMBLE_REQUEST_CURRENT,
    SCRAMBLE_REQUEST_LAST,
    SCRAMBLE_RESPONSE_CURRENT,
    SCRAMBLE_RESPONSE_LAST,
    SCRAMBLE_CHANGED
}

/**
 * Interface that makes the class able to receive events (with its respective data)
 */
interface EventListener {
    fun onEvent(event: com.fltimer.library.Event, data: Any?)
}

/**
 * Class that makes all its children able to send events/data to any other which is
 * listening to.
 */
open class Listenable {

    private val listeners = arrayListOf<com.fltimer.library.EventListener>()

    fun notifyListeners(event: com.fltimer.library.Event, data: Any? = null) {
        listeners.forEach { it.onEvent(event, data) }
    }

    fun addListener(el: com.fltimer.library.EventListener) {
        if (!listeners.contains(el)) listeners += el
    }

    // deprecated?
    fun removeListener(el: com.fltimer.library.EventListener) {
        listeners -= el
    }
}