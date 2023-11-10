package christmas.service

import christmas.constants.Constants.PRESENT_PRICE

private const val PRESENT_STANDARD = 120000

object PresentApplier {
    fun availablePresent(totalPrice: Int) =
        totalPrice >= PRESENT_STANDARD

    fun getPresent() = PRESENT_PRICE.value
}