package christmas.model.discount

import christmas.constants.Benefit.WEEKEND
import christmas.constants.Benefit.WEEKDAY
import christmas.constants.Benefit.SPECIAL
import christmas.constants.Benefit.CHRISTMAS
import christmas.constants.Benefit
import christmas.constants.menu.MenuType.MAIN
import christmas.constants.menu.MenuType.DESSERT
import christmas.model.OrderMenu
import christmas.model.Price
import christmas.model.VisitDate

class DiscountStoreBuilder(private val visitDate: VisitDate, private val orderMenu: OrderMenu) {
    private val manager = DiscountManager
    private val store = mutableMapOf<Benefit, Price>()
    private var totalDiscountPrice = Price()

    init {
        applyDiscount()
    }

    private fun applyDiscount() {
        if (!manager.isDiscount(orderMenu.totalPrice)) return

        applyChristmasDiscount()
        applyWeekDiscount()
        applySpecialDiscount()
    }

    private fun applyChristmasDiscount() =
        discount(CHRISTMAS, manager.getChristmasPrice(visitDate))

    private fun applyWeekDiscount() {
        if (manager.isWeekend(visitDate)) {
            discount(WEEKEND, manager.getWeekPrice(orderMenu, MAIN))
        } else {
            discount(WEEKDAY, manager.getWeekPrice(orderMenu, DESSERT))
        }
    }

    private fun applySpecialDiscount() =
        discount(SPECIAL, manager.getSpecialPrice(visitDate))

    private fun discount(benefit: Benefit, price: Int) {
        if (price == 0) return
        store[benefit] = Price(price)
        totalDiscountPrice.add(price)
    }

    fun create() = DiscountStore(store, totalDiscountPrice)
}