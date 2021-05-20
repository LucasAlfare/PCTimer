package com.fltimer.statistics

import java.util.*

class Mean : Statistic(StatisticId.MEAN) {

    override fun getStatisticResult(statisticData: LinkedHashMap<UUID, StatisticDataObject>): StatisticResult {
        relatedElements.clear()
        var sum = 0L

        statisticData.keys.forEach {
            val data = statisticData[it]!!
            sum += data.number
            relatedElements += it
        }

        val result = sum / statisticData.size
        return StatisticResult(id, result, relatedElements)
    }
}
