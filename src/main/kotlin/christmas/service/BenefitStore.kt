package christmas.service

import christmas.constants.Benefit
import christmas.constants.menu.MenuType
import christmas.model.OrderMenu
import christmas.model.VisitDate

class BenefitStore(private val visitDate: VisitDate, private val orderMenu: OrderMenu) {
    private val classifier = BenefitClassifier
    val store = mutableMapOf<Benefit, Int>()

    fun applyBenefit() {
        if (classifier.availableChristmasDiscount(visitDate)) {
            store[Benefit.CHRISTMAS] = classifier.discountChristmas(visitDate)
        }

        if (classifier.isWeekend(visitDate)) {
            store[Benefit.WEEKEND] = classifier.discountWeek(orderMenu.menuTypeStore[MenuType.MAIN]!!)
        } else {
            store[Benefit.WEEKEND] = classifier.discountWeek(orderMenu.menuTypeStore[MenuType.DESSERT]!!)
        }
    }
}