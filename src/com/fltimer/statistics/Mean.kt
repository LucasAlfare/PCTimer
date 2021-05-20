package com.fltimer.statistics

class Mean : Statistic(StatisticId.MEAN) {

    override fun getResult(): Long {
        relatedElements.clear()
        var sum = 0L

        statisticData.keys.forEach {
            val data = statisticData[it]!!
            sum += data.number
            relatedElements += it
        }

        return sum / statisticData.size
    }
}
