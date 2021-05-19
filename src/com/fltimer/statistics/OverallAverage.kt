package com.fltimer.statistics

class OverallAverage : Statistic(StatisticId.OVERALL_AVERAGE) {

    override fun getResult(): Long {
        if (statisticData.objects.size < 3) return -1L

        relatedElements.clear()
        var min = Long.MAX_VALUE
        var max = Long.MIN_VALUE
        var bestObj: StatisticDataObject? = null
        var worstObj: StatisticDataObject? = null
        statisticData.objects.forEach {
            if (it.number < min) {
                min = it.number
                bestObj = it
            } else if (it.number > max) {
                max = it.number
                worstObj = it
            }
        }

        var sum = 0L
        statisticData.objects.forEach {
            if (it.id != bestObj!!.id && it.id != worstObj!!.id) {
                sum += it.number
                relatedElements.add(it.id)
            }
        }

        return sum / (statisticData.objects.size - 2)
    }
}
