package christmas.service.applier

import christmas.constants.Constants.PRESENT_PRICE
import christmas.constants.Constants.PRESENT_STANDARD

object PresentApplier {
    fun availablePresent(totalPrice: Int) =
        totalPrice >= PRESENT_STANDARD.value

    fun getPresent() = PRESENT_PRICE.value
}