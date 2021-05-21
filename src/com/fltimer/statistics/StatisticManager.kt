package com.fltimer.statistics

import com.fltimer.Event
import com.fltimer.EventListener
import com.fltimer.Listenable
import com.fltimer.data.Solves
import com.fltimer.toStatisticData

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

    private val statisticsMap = LinkedHashMap<StatisticId, Statistic>()

    init {
        //TODO: make assigns dynamic
        statisticsMap[StatisticId.MEAN] = Mean()
        statisticsMap[StatisticId.LOWEST_SINGLE] = LowestSingle()
        statisticsMap[StatisticId.HIGHEST_SINGLE] = HighestSingle()
        statisticsMap[StatisticId.AVERAGE] = Average()
        //TODO other stats
    }

    override fun onEvent(event: Event, data: Any?) {
        when (event) {
            Event.STATISTIC_GET_ALL_RESULTS -> {
                handleStatisticGetAllResults(data as Solves)
            }

            Event.STATISTIC_GET_RESULT_OF -> {
                val params = data as Array<*>
                handleStatisticGetResultOf(params[0] as StatisticId, params[1] as Solves)
            }
            else -> {
            }
        }
    }

    private fun handleStatisticGetAllResults(solves: Solves) {
        val results = arrayListOf<StatisticResult>()
        statisticsMap.values.forEach { statistic ->
            results.add(statistic.getStatisticResult(solves.toStatisticData()))
        }
        notifyListeners(Event.STATISTIC_RESPONSE_ALL_RESULT, results)
    }

    private fun handleStatisticGetResultOf(statisticId: StatisticId, solves: Solves) {
        val result = statisticsMap[statisticId]!!.getStatisticResult(solves.toStatisticData())
        notifyListeners(Event.STATISTIC_RESPONSE_RESULT_OF, result)
    }
}
