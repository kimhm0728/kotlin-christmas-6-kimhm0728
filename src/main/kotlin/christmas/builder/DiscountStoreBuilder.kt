package christmas.builder

import christmas.constants.Benefit
import christmas.constants.Constants.EVENT_STANDARD
import christmas.constants.menu.MenuType
import christmas.model.OrderMenu
import christmas.model.VisitDate
import christmas.model.store.DiscountStore
import christmas.service.calculator.DiscountCalculator

class DiscountStoreBuilder(private val visitDate: VisitDate, private val orderMenu: OrderMenu) {
    private val store = mutableMapOf<Benefit, Int>()
    private var totalDiscountPrice = 0

    init {
        if (availableDiscount(orderMenu.totalPrice)) applyDiscount()
    }

    private fun availableDiscount(totalPrice: Int) =
        totalPrice >= EVENT_STANDARD.value

    private fun applyDiscount() {
        applyChristmasDiscount()
        applyWeekDiscount()
        applySpecialDiscount()
    }

    private fun applyChristmasDiscount() {
        if (DiscountCalculator.availableChristmasDiscount(visitDate)) {
            addDiscount(Benefit.CHRISTMAS, DiscountCalculator.discountChristmas(visitDate))
        }
    }

    private fun applyWeekDiscount() {
        if (DiscountCalculator.isWeekend(visitDate)) {
            orderMenu.getMenuTypeCount(MenuType.MAIN).let { menuTypeCount ->
                if (menuTypeCount != 0) {
                    addDiscount(Benefit.WEEKEND, DiscountCalculator.discountWeek(menuTypeCount))
                }
            }
        } else {
            orderMenu.getMenuTypeCount(MenuType.DESSERT).let { menuTypecount ->
                if (menuTypecount != 0) {
                    addDiscount(Benefit.WEEKDAY, DiscountCalculator.discountWeek(menuTypecount))
                }
            }
        }
    }

    private fun applySpecialDiscount() {
        if (DiscountCalculator.availableSpecialDiscount(visitDate)) {
            addDiscount(Benefit.SPECIAL, DiscountCalculator.discountSpecial())
        }
    }

    private fun addDiscount(benefit: Benefit, discount: Int) {
        store[benefit] = discount
        totalDiscountPrice += discount
    }

    fun createDiscountStore() = DiscountStore(store, totalDiscountPrice)
}