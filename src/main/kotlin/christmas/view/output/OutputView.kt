package christmas.view.output

import christmas.constants.Benefit
import christmas.constants.Benefit.PRESENT
import christmas.constants.Print
import christmas.model.ordermenu.OrderMenu
import christmas.model.Price
import christmas.model.badge.BadgeStore
import christmas.model.benefit.BenefitStore
import christmas.model.discount.DiscountStore
import christmas.model.present.PresentStore

object OutputView {
    fun printStartGreeting() = println("안녕하세요! 우테코 식당 12월 이벤트 플래너입니다.")

    fun printEventPreviewInfo() {
        println("12월 26일에 우테코 식당에서 받을 이벤트 혜택 미리 보기!")
        lineBreak()
    }

    fun printOrderMenuItems(orderMenu: OrderMenu) {
        println("<주문 메뉴>")
        orderMenu.forEach { menu, count -> printMenuCount(menu, count) }
        lineBreak()
    }

    fun printTotalPriceBeforeDiscount(orderMenu: OrderMenu) {
        println("<할인 전 총주문 금액>")
        printPositivePrice(orderMenu.totalPrice)
        lineBreak()
    }

    fun printBenefits(benefitStore: BenefitStore, totalBenefitPrice: Price, paymentPrice: Price) =
        with(benefitStore) {
            printPresentMenu(presentStore)
            printBenefitHistory(discountStore, presentStore)
            printTotalBenefitPrice(totalBenefitPrice)
            printPaymentPrice(paymentPrice)
            printEventBadge(badgeStore)
        }

    private fun printPresentMenu(presentStore: PresentStore) {
        println("<증정 메뉴>")
        if (presentStore.price.isEmpty()) {
            printNoBenefit()
            return
        }

        println(Print.PRESENT_MENU)
        lineBreak()
    }

    private fun printBenefitHistory(discountStore: DiscountStore, presentStore: PresentStore) {
        println("<혜택 내역>")
        if (discountStore.isEmpty() && presentStore.price.isEmpty()) {
            printNoBenefit()
            return
        }

        printDiscountBenefit(discountStore)
        printPresentBenefit(presentStore)
        lineBreak()
    }

    private fun printNoBenefit() {
        println(Print.NO_BENEFIT)
        lineBreak()
    }

    private fun printDiscountBenefit(discountStore: DiscountStore) {
        discountStore.forEach { benefit, price ->
            printNegativePriceWithBenefit(benefit, price)
        }
    }

    private fun printPresentBenefit(presentStore: PresentStore) {
        if (presentStore.price.isEmpty()) return
        printNegativePriceWithBenefit(PRESENT, presentStore.price)
    }

    private fun printTotalBenefitPrice(totalBenefitPrice: Price) {
        println("<총혜택 금액>")
        if (totalBenefitPrice.isEmpty()) printPositivePrice(totalBenefitPrice)
        else printNegativePrice(totalBenefitPrice)

        lineBreak()
    }

    private fun printPaymentPrice(paymentPrice: Price) {
        println("<할인 후 예상 결제 금액>")
        printPositivePrice(paymentPrice)
        lineBreak()
    }

    private fun printEventBadge(badgeStore: BadgeStore) {
        println("<12월 이벤트 배지>")
        println(badgeStore)
    }

    private fun printMenuCount(menu: String, count: Int) = println("$menu ${count}개")
    private fun printNegativePriceWithBenefit(benefit: Benefit, price: Price) = println("$benefit: -${price}원")
    private fun printNegativePrice(price: Price) = println("-${price}원")
    private fun printPositivePrice(price: Price) = println("${price}원")
    private fun lineBreak() = println()
}