package com.fltimer.statistics

import java.util.*

class Average : Statistic(StatisticId.AVERAGE) {

    override fun getStatisticResult(statisticData: LinkedHashMap<UUID, StatisticDataObject>): StatisticResult {
        val best = BestSingle().getStatisticResult(statisticData)
        val worst = WorstSingle().getStatisticResult(statisticData)
        val targetObjects =
            statisticData.filter {
                it.key != best.relatedElements[0] && it.key != worst.relatedElements[0]
            }
        val stat = Mean().getStatisticResult(
            targetObjects as LinkedHashMap<UUID, StatisticDataObject>
        )
        stat.statisticId = id
        return stat
    }
}