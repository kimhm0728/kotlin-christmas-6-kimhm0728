package christmas.model.store

import christmas.constants.Benefit

class DiscountStore(private val store: Map<Benefit, Int>, val totalPrice: Int) {
    fun get(benefit: Benefit) =
        store.getOrDefault(benefit, 0)

    fun forEach(action: (Benefit, Int) -> Unit) =
        store.forEach { (benefit, discount) ->
            action(benefit, discount)
        }

    fun isEmpty() = store.isEmpty()
}