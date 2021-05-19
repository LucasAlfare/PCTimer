package com.fltimer.statistics

import java.util.*

class BestSingle : Statistic(StatisticId.BEST_SINGLE) {

    override fun getResult(): Long {
        relatedElements.clear()
        var min = Long.MAX_VALUE
        var currentId = UUID.randomUUID()
        statisticData.objects.forEach {
            if (it.number < min) {
                min = it.number
                currentId = it.id
            }
        }
        relatedElements.add(currentId)
        return min
    }
}