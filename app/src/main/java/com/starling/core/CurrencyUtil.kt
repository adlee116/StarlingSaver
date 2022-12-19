package com.starling.core

fun Number.penniesToRoundUp(): Int {
    val remainingPennies = this.toInt() % 100
    return if (remainingPennies == 0) {
        0
    } else {
        100 - remainingPennies
    }
}

fun Number.formatPound(): String {
    val pennies = this.toInt()
    val pounds = pennies / 100
    val remainingPennies = pennies % 100
    return "£${String.format("%d.%02d", pounds, remainingPennies)}"
}