package com.fltimer.data

import java.util.*

class Solves : LinkedHashMap<UUID, Solve>() {

    operator fun plusAssign(solve: Solve) {
        this[solve.id] = solve
    }
}
