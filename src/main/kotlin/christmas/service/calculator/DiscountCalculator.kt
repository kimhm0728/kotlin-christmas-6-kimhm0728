package christmas.service.calculator

import christmas.constants.Constants.CHRISTMAS_DATE
import christmas.constants.Constants.DATE_START
import christmas.constants.Range
import christmas.model.VisitDate

object DiscountCalculator {
    private val weekendStore = mutableSetOf<Int>()
    private val specialDateStore = mutableSetOf<Int>()

    init {
        readyDateStore()
    }

    private fun readyDateStore() {
        for (date in Range.DATE step(WEEK)) {
            weekendStore.add(date)
            weekendStore.add(date + 1)
            specialDateStore.add(date + 2)
        }
        specialDateStore.add(CHRISTMAS_DATE.value)
    }

    fun availableChristmasDiscount(visitDate: VisitDate) =
        visitDate.date in Range.CHRISTMAS

    fun discountChristmas(visitDate: VisitDate) =
        CHRISTMAS_DISCOUNT_START + (visitDate.date - DATE_START.value) * CHRISTMAS_DISCOUNT_INTERVAL

    fun isWeekend(visitDate: VisitDate) =
        weekendStore.contains(visitDate.date)

    fun discountWeek(menuCount: Int) =
        menuCount * WEEK_DISCOUNT

    fun availableSpecialDiscount(visitDate: VisitDate) =
        specialDateStore.contains(visitDate.date)

    fun discountSpecial() = SPECIAL_DISCOUNT

    private const val WEEK = 7
    private const val WEEK_DISCOUNT = 2023
    private const val CHRISTMAS_DISCOUNT_START = 1000
    private const val CHRISTMAS_DISCOUNT_INTERVAL = 100
    private const val SPECIAL_DISCOUNT = 100
}