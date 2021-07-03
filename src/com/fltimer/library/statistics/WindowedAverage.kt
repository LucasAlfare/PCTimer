package com.fltimer.library.statistics

import java.util.*

/**
 * This is a secondary type of Average that refers only for the last N elements from
 * the statistic data.
 *
 * Considering the main data contains 500 elements, this statistic will get the average
 * only for its last N elements. Then, for this example, if the windowSize field is set
 * to any value between 3 and 500, the class will return the Average from this amount
 * of data.
 */
class WindowedAverage(val windowSize: Int) : Statistic(StatisticId.WINDOWED_AVERAGE) {

    override fun getStatisticResult(statisticData: LinkedHashMap<UUID, StatisticDataObject>): StatisticResult {
        relatedElements.clear()
        if (statisticData.size < windowSize)
            return StatisticResult(id, 0L, relatedElements)

        val target = LinkedHashMap<UUID, StatisticDataObject>()
        val keys = statisticData.keys.toTypedArray()

        var i = keys.size - 1
        while (i != (keys.size - 1 - windowSize)) {
            target[keys[i]] = statisticData[keys[i]] as StatisticDataObject
            i--
        }

        val stat = Average().getStatisticResult(target)
        //TODO: update logic to avoid reversing the related elements
        stat.relatedElements.reverse()
        stat.statisticId = id

        return stat
    }
}
