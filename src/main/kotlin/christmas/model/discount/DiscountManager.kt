package christmas.model.discount

import christmas.constants.PriceConstants
import christmas.constants.menu.MenuType
import christmas.model.ordermenu.OrderMenu
import christmas.model.Price
import christmas.model.VisitDate
import christmas.service.DateClassifier

object DiscountManager {
    private val calculator = DiscountCalculator
    private val classifier = DateClassifier

    fun isDiscount(totalPrice: Price) =
        totalPrice >= PriceConstants.EVENT_STANDARD.price

    fun isWeekend(visitDate: VisitDate) = classifier.isWeekend(visitDate)

    fun getChristmasPrice(visitDate: VisitDate): Int {
        if (classifier.isBeforeChristmas(visitDate)) {
            return calculator.getChristmasPrice(visitDate)
        }
        return 0
    }

    fun getWeekPrice(orderMenu: OrderMenu, menuType: MenuType): Int {
        val menuTypeCount = orderMenu.getMenuTypeCount(menuType)
        return calculator.getWeekPrice(menuTypeCount)
    }

    fun getSpecialPrice(visitDate: VisitDate): Int {
        if (classifier.isSpecialDate(visitDate)) {
            return calculator.getSpecialPrice()
        }
        return 0
    }
}