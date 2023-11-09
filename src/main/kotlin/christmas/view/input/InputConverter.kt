package christmas.view.input

import christmas.model.VisitDate

object InputConverter {
    fun convertToVisitDate(inputDate: String) = VisitDate(inputDate.toInt())
}