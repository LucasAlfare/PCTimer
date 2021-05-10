package com.fltimer.main.statistics

import com.fltimer.main.Event
import com.fltimer.main.EventListener
import com.fltimer.main.Listenable
import com.fltimer.main.data.Solve

enum class StatisticId {
    MEAN
}

interface Statistic {

    fun result(data: ArrayList<Solve>): Long
    fun elements(): Any
    fun statisticResult(data: ArrayList<Solve>): StatisticResult
}

data class StatisticResult(val statisticId: StatisticId, val result: Long, val relatedElements: Any)

/**
 * In Events:
 * - STATISTIC_GET_ALL_RESULTS;
 * - STATISTIC_GET_RESULT_OF(params: StatisticId).
 *
 * Out Events:
 * - STATISTIC_RESPONSE_ALL_RESULT(carries: StatisticResult of all statistics in a array);
 * - STATISTIC_RESPONSE_RESULT_OF(carries: StatisticResult of desired statistic).
 */
class StatisticManager : Listenable(), EventListener {

    private val statisticsMap = HashMap<StatisticId, Statistic>()

    init {
        statisticsMap[StatisticId.MEAN] = Mean()
        //TODO other stats
    }

    override fun onEvent(event: Event, data: Any?) {
        when (event) {
            Event.STATISTIC_GET_ALL_RESULTS -> {
                handleStatisticGetAllResults()
            }

            Event.STATISTIC_GET_RESULT_OF -> {
                handleStatisticGetResultOf(data as StatisticId)
            }
            else -> {
            }
        }
    }

    private fun handleStatisticGetAllResults() {
        notifyListeners(Event.STATISTIC_RESPONSE_ALL_RESULT, statisticsMap.values.toTypedArray())
    }

    private fun handleStatisticGetResultOf(statisticId: StatisticId) {
        notifyListeners(Event.STATISTIC_RESPONSE_RESULT_OF, statisticsMap[statisticId])
    }
}
