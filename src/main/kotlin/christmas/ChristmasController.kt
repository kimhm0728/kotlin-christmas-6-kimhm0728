package christmas

import christmas.model.OrderMenu
import christmas.model.VisitDate
import christmas.service.BenefitStore
import christmas.service.OrderMenuGenerator
import christmas.utils.retryWhileNoException
import christmas.view.input.InputView
import christmas.view.output.OutputView

class ChristmasController {

    fun run() {
        OutputView.printStartGreeting()

        val visitDate = retryWhileNoException { InputView.inputVisitDate() }.run {
            VisitDate(this.toInt())
        }

        val orderMenu = retryWhileNoException {
            val inputOrder = InputView.inputOrderMenu()
            OrderMenuGenerator(inputOrder).createOrderMenu()
        }

        OutputView.printEventPreviewInfo()
        OutputView.printOrderMenus(orderMenu)
        OutputView.printTotalPriceBeforeDiscount(orderMenu)

        val benefitStore = BenefitStore(visitDate, orderMenu)
        OutputView.printBenefits(benefitStore)
    }
}