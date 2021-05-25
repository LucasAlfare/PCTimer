package com.fltimer.statistics

import java.util.*


fun getAllStatisticsFor(statisticData: LinkedHashMap<UUID, StatisticDataObject>): ArrayList<StatisticResult> {
    return arrayListOf(
        getStatisticResult(statisticData, StatisticId.LOWEST_SINGLE),
        getStatisticResult(statisticData, StatisticId.HIGHEST_SINGLE),
        getStatisticResult(statisticData, StatisticId.MEAN),
        getStatisticResult(statisticData, StatisticId.AVERAGE),
        getStatisticResult(statisticData, StatisticId.WINDOWED_AVERAGE),
        getStatisticResult(statisticData, StatisticId.LOWEST_WINDOWED_AVERAGE, 5),
        getStatisticResult(statisticData, StatisticId.LOWEST_WINDOWED_AVERAGE, 12),
        getStatisticResult(statisticData, StatisticId.LOWEST_WINDOWED_AVERAGE, 50),
        getStatisticResult(statisticData, StatisticId.LOWEST_WINDOWED_AVERAGE, 100)
    )
}

fun getStatisticResult(
    statisticData: LinkedHashMap<UUID, StatisticDataObject>,
    id: StatisticId,
    windowSize: Int = -1
): StatisticResult {
    when (id) {
        StatisticId.LOWEST_SINGLE -> {
            return LowestSingle().getStatisticResult(statisticData)
        }

        StatisticId.HIGHEST_SINGLE -> {
            return HighestSingle().getStatisticResult(statisticData)
        }

        StatisticId.MEAN -> {
            return Mean().getStatisticResult(statisticData)
        }

        StatisticId.AVERAGE -> {
            return Average().getStatisticResult(statisticData)
        }

        StatisticId.WINDOWED_AVERAGE -> {
            if (windowSize != -1) {
                return WindowedAverage(windowSize).getStatisticResult(statisticData)
            }
        }

        StatisticId.LOWEST_WINDOWED_AVERAGE -> {
            if (windowSize != -1) {
                return LowsestWindowedAverage(windowSize).getStatisticResult(statisticData)
            }
        }
    }

    return StatisticResult(StatisticId.EMPTY, 0L, arrayListOf())
}