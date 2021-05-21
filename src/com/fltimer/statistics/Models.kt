package com.fltimer.statistics

import java.util.*

enum class StatisticId {
    MEAN,
    BEST_SINGLE,
    WORST_SINGLE,
    AVERAGE,
    WINDOWED_AVERAGE,
    BEST_WINDOWED_AVERAGE,
    WORST_WINDOWED_AVERAGE
}

abstract class Statistic(val id: StatisticId) {

    var relatedElements: ArrayList<UUID> = arrayListOf()

    abstract fun getStatisticResult(statisticData: LinkedHashMap<UUID, StatisticDataObject>): StatisticResult
}

data class StatisticDataObject(val id: UUID, val number: Long)

data class StatisticResult(var statisticId: StatisticId, var result: Long, var relatedElements: ArrayList<UUID>)
