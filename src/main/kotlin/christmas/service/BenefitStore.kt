package christmas.service

import christmas.constants.Benefit
import christmas.constants.menu.MenuType
import christmas.model.OrderMenu
import christmas.model.VisitDate

class BenefitStore(private val visitDate: VisitDate, private val orderMenu: OrderMenu) {
    val store = mutableMapOf<Benefit, Int>()

    fun applyBenefit() {
        applyDiscount()
        applyPresent()
    }

    private fun applyDiscount() {
        if (DiscountApplier.availableChristmasDiscount(visitDate)) {
            store[Benefit.CHRISTMAS] = DiscountApplier.discountChristmas(visitDate)
        }

        if (DiscountApplier.isWeekend(visitDate)) {
            store[Benefit.WEEKEND] = DiscountApplier.discountWeek(orderMenu.menuTypeStore[MenuType.MAIN]!!)
        } else {
            store[Benefit.WEEKDAY] = DiscountApplier.discountWeek(orderMenu.menuTypeStore[MenuType.DESSERT]!!)
        }

        if (DiscountApplier.availableSpecialDiscount(visitDate)) {
            store[Benefit.SPECIAL] = DiscountApplier.discountSpecial()
        }
    }

    private fun applyPresent() {
        if (PresentApplier.availablePresent(orderMenu.totalPrice)) {
            store[Benefit.PRESENT] = PresentApplier.givePresent()
        }
    }
}