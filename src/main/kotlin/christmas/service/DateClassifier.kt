package christmas.service

import christmas.constants.Constants.CHRISTMAS_DATE
import christmas.constants.Constants.DATE_START
import christmas.constants.Constants.DATE_END
import christmas.model.VisitDate

object DateClassifier {
    private val weekendStore = mutableSetOf<Int>()
    private val specialDateStore = mutableSetOf<Int>()

    private const val WEEK = 7
    private val DATE_RANGE by lazy { DATE_START.value..DATE_END.value }

    init {
        readyDateStore()
    }

    private fun readyDateStore() {
        for (date in DATE_RANGE step (WEEK)) {
            weekendStore.add(date)
            weekendStore.add(date + 1)
            specialDateStore.add(date + 2)
        }
        specialDateStore.add(CHRISTMAS_DATE.value)
    }

    fun isBeforeChristmas(visitDate: VisitDate) =
        visitDate.date <= CHRISTMAS_DATE.value

    fun isWeekend(visitDate: VisitDate) =
        weekendStore.contains(visitDate.date)

    fun isSpecialDate(visitDate: VisitDate) =
        specialDateStore.contains(visitDate.date)
}