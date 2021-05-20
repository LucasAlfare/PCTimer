package com.fltimer.statistics

import java.util.*

class OverallAverage : Statistic(StatisticId.OVERALL_AVERAGE) {

    override fun getStatisticResult(statisticData: LinkedHashMap<UUID, StatisticDataObject>): StatisticResult {
        relatedElements.clear()
        val ret = StatisticResult(id, 0L, relatedElements)

        if (statisticData.size < 3) return ret

        var high = Long.MIN_VALUE
        var low = Long.MAX_VALUE
        var best: StatisticDataObject? = null
        var worst: StatisticDataObject? = null

        statisticData.keys.forEach {
            val data = statisticData[it]!!
            if (low > data.number) {
                low = data.number
                best = data
            } else if (high < data.number) {
                high = data.number
                worst = data
            }
        }

        var sum = 0L
        statisticData.keys.forEach {
            val data = statisticData[it]!!
            if (data.id != best!!.id && data.id != worst!!.id) {
                sum += data.number
                relatedElements += data.id
            }
        }

        val result = sum / (statisticData.size - 2)
        ret.result = result
        ret.relatedElements = relatedElements
        return ret
    }
}
