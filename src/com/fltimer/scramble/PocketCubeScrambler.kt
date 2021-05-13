package com.fltimer.scramble

class PocketCubeScrambler : Scrambler {

    private val moves = arrayOf("R", "U", "F")
    private val directions = arrayOf(" ", "' ", "2 ")

    override fun getScramble(): String {
        var s = ""
        var a = ""
        var b: String

        for (i in 0 until (rand.nextInt(4) + 8)) {
            do {
                b = moves[rand.nextInt(moves.size)]
            } while (b == a)

            a = b
            s += "$b${directions[rand.nextInt(directions.size)]}"
        }

        return s
    }
}
