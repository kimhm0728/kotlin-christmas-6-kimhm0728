package christmas.view.input

import christmas.constants.Exception

object InputValidator {
    fun checkVisitDate(inputDate: String) {
        with(Exception.VISIT_DATE.toString()) {
            validateEmpty(inputDate, this)
            validateInt(inputDate, this)
        }
    }

    private fun validateEmpty(input: String, errorMsg: String) {
        require(input.isNotEmpty()) { errorMsg }
    }

    private fun validateInt(input: String, errorMsg: String) {
        require(input.toIntOrNull() != null) { errorMsg }
    }
}