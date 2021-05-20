package com.fltimer.statistics

import com.fltimer.Event
import com.fltimer.EventListener
import com.fltimer.Listenable
import com.fltimer.data.Solves
import com.fltimer.toStatisticData
import java.util.*

enum class StatisticId {
    MEAN,
    BEST_SINGLE,
    WORST_SINGLE,
    OVERALL_AVERAGE,
    WINDOWED_AVERAGE,
    BEST_WINDOWED_AVERAGE,
    WORST_WINDOWED_AVERAGE
}

data class StatisticDataObject(val id: UUID, val number: Long)
data class StatisticResult(val statisticId: StatisticId, var result: Long, var relatedElements: ArrayList<UUID>)
abstract class Statistic(var id: StatisticId) {

    var relatedElements: ArrayList<UUID> = arrayListOf()

    abstract fun getStatisticResult(statisticData: LinkedHashMap<UUID, StatisticDataObject>): StatisticResult
}

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
        statisticsMap[StatisticId.MEAN] = Mean()
        statisticsMap[StatisticId.BEST_SINGLE] = BestSingle()
        statisticsMap[StatisticId.OVERALL_AVERAGE] = OverallAverage()
        statisticsMap[StatisticId.WORST_SINGLE] = WorstSingle()
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
