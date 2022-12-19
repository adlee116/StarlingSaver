package com.starling.core

import org.junit.Test

internal class CurrencyUtilKtTest {

    @Test
    fun `Pennies are correctly rounded to the nearest pound`() {
        val pennyOne: Pair<Int, Int> = Pair(1, 99)
        val pennyTwo: Pair<Int, Int> = Pair(99, 1)
        val pennyThree: Pair<Int, Int> = Pair(149, 51)
        val pennyFour: Pair<Int, Int> = Pair(9999, 1)
        assert(pennyOne.first.penniesToRoundUp() == pennyOne.second)
        assert(pennyTwo.first.penniesToRoundUp() == pennyTwo.second)
        assert(pennyThree.first.penniesToRoundUp() == pennyThree.second)
        assert(pennyFour.first.penniesToRoundUp() == pennyFour.second)
    }

    @Test
    fun `Pounds are displayed correctly and keep their decimal places`() {
        val pennyOne: Pair<Int, String> = Pair(1, "£0.01")
        val pennyTwo: Pair<Int, String> = Pair(99, "£0.99")
        val pennyThree: Pair<Int, String> = Pair(149, "£1.49")
        val pennyFour: Pair<Int, String> = Pair(9999, "£99.99")
        val pennyFive: Pair<Int, String> = Pair(140, "£1.40")
        assert(pennyOne.first.formatPound() == pennyOne.second)
        assert(pennyTwo.first.formatPound() == pennyTwo.second)
        assert(pennyThree.first.formatPound() == pennyThree.second)
        assert(pennyFour.first.formatPound() == pennyFour.second)
        assert(pennyFive.first.formatPound() == pennyFive.second)
    }
}