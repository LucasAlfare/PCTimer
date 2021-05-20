package com.fltimer.statistics

import java.util.*

class BestWindowedAverage(val windowSize: Int) : Statistic(StatisticId.BEST_WINDOWED_AVERAGE) {

    override fun getStatisticResult(statisticData: LinkedHashMap<UUID, StatisticDataObject>): StatisticResult {
        val averages = arrayListOf<StatisticResult>()
        val keys = statisticData.keys.toTypedArray()
        var i = 0
        while (i < keys.size) {
            val target = LinkedHashMap<UUID, StatisticDataObject>()

            var j = 0
            while (j < windowSize) {
                if (j + i < keys.size) {
                    val index = i + j++
                    target[keys[index]] = statisticData[keys[index]] as StatisticDataObject
                }
            }

            averages += WindowedAverage(windowSize).getStatisticResult(target)

            if (i + windowSize >= statisticData.size) {
                break
            }

            i++
        }

        var min = Long.MAX_VALUE
        var indexOfMin = 0
        i = 0
        while (i < averages.size) {
            val currentResult = averages[i].result
            if (currentResult < min) {
                min = currentResult
                indexOfMin = i
            }
            i++
        }

        val stat = averages[indexOfMin]
        stat.statisticId = StatisticId.BEST_WINDOWED_AVERAGE
        return stat
    }
}
