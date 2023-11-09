package christmas.service

import christmas.model.VisitDate

private const val DATE_START = 1
private const val DATE_END = 31
private val DATE_RANGE = DATE_START..DATE_END

private const val WEEK = 7

private const val WEEK_DISCOUNT = 2023

private const val CHRISTMAS_DISCOUNT_START = 1000
private const val CHRISTMAS_DISCOUNT_INTERVAL = 100
private const val CHRISTMAS_DATE = 25
private val CHRISTMAS_RANGE = DATE_START..CHRISTMAS_DATE

private const val SPECIAL_DISCOUNT = 1000

object DiscountApplier {
    private val weekendStore = mutableSetOf<Int>()
    private val specialDateStore = mutableSetOf<Int>()

    init {
        readyDateStore()
    }

    private fun readyDateStore() {
        for (date in DATE_RANGE step(WEEK)) {
            weekendStore.add(date)
            weekendStore.add(date + 1)
            specialDateStore.add(date + 2)
        }
        specialDateStore.add(CHRISTMAS_DATE)
    }

    fun availableChristmasDiscount(visitDate: VisitDate) =
        visitDate.date in CHRISTMAS_RANGE

    fun discountChristmas(visitDate: VisitDate) =
        CHRISTMAS_DISCOUNT_START + (visitDate.date - DATE_START) * CHRISTMAS_DISCOUNT_INTERVAL

    fun isWeekend(visitDate: VisitDate) =
        weekendStore.contains(visitDate.date)

    fun discountWeek(menuCount: Int) =
        menuCount * WEEK_DISCOUNT

    fun availableSpecialDiscount(visitDate: VisitDate) =
        specialDateStore.contains(visitDate.date)

    fun discountSpecial() = SPECIAL_DISCOUNT
}