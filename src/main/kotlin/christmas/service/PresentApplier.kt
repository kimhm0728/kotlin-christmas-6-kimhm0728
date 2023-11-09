package christmas.service

private const val PRESENT_DISCOUNT = 120000

object PresentApplier {
    fun availablePresent(totalPrice: Int) =
        totalPrice >= PRESENT_DISCOUNT

    fun givePresent() = PRESENT_DISCOUNT
}