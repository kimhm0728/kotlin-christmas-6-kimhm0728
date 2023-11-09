package christmas.view.input

import camp.nextstep.edu.missionutils.Console
import christmas.model.VisitDate

object InputView {
    private val validator = InputValidator
    private val converter = InputConverter

    fun inputVisitDate(): VisitDate {
        println("12월 중 식당 예상 방문 날짜는 언제인가요? (숫자만 입력해 주세요!)")
        val inputDate = Console.readLine()
        validator.checkVisitDate(inputDate)

        return converter.convertToVisitDate(inputDate)
    }
}