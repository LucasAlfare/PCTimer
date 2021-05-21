package com.fltimer.statistics

import java.util.*

/**
 * Class that calculates the truncated mean for the statistic data supplied.
 *
 * This calculation is made in a composite way, once this first finds the Minimum and
 * the Maximum values (for not considering it) and finally returns the standard mean,
 * which carries the main result.
 *
 * A truncated mean consists in a standard arithmetic mean made over the elements,
 * excluding the element with minimum value and the element with maximum value.
 *
 * In practical terms this can be helpful because can measure a better mean approximation
 * unconsidering any elements related from excess lucky or bad lucky.
 */
class Average : Statistic(StatisticId.AVERAGE) {

    override fun getStatisticResult(statisticData: LinkedHashMap<UUID, StatisticDataObject>): StatisticResult {
        val best = LowestSingle().getStatisticResult(statisticData)
        val worst = HighestSingle().getStatisticResult(statisticData)
        val targetObjects = statisticData.filter {
            it.key != best.relatedElements[0] && it.key != worst.relatedElements[0]
        }

        relatedElements.clear()
        statisticData.keys.forEach { relatedElements += it }

        val stat = Mean().getStatisticResult(targetObjects as LinkedHashMap<UUID, StatisticDataObject>)
        stat.statisticId = id
        stat.relatedElements = relatedElements

        return stat
    }
}
