package com.fltimer.statistics

import com.fltimer.Event
import com.fltimer.EventListener
import com.fltimer.Listenable
import com.fltimer.data.Solve

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
 * - STATISTIC_GET_ALL_RESULTS(params: solves reference);
 * - STATISTIC_GET_RESULT_OF(params: StatisticId, solves reference).
 *
 * Out Events:
 * - STATISTIC_RESPONSE_ALL_RESULT(carries: StatisticResult of all statistics in a array);
 * - STATISTIC_RESPONSE_RESULT_OF(carries: StatisticResult of desired statistic).
 */
class StatisticManager : Listenable(), EventListener {

    private var solvesRef: ArrayList<Solve>? = null
    private val statisticsMap = HashMap<StatisticId, Statistic>()

    init {
        statisticsMap[StatisticId.MEAN] = Mean()
        //TODO other stats
    }

    override fun onEvent(event: Event, data: Any?) {
        when (event) {
            Event.STATISTIC_GET_ALL_RESULTS -> {
                handleStatisticGetAllResults(data as ArrayList<Solve>)
            }

            Event.STATISTIC_GET_RESULT_OF -> {
                val params = data as Array<*>
                handleStatisticGetResultOf(params[0] as StatisticId, params[1] as ArrayList<Solve>)
            }
            else -> {
            }
        }
    }

    private fun handleStatisticGetAllResults(solves: ArrayList<Solve>) {
        val results = arrayListOf<StatisticResult>()
        statisticsMap.values.forEach { statistic -> results.add(statistic.statisticResult(solves)) }
        notifyListeners(Event.STATISTIC_RESPONSE_ALL_RESULT, results)
    }

    private fun handleStatisticGetResultOf(statisticId: StatisticId, solves: ArrayList<Solve>) {
        val result = statisticsMap[statisticId]!!.statisticResult(solves)
        notifyListeners(Event.STATISTIC_RESPONSE_RESULT_OF, result)
    }
}
