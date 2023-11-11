package christmas.model.discount

import christmas.constants.Constants.DATE_START
import christmas.model.VisitDate

object DiscountCalculator {
    fun getChristmasPrice(visitDate: VisitDate) =
        CHRISTMAS_DISCOUNT_START + (visitDate.date - DATE_START.value) * CHRISTMAS_DISCOUNT_INTERVAL

    fun getWeekPrice(menuCount: Int) =
        menuCount * WEEK_DISCOUNT

    fun getSpecialPrice() = SPECIAL_DISCOUNT

    private const val WEEK_DISCOUNT = 2023
    private const val CHRISTMAS_DISCOUNT_START = 1000
    private const val CHRISTMAS_DISCOUNT_INTERVAL = 100
    private const val SPECIAL_DISCOUNT = 1000
}