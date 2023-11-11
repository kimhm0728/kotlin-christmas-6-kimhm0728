package christmas.model

import christmas.model.badge.BadgeStoreBuilder
import christmas.model.discount.DiscountStoreBuilder
import christmas.model.present.PresentStoreBuilder

object BenefitStoreBuilder {
    fun create(visitDate: VisitDate, orderMenu: OrderMenu): BenefitStore {
        val discountStore = DiscountStoreBuilder(visitDate, orderMenu).create()
        val presentStore = PresentStoreBuilder.create(orderMenu.totalPrice)
        val badgeStore = BadgeStoreBuilder.create(discountStore.totalPrice + presentStore.price)

        return BenefitStore(discountStore, presentStore, badgeStore)
    }
}