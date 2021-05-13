package com.fltimer.statistics

import com.fltimer.data.Solve
import java.util.*
import kotlin.collections.ArrayList

class Mean : Statistic {

    private val relatedIds = arrayListOf<UUID>()

    override fun result(data: ArrayList<Solve>): Long {
        relatedIds.clear()
        var sum = 0L
        data.forEach {
            sum += it.time
            relatedIds += it.id
        }

        return sum / data.size
    }

    override fun elements(): Any {
        return relatedIds
    }

    override fun statisticResult(data: ArrayList<Solve>) = StatisticResult(StatisticId.MEAN, result(data), relatedIds)
}