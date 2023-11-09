package christmas

import christmas.constants.Exception
import christmas.view.input.InputConverter
import christmas.view.input.InputValidator
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.ValueSource

class InputTest {

    @Test
    fun `방문할 날짜에 아무 값도 입력하지 않으면 예외가 발생한다`() {
        Assertions.assertThatThrownBy { InputValidator.checkVisitDate("") }
            .isInstanceOf(IllegalArgumentException::class.java)
            .hasMessage(Exception.VISIT_DATE.toString())
    }

    @ParameterizedTest
    @ValueSource(strings = ["9000000000", " ", "10 ", "abc"])
    fun `방문할 날짜에 문자나 INT의 범위에 벗어나는 숫자를 입력하면 예외가 발생한다`(inputDate: String) {
        Assertions.assertThatThrownBy { InputValidator.checkVisitDate(inputDate) }
            .isInstanceOf(IllegalArgumentException::class.java)
            .hasMessage(Exception.VISIT_DATE.toString())
    }

    @ParameterizedTest
    @ValueSource(strings = ["10", "1", "31"])
    fun `방문한 날짜를 올바르게 입력하면 예외가 발생하지 않는다`(inputDate: String) {
        Assertions.assertThatCode { InputValidator.checkVisitDate(inputDate) }
            .doesNotThrowAnyException()
    }

    @Test
    fun `주문할 메뉴에 아무 값도 입력하지 않으면 예외가 발생한다`() {
        Assertions.assertThatThrownBy { InputValidator.checkOrderMenu("") }
            .isInstanceOf(IllegalArgumentException::class.java)
            .hasMessage(Exception.ORDER_MENU.toString())
    }

    @ParameterizedTest
    @ValueSource(strings = ["티본스테이크-1,", "양송이수프-1,바비큐립2", "해산물파스타-1,아이스크림", "티본스테이크--1", "해산물파스타-1, 제로콜라2"])
    fun `주문할 메뉴의 형식을 맞추지 않으면 예외가 발생한다`(inputOrder: String) {
        Assertions.assertThatThrownBy { InputValidator.checkOrderMenu(inputOrder) }
            .isInstanceOf(IllegalArgumentException::class.java)
            .hasMessage(Exception.ORDER_MENU.toString())
    }

    @ParameterizedTest
    @ValueSource(strings = ["티본스테이크-1", "양송이수프-1,초코케이크-1,제로콜라-2"])
    fun `주문할 메뉴를 올바르게 입력하면 예외가 발생하지 않는다`(inputDate: String) {
        Assertions.assertThatCode { InputValidator.checkOrderMenu(inputDate) }
            .doesNotThrowAnyException()
    }
}