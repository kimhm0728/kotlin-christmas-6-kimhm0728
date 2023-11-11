package christmas

import christmas.model.benefit.BenefitStoreBuilder
import christmas.model.ordermenu.OrderMenu
import christmas.model.VisitDate
import christmas.model.ordermenu.OrderMenuBuilder
import christmas.service.PriceCalculator
import christmas.utils.retryWhileNoException
import christmas.view.input.InputView
import christmas.view.output.OutputView

class ChristmasController {
    private val visitDate: VisitDate
    private val orderMenu: OrderMenu

    init {
        OutputView.printStartGreeting()
        visitDate = readyVisitDate()
        orderMenu = readyOrderMenu()
    }

    private fun readyVisitDate() = retryWhileNoException {
        val inputDate = InputView.inputVisitDate()
        VisitDate(inputDate.toInt())
    }

    private fun readyOrderMenu() = retryWhileNoException {
        val inputOrder = InputView.inputOrderMenu()
        OrderMenuBuilder(inputOrder).create()
    }

    fun run() = with(OutputView) {
        printEventPreviewInfo()
        printOrderMenuItems(orderMenu)
        printTotalPriceBeforeDiscount(orderMenu)

        val benefitStore = BenefitStoreBuilder.create(visitDate, orderMenu)
        val totalBenefitPrice = PriceCalculator.getTotalBenefitPrice(benefitStore)
        val paymentPrice = PriceCalculator.getPaymentPrice(benefitStore, orderMenu)

        printBenefits(benefitStore, totalBenefitPrice, paymentPrice)
    }
}