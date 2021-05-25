package com.fltimer.data

import java.util.*

/**
 * Basic extension for a LinkedHashMap.
 *
 * This class is used as the main data holder for all objects created over the application
 * flow.
 */
class Solves : LinkedHashMap<UUID, Solve>() {

    operator fun plusAssign(solve: Solve) {
        this[solve.id] = solve
    }
}
