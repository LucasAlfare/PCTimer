package com.fltimer.statistics

import java.util.*

/**
 * This is a composite statistic that finds the minimum/lowest Windowed Average of
 * N elements over the statistic data supplied.
 *
 * The algorithm will compute all windowed averages of N that can fit inside the data.
 * For example, if windowSize field is set to 5 and the data contains 5 elements, then
 * only 1 windowed average of 5 elements will be used to get the result.
 *
 * For the same scenario, if the data contains 6 elements, 2 windowed averages will be
 * used to get the result. If is 10, the 5 will be used and so on.
 */
class LowsestWindowedAverage(val windowSize: Int) : Statistic(StatisticId.LOWEST_WINDOWED_AVERAGE) {

    override fun getStatisticResult(statisticData: LinkedHashMap<UUID, StatisticDataObject>): StatisticResult {
        if (statisticData.size < windowSize)
            return StatisticResult(StatisticId.LOWEST_WINDOWED_AVERAGE, 0L, relatedElements)

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
        stat.statisticId = StatisticId.LOWEST_WINDOWED_AVERAGE
        return stat
    }
}
