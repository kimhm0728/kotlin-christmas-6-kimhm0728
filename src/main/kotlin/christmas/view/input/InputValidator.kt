package christmas.view.input

import christmas.constants.Exception

object InputValidator {
    private val orderMenuRegex = "^[가-힣]+-\\d+$".toRegex()

    fun checkVisitDate(inputDate: String) {
        validateEmpty(inputDate, Exception.VISIT_DATE.toString())
        validateInt(inputDate, Exception.VISIT_DATE.toString())
    }

    fun checkOrderMenu(inputOrder: String) =
        inputOrder.toOrderMenuItems().forEach { menu ->
            validateEmpty(menu, Exception.ORDER_MENU.toString())
            validateRegex(menu, Exception.ORDER_MENU.toString())
        }

    private fun validateEmpty(input: String, errorMsg: String) =
        require(input.isNotEmpty()) { errorMsg }

    private fun validateInt(input: String, errorMsg: String) =
        require(input.toIntOrNull() != null) { errorMsg }

    private fun validateRegex(input: String, errorMsg: String) =
        require(orderMenuRegex.matches(input)) { errorMsg }

    private fun String.toOrderMenuItems() = this.split(",")
}