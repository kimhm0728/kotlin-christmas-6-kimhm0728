package christmas.model.store

class BenefitStore(
    val discountStore: DiscountStore,
    val presentStore: PresentStore,
    val badgeStore: BadgeStore,
    private val totalOrderPrice: Int
) {
    fun getTotalBenefitPrice() = discountStore.totalPrice + presentStore.price

    fun getPaymentPrice() = totalOrderPrice - discountStore.totalPrice
}