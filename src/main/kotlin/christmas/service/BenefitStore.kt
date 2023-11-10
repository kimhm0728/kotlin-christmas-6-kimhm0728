package christmas.service

import christmas.constants.Badge
import christmas.constants.Benefit
import christmas.constants.Constants.EVENT_STANDARD
import christmas.constants.menu.MenuType
import christmas.model.OrderMenu
import christmas.model.VisitDate

class BenefitStore(private val visitDate: VisitDate, private val orderMenu: OrderMenu) {
    private val store = mutableMapOf<Benefit, Int>()
    var badge = Badge.NO_BADGE
    var totalBenefitPrice = 0
        private set

    init {
        if (orderMenu.totalPrice >= EVENT_STANDARD.value) {
            applyBenefit()
            badge = BadgeApplier.getBadge(totalBenefitPrice)
        }
    }

    private fun applyBenefit() {
        applyDiscount()
        applyPresent()
    }

    private fun applyDiscount() {
        if (DiscountApplier.availableChristmasDiscount(visitDate)) {
            addBenefit(Benefit.CHRISTMAS, DiscountApplier.discountChristmas(visitDate))
        }

        if (DiscountApplier.isWeekend(visitDate)) {
            getMenuTypeCount(MenuType.MAIN).let { menuTypeCount ->
                if (menuTypeCount != 0) {
                    addBenefit(Benefit.WEEKEND, DiscountApplier.discountWeek(menuTypeCount))
                }
            }
        } else {
            getMenuTypeCount(MenuType.DESSERT).let { menuTypecount ->
                if (menuTypecount != 0) {
                    addBenefit(Benefit.WEEKDAY, DiscountApplier.discountWeek(menuTypecount))
                }
            }
        }

        if (DiscountApplier.availableSpecialDiscount(visitDate)) {
            addBenefit(Benefit.SPECIAL, DiscountApplier.discountSpecial())
        }
    }

    private fun applyPresent() {
        if (PresentApplier.availablePresent(orderMenu.totalPrice)) {
            addBenefit(Benefit.PRESENT, PresentApplier.getPresent())
        }
    }

    private fun addBenefit(benefit: Benefit, discount: Int) {
        store[benefit] = discount
        totalBenefitPrice += discount
    }

    private fun getMenuTypeCount(menuType: MenuType) =
        orderMenu.menuTypeStore.getOrDefault(menuType, 0)

    fun get(benefit: Benefit) =
        store.getOrDefault(benefit, 0)

    fun forEach(action: (Benefit, Int) -> Unit) =
        store.forEach { (benefit, discount) ->
            action(benefit, discount)
        }

    fun isEmpty() = store.isEmpty()

    fun getPaymentPrice(): Int {
        if (store.containsKey(Benefit.PRESENT)) {
            return orderMenu.totalPrice - totalBenefitPrice + PresentApplier.getPresent()
        }
        return orderMenu.totalPrice - totalBenefitPrice
    }

}