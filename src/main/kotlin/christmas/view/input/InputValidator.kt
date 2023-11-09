package christmas.view.input

import christmas.constants.Exception

object InputValidator {
    private val orderMenuRegex = "^[가-힣]+-\\d+$".toRegex()

    fun checkVisitDate(inputDate: String) =
        with(Exception.VISIT_DATE.toString()) {
            validateEmpty(inputDate, this)
            validateInt(inputDate, this)
        }

    fun checkOrderMenu(inputOrder: String) =
        with(Exception.VISIT_ORDER.toString()) {
            inputOrder.toOrderMenus().forEach { menus ->
                validateEmpty(menus, this)
                validateRegex(menus, this)
            }
        }

    private fun validateEmpty(input: String, errorMsg: String) =
        require(input.isNotEmpty()) { errorMsg }

    private fun validateInt(input: String, errorMsg: String) =
        require(input.toIntOrNull() != null) { errorMsg }

    private fun validateRegex(input: String, errorMsg: String) =
        require(orderMenuRegex.matches(input)) { errorMsg }

    private fun String.toOrderMenus() = this.split(",")

    private fun String.toMenuAndCount() = this.split("-")


}