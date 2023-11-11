package christmas.service

import christmas.model.ordermenu.OrderMenu
import christmas.model.benefit.BenefitStore

object PriceCalculator {
    fun getTotalBenefitPrice(benefitStore: BenefitStore) =
        benefitStore.discountStore.totalPrice + benefitStore.presentStore.price

    fun getPaymentPrice(benefitStore: BenefitStore, orderMenu: OrderMenu) =
        orderMenu.totalPrice - benefitStore.discountStore.totalPrice
}