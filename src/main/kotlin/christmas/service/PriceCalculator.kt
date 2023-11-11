package christmas.service

import christmas.model.ordermenu.OrderMenu
import christmas.model.discount.DiscountStore
import christmas.model.present.PresentStore

object PriceCalculator {
    fun getTotalBenefitPrice(discountStore: DiscountStore, presentStore: PresentStore) =
        discountStore.totalPrice + presentStore.price

    fun getPaymentPrice(discountStore: DiscountStore, orderMenu: OrderMenu) =
        orderMenu.totalPrice - discountStore.totalPrice
}