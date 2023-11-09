package christmas.service

import christmas.model.VisitDate

private const val CHRISTMAS_DISCOUNT_START = 1000
private const val CHRISTMAS_DISCOUNT_INTERVAL = 100
private const val CHRISTMAS_DATE_START = 1
private const val CHRISTMAS_DATE_END = 25
private val CHRISTMAS_RANGE = CHRISTMAS_DATE_START..CHRISTMAS_DATE_END

object BenefitClassifier {

    fun availableChristmasDiscount(visitDate: VisitDate) =
        visitDate.date in CHRISTMAS_RANGE

    fun discountChristmas(visitDate: VisitDate) =
        CHRISTMAS_DISCOUNT_START + (visitDate.date - CHRISTMAS_DATE_START) * CHRISTMAS_DISCOUNT_INTERVAL

}