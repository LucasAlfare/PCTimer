package com.fltimer.library.statistics

import java.util.*

/**
 * Class that finds the maximum/highest value over the statistic data supplied.
 */
class HighestSingle : Statistic(StatisticId.HIGHEST_SINGLE) {

    override fun getStatisticResult(statisticData: LinkedHashMap<UUID, StatisticDataObject>): StatisticResult {
        relatedElements.clear()

        val ret = StatisticResult(id, 0L, relatedElements)
        if (statisticData.size < 1) return ret

        var highest = Long.MIN_VALUE
        var worst: StatisticDataObject? = null
        statisticData.keys.forEach {
            val data = statisticData[it]!!
            if (data.number > highest) {
                highest = data.number
                worst = data
            }
        }

        val result = worst!!.number
        relatedElements += worst!!.id

        ret.result = result
        ret.relatedElements = relatedElements

        return ret
    }
}
