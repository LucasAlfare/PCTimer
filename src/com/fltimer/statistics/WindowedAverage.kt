package com.fltimer.statistics

import java.util.*

//refers to the current average X (average of last X numbers)
class WindowedAverage(val windowSize: Int) : Statistic(StatisticId.WINDOWED_AVERAGE) {

    override fun getStatisticResult(statisticData: LinkedHashMap<UUID, StatisticDataObject>): StatisticResult {
        relatedElements.clear()
        if (statisticData.size < windowSize) return StatisticResult(id, 0L, relatedElements)

        val target = LinkedHashMap<UUID, StatisticDataObject>()
        val keys = statisticData.keys.toTypedArray()

        var i = keys.size - 1
        while (i != (keys.size - 1 - windowSize)) {
            target[keys[i]] = statisticData[keys[i]] as StatisticDataObject
            i--
        }

        val stat = Average().getStatisticResult(target)
        stat.statisticId = id
        return stat
    }
}
