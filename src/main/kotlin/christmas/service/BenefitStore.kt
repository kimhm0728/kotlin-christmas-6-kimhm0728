package christmas.service

import christmas.constants.Benefit
import christmas.model.OrderMenu
import christmas.model.VisitDate

class BenefitStore(private val visitDate: VisitDate, private val orderMenu: OrderMenu) {
    val store = mutableMapOf<Benefit, Int>()

    fun applyBenefit() {
        if (BenefitClassifier.availableChristmasDiscount(visitDate)) {
            store[Benefit.CHRISTMAS] = BenefitClassifier.discountChristmas(visitDate)
        }
    }
}