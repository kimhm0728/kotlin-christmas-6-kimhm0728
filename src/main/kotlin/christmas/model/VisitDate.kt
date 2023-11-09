package christmas.model

import christmas.constants.Constants.DATE_START
import christmas.constants.Constants.DATE_END
import christmas.constants.Exception

class VisitDate(private val date: Int) {
    init {
        require(date in DATE_START.value..DATE_END.value) { Exception.VISIT_DATE }
    }
}