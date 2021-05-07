package com.fltimer.main

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
    DATA_CHANGED
}

interface EventListener {
    fun onEvent(event: Event, data: Any?)
}

open class Listenable {

    private val listeners = arrayListOf<EventListener>()

    fun notifyListeners(event: Event, data: Any? = null) {
        listeners.forEach { it.onEvent(event, data) }
    }

    fun addListener(el: EventListener) {
        if (!listeners.contains(el)) listeners += el
    }

    // deprecated?
    fun removeListener(el: EventListener) {
        listeners -= el
    }
}
