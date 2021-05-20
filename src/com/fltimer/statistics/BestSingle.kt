package com.fltimer.statistics

import java.util.*

class BestSingle : Statistic(StatisticId.BEST_SINGLE) {

    override fun getStatisticResult(statisticData: LinkedHashMap<UUID, StatisticDataObject>): StatisticResult {
        relatedElements.clear()

        val ret = StatisticResult(id, 0L, relatedElements)
        if (statisticData.size < 1) return ret

        var lowest = Long.MAX_VALUE
        var best: StatisticDataObject? = null
        statisticData.keys.forEach {
            val data = statisticData[it]!!
            if (data.number < lowest) {
                lowest = data.number
                best = data
            }
        }

        val result = best!!.number
        relatedElements += best!!.id

        ret.result = result
        ret.relatedElements = relatedElements

        return ret
    }
}
