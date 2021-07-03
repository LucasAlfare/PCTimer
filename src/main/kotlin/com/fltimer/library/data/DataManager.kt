@file:Suppress("MemberVisibilityCanBePrivate")

package com.fltimer.library.data

import com.fltimer.library.Listenable
import java.util.*


enum class Penalty {
    OK,
    PLUS_TWO,
    DNF
}

data class Solve(
    val id: UUID = UUID.randomUUID(),
    var time: Long,
    var scramble: String = "[no scramble set]",
    var penalty: Penalty = Penalty.OK
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
@Suppress("NON_EXHAUSTIVE_WHEN")
class DataManager : Listenable(), com.fltimer.library.EventListener {

    private val solves = Solves()

    override fun onEvent(event: com.fltimer.library.Event, data: Any?) {
        when (event) {
            com.fltimer.library.Event.DATA_CLEAR -> handleDataClear()

            com.fltimer.library.Event.DATA_ITEM_CREATE -> {
                val params = data as Array<*>
                val time = params[0] as Long
                val scramble = params[1] as String
                val penalty = params[2] as Penalty
                handleDataItemCreate(
                    time = time,
                    scramble = scramble,
                    penalty = penalty
                )
            }

            com.fltimer.library.Event.DATA_ITEM_UPDATE -> {
                println("DATA_ITEM_UPDATE received")
                val params = data as Array<*>
                handleDataItemUpdate(
                    id = params[0] as UUID,
                    time = params[1] as Long,
                    scramble = params[2] as String,
                    penalty = params[3] as Penalty
                )
            }

            com.fltimer.library.Event.DATA_ITEM_REMOVE -> handleDataItemRemove(data as UUID)
            com.fltimer.library.Event.DATA_GET -> handleDataGet()
        }
    }

    fun handleDataClear() {
        solves.clear()
        notifyListeners(com.fltimer.library.Event.DATA_CHANGED, solves)
    }

    fun handleDataItemCreate(time: Long, scramble: String, penalty: Penalty): Solve {
        val solveRef = Solve(time = time, scramble = scramble, penalty = penalty)
        solves[solveRef.id] = solveRef
        notifyListeners(com.fltimer.library.Event.DATA_CHANGED, solves)
        return solveRef
    }

    fun handleDataItemUpdate(id: UUID, time: Long, scramble: String, penalty: Penalty) {
        val solveRef = solves[id]!!
        solveRef.time = time
        solveRef.scramble = scramble
        solveRef.penalty = penalty
        notifyListeners(com.fltimer.library.Event.DATA_CHANGED, solves)
    }

    fun handleDataItemRemove(id: UUID) {
        solves.remove(id)
        notifyListeners(com.fltimer.library.Event.DATA_CHANGED, solves)
    }

    fun handleDataGet(): LinkedHashMap<UUID, Solve> {
        notifyListeners(com.fltimer.library.Event.DATA_CHANGED, solves)
        return solves
    }
}