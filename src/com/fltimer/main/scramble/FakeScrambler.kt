package com.fltimer.main.scramble

import kotlin.math.round

class FakeScrambler : Scrambler {
    override fun getScramble(): String {
        return "fake-scramble-${round(Math.random() * 100)}"
    }
}