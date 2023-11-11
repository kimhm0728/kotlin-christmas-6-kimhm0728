package christmas.model.discount

import christmas.constants.Benefit
import christmas.model.Price

class DiscountStore(private val store: Map<Benefit, Price>, val totalPrice: Price) {
    fun get(benefit: Benefit) =
        store.getOrDefault(benefit, 0)

    fun forEach(action: (Benefit, Price) -> Unit) =
        store.forEach { (benefit, price) ->
            action(benefit, price)
        }

    fun isEmpty() = store.isEmpty()
}