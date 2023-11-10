package christmas.view.output

import christmas.constants.Benefit
import christmas.constants.Print
import christmas.model.OrderMenu
import christmas.service.BenefitStore
import christmas.utils.convertWithDigitComma

object OutputView {
    fun printStartGreeting() {
        println("안녕하세요! 우테코 식당 12월 이벤트 플래너입니다.")
    }

    fun printEventPreviewInfo() {
        println("12월 26일에 우테코 식당에서 받을 이벤트 혜택 미리 보기!")
        lineBreak()
    }

    fun printOrderMenus(orderMenu: OrderMenu) {
        println("<주문 메뉴>")
        orderMenu.menuStore.forEach { menu ->
            println("${menu.key} ${menu.value}개")
        }
        lineBreak()
    }

    fun printTotalPriceBeforeDiscount(orderMenu: OrderMenu) {
        println("<할인 전 총주문 금액>")
        println(orderMenu.totalPrice.convertPositivePriceForm())
        lineBreak()
    }

    fun printBenefits(benefitStore: BenefitStore) {
        printPresentMenu(benefitStore)
        printBenefitHistory(benefitStore)
        printTotalBenefitPrice(benefitStore)
        printPaymentPrice(benefitStore)
        printEventBadge(benefitStore)
    }

    private fun printPresentMenu(benefitStore: BenefitStore) {
        println("<증정 메뉴>")
        if (benefitStore.get(Benefit.PRESENT) != 0) {
            println(Print.PRESENT_MENU)
            lineBreak()
            return
        }
        println(Print.NO_BENEFIT)
        lineBreak()
    }

    private fun printBenefitHistory(benefitStore: BenefitStore) {
        println("<혜택 내역>")
        if (benefitStore.isEmpty()) {
            println(Print.NO_BENEFIT)
            lineBreak()
            return
        }

        benefitStore.forEach { benefit, discount ->
            println("$benefit: ${discount.convertNegativePriceForm()}")
        }
        lineBreak()
    }

    private fun printTotalBenefitPrice(benefitStore: BenefitStore) {
        println("<총혜택 금액>")
        val totalBenefitPrice = benefitStore.totalBenefitPrice

        if (totalBenefitPrice == 0) {
            println(totalBenefitPrice.convertPositivePriceForm())
            lineBreak()
            return
        }

        println(totalBenefitPrice.convertNegativePriceForm())
        lineBreak()
    }

    private fun printPaymentPrice(benefitStore: BenefitStore) {
        println("<할인 후 예상 결제 금액>")
        println(benefitStore.getPaymentPrice().convertPositivePriceForm())
        lineBreak()
    }

    private fun printEventBadge(benefitStore: BenefitStore) {
        println("<12월 이벤트 배지>")
        println(benefitStore.badge)
    }

    private fun Int.convertPositivePriceForm() = "${this.convertWithDigitComma()}원"
    private fun Int.convertNegativePriceForm() = "-${this.convertWithDigitComma()}원"

    private fun lineBreak() = println()
}