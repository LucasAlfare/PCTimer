package com.fltimer

/**
 * Converts a long to a timestamp in the format
 *
 * mm:ss.SSS
 *
 * where:
 * - mm: minutes padded to 2 digits;
 * - ss: seconds padded to 2 digits;
 * - SSS: milliseconds padded to 3 digits.
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
