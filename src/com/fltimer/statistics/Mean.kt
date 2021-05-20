package com.fltimer.statistics

import java.util.*

class Mean : Statistic(StatisticId.MEAN) {

    override fun getStatisticResult(statisticData: LinkedHashMap<UUID, StatisticDataObject>): StatisticResult {
        relatedElements.clear()

        val ret = StatisticResult(id, 0L, relatedElements)
        if (statisticData.size < 1) return ret

        var sum = 0L

        statisticData.keys.forEach {
            val data = statisticData[it]!!
            sum += data.number
            relatedElements += it
        }

        val result = sum / statisticData.size
        ret.result = result
        ret.relatedElements = relatedElements
        return ret
    }
}
