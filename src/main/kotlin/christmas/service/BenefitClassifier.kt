package christmas.service

import christmas.model.VisitDate

private const val CHRISTMAS_DISCOUNT_START = 1000
private const val CHRISTMAS_DISCOUNT_INTERVAL = 100
private const val CHRISTMAS_DATE_START = 1
private const val CHRISTMAS_DATE_END = 25
private val CHRISTMAS_RANGE = CHRISTMAS_DATE_START..CHRISTMAS_DATE_END

private const val DATE_START = 1
private const val DATE_END = 31
private val DATE_RANGE = DATE_START..DATE_END

private const val WEEK = 7

private const val WEEK_DISCOUNT = 2023

object BenefitClassifier {
    private val weekendStore = mutableSetOf<Int>()

    init {
        for (date in DATE_RANGE step(WEEK)) {
            weekendStore.add(date)
            weekendStore.add(date + 1)
        }
    }

    fun availableChristmasDiscount(visitDate: VisitDate) =
        visitDate.date in CHRISTMAS_RANGE

    fun discountChristmas(visitDate: VisitDate) =
        CHRISTMAS_DISCOUNT_START + (visitDate.date - CHRISTMAS_DATE_START) * CHRISTMAS_DISCOUNT_INTERVAL

    fun isWeekend(visitDate: VisitDate) =
        weekendStore.contains(visitDate.date)

    fun discountWeek(menuCount: Int) =
        menuCount * WEEK_DISCOUNT

}