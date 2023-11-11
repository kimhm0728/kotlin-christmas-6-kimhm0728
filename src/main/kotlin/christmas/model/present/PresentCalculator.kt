package christmas.model.present

import christmas.constants.PriceConstants.PRESENT_STANDARD
import christmas.constants.PriceConstants.PRESENT_PRICE
import christmas.constants.PriceConstants.NO_PRESENT_PRICE
import christmas.model.Price

object PresentCalculator {
    fun isPresent(totalPrice: Price) =
        totalPrice >= PRESENT_STANDARD.price

    fun getPresent() = PRESENT_PRICE.price

    fun getNoPresent() = NO_PRESENT_PRICE.price
}