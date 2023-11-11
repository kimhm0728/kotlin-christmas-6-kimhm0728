package christmas.service

import christmas.model.OrderMenu
import christmas.model.BenefitStore

object PriceCalculator {
    fun getTotalBenefitPrice(benefitStore: BenefitStore) =
        benefitStore.discountStore.totalPrice + benefitStore.presentStore.price

    fun getPaymentPrice(benefitStore: BenefitStore, orderMenu: OrderMenu) =
        orderMenu.totalPrice - benefitStore.discountStore.totalPrice
}