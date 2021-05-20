package com.fltimer.statistics

import java.util.*

//refers to the current average X (average of last X numbers)
class WindowedAverage(private val windowSize: Int) : Statistic(StatisticId.WINDOWED_AVERAGE) {

    override fun getStatisticResult(statisticData: LinkedHashMap<UUID, StatisticDataObject>): StatisticResult {
        TODO("not implemented yet")
    }
}
