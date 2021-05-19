package com.fltimer.statistics

import java.util.*

class WorstSingle : Statistic(StatisticId.WORST_SINGLE) {
    override fun getResult(): Long {
        relatedElements.clear()
        var max = Long.MIN_VALUE
        var currentId = UUID.randomUUID()
        statisticData.objects.forEach {
            if (it.number > max) {
                max = it.number
                currentId = it.id
            }
        }
        relatedElements.add(currentId)
        return max
    }
}