package com.fltimer.statistics

class Mean : Statistic(StatisticId.MEAN) {

    override fun getResult(): Long {

        var sum = 0L

        statisticData.objects.forEach {
            sum += it.number
            relatedElements.add(it.id)
        }

        return sum / statisticData.objects.size
    }
}