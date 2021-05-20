package com.fltimer.statistics

import java.util.*

class OverallAverage : Statistic(StatisticId.OVERALL_AVERAGE) {

    override fun getStatisticResult(statisticData: LinkedHashMap<UUID, StatisticDataObject>): StatisticResult {
        val best = BestSingle().getStatisticResult(statisticData)
        val worst = WorstSingle().getStatisticResult(statisticData)
        val targetObjects =
            statisticData.filter {
                it.key != best.relatedElements[0] && it.key != worst.relatedElements[0]
            }
        return Mean().getStatisticResult(
            targetObjects as LinkedHashMap<UUID, StatisticDataObject>
        )
    }
}
