package com.fltimer.library.statistics

import java.util.*

/**
 * Enum class hold some identifiers for the statistics.
 * The idea is use this to mark the statistics is with something clear to the
 * application look at.
 */
enum class StatisticId {
    MEAN,
    LOWEST_SINGLE,
    HIGHEST_SINGLE,
    AVERAGE,
    WINDOWED_AVERAGE,
    LOWEST_WINDOWED_AVERAGE,
    HIGHEST_WINDOWED_AVERAGE,

    EMPTY
}

/**
 * This data class represents a result calculated for any statistic.
 *
 * The result is described as having a ID, a numeric result and a list with references for
 * all elements used to calculate this statistic.
 */
data class StatisticResult(var statisticId: StatisticId, var result: Long, var relatedElements: ArrayList<UUID>)

/**
 * This data class is a representation of a object used over the statistics calculations.
 *
 * Basically, any entity that wants to get statistics for any numeric data must to supply
 * then in this format.
 */
data class StatisticDataObject(val id: UUID, val number: Long)

/**
 * Base class for all statistics.
 *
 * This class describes that all statistics will be able to generate a StatisticResult
 * based in a map supplied via parameter.
 */
abstract class Statistic(val id: StatisticId) {

    var relatedElements: ArrayList<UUID> = arrayListOf()

    abstract fun getStatisticResult(statisticData: LinkedHashMap<UUID, StatisticDataObject>): StatisticResult
}
