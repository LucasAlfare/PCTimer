package com.fltimer

import com.fltimer.data.Solve
import com.fltimer.data.Solves
import com.fltimer.statistics.StatisticDataObject
import java.util.*

/**
 * Converts a long to a timestamp in the format
 *
 * mm:ss.SSS
 *
 * where:
 * - mm: minutes padded to 2 digits;
 * - ss: seconds padded to 2 digits;
 * - SSS: milliseconds padded to 3 digits.
 *
 * TODO: prototype nanoseconds precision.
 */
fun Long.timestamp(): String {
    val seconds = "${(this / 1000) % 60}".padStart(2, '0')
    val milliseconds = "${this % 1000}".padStart(3, '0')

    if (this >= 60_000L) {
        val minutes = "${(this / 60_000L)}".padStart(2, '0')
        return "$minutes:$seconds.$milliseconds"
    }

    return "$seconds.$milliseconds"
}

fun Solve.toStatisticDataObject(): StatisticDataObject {
    return StatisticDataObject(this.id, this.time)
}

fun Solves.toStatisticData(): LinkedHashMap<UUID, StatisticDataObject> {
    val map = LinkedHashMap<UUID, StatisticDataObject>()
    keys.forEach { id ->
        val solve = get(id) as Solve
        map[id] = solve.toStatisticDataObject()
    }
    return map
}
