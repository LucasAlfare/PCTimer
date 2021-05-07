@file:Suppress("MemberVisibilityCanBePrivate")

package com.fltimer.main.data

import com.fltimer.main.Event
import com.fltimer.main.EventListener
import com.fltimer.main.Listenable
import java.util.*
import kotlin.collections.ArrayList

data class Solve(
    val id: UUID = UUID.randomUUID(),
    var time: Long,
    var scramble: String = "[no scramble yet]",
    var penalty: String = "ok"
)

/**
 * In Events:
 * - DATA_CLEAR;
 * - DATA_ITEM_CREATE(params: time, scramble, penalty)
 * - DATA_ITEM_UPDATE(params: id, time, scramble, penalty);
 * - DATA_ITEM_REMOVE(params: id);
 * - DATA_GET.
 *
 * Out Events:
 * - DATA_RESPONSE(carries: the data);
 * - DATA_CHANGED(carries: the data).
 */
class DataManager : Listenable(), EventListener {

    private val solves = arrayListOf<Solve>()

    override fun onEvent(event: Event, data: Any?) {
        when (event) {
            Event.DATA_CLEAR -> {
                println("DATA_CLEAR received")
                handleDataClear()
            }

            Event.DATA_ITEM_CREATE -> {
                println("DATA_ITEM_CREATE received")
                val params = data as Array<*>
                val time = params[0] as Long
                val scramble = params[1] as String
                val penalty = params[2] as String
                handleDataItemCreate(
                    time = time,
                    scramble = scramble,
                    penalty = penalty
                )
            }

            Event.DATA_ITEM_UPDATE -> {
                println("DATA_ITEM_UPDATE received")
                val params = data as Array<*>
                handleDataItemUpdate(
                    id = params[0] as UUID,
                    time = params[1] as Long,
                    scramble = params[2] as String,
                    penalty = params[3] as String
                )
            }

            Event.DATA_ITEM_REMOVE -> {
                println("DATA_ITEM_REMOVE received")
                handleDataItemRemove(data as UUID)
            }

            Event.DATA_GET -> {
                println("DATA_GET received")
                handleDataGet()
            }
            else -> {
            }
        }
    }

    fun handleDataClear() {
        solves.clear()
        notifyListeners(Event.DATA_CHANGED, solves)
    }

    fun handleDataItemCreate(time: Long, scramble: String, penalty: String): Solve {
        val ref = Solve(time = time, scramble = scramble, penalty = penalty)
        solves += ref
        notifyListeners(Event.DATA_CHANGED, solves)
        return ref
    }

    fun handleDataItemUpdate(id: UUID, time: Long, scramble: String, penalty: String) {
        val solveRef = solves.find { it.id == id }!!
        solveRef.time = time
        solveRef.scramble = scramble
        solveRef.penalty = penalty
        notifyListeners(Event.DATA_CHANGED, solves)
    }

    fun handleDataItemRemove(id: UUID) {
        val solveRef = solves.find { it.id == id }!!
        solves -= solveRef
        notifyListeners(Event.DATA_CHANGED, solves)
    }

    fun handleDataGet(): ArrayList<*> {
        notifyListeners(Event.DATA_CHANGED, solves)
        return solves
    }
}
