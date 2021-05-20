package com.fltimer.statistics

class OverallAverage : Statistic(StatisticId.OVERALL_AVERAGE) {

    override fun getResult(): Long {
        if (statisticData.size < 3) return 0L

        relatedElements.clear()

        var high = Long.MIN_VALUE
        var low = Long.MAX_VALUE
        var best: StatisticDataObject? = null
        var worst: StatisticDataObject? = null

        statisticData.keys.forEach {
            val data = statisticData[it]!!
            if (low > data.number) {
                low = data.number
                best = data
            } else if (high < data.number) {
                high = data.number
                worst = data
            }
        }

        var sum = 0L
        statisticData.keys.forEach {
            val data = statisticData[it]!!
            if (data.id != best!!.id && data.id != worst!!.id) {
                sum += data.number
                relatedElements += data.id
            }
        }

        return sum / (statisticData.size - 2)
    }
}
