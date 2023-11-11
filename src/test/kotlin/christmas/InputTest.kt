package christmas

import christmas.constants.Exception
import christmas.model.VisitDate
import christmas.model.ordermenu.OrderMenu
import christmas.model.ordermenu.OrderMenuBuilder
import christmas.view.input.InputValidator
import org.assertj.core.api.Assertions
import org.assertj.core.api.Assertions.assertThat
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

    @ParameterizedTest
    @ValueSource(ints = [0, -20, 50])
    fun `1~31 범위에 벗어나는 VisitDate 객체를 생성하면 예외가 발생한다`(inputDate: Int) {
        Assertions.assertThatThrownBy { VisitDate(inputDate) }
            .isInstanceOf(IllegalArgumentException::class.java)
            .hasMessage(Exception.VISIT_DATE.toString())
    }

    @ParameterizedTest
    @ValueSource(ints = [1, 10, 30])
    fun `1~31 범위에 포함되는 VisitDate 객체를 생성하면 예외가 발생하지 않는다`(inputDate: Int) {
        Assertions.assertThatCode { VisitDate(inputDate) }
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

    @ParameterizedTest
    @ValueSource(strings = ["토마토파스타-10", "해산물파스타-1,제로사이다-2"])
    fun `메뉴판에 없는 메뉴를 입력하면 예외가 발생한다`(inputOrder: String) {
        Assertions.assertThatThrownBy { OrderMenuBuilder(inputOrder) }
            .isInstanceOf(IllegalArgumentException::class.java)
            .hasMessage(Exception.ORDER_MENU.toString())
    }

    @ParameterizedTest
    @ValueSource(strings = ["티본스테이크-0", "타파스-50000000000"])
    fun `메뉴의 개수가 1보다 작거나 Int의 범위를 벗어나면 예외가 발생한다`(inputOrder: String) {
        Assertions.assertThatThrownBy { OrderMenuBuilder(inputOrder) }
            .isInstanceOf(IllegalArgumentException::class.java)
            .hasMessage(Exception.ORDER_MENU.toString())
    }

    @Test
    fun `총 메뉴의 개수가 20보다 많으면 예외가 발생한다`() {
        // given
        val inputOrder = "양송이수프-2,티본스테이크-5,바비큐립-5,제로콜라-10"

        // when, then
        Assertions.assertThatThrownBy { OrderMenuBuilder(inputOrder) }
            .isInstanceOf(IllegalArgumentException::class.java)
            .hasMessage(Exception.ORDER_MENU_MAX_SIZE.toString())
    }

    @Test
    fun `중복된 메뉴를 주문하면 예외가 발생한다`() {
        // given
        val inputOrder = "양송이수프-2,티본스테이크-1,양송이수프-1"

        // when, then
        Assertions.assertThatThrownBy { OrderMenuBuilder(inputOrder) }
            .isInstanceOf(IllegalArgumentException::class.java)
            .hasMessage(Exception.ORDER_MENU.toString())
    }

    @Test
    fun `음료만 주문하면 예외가 발생한다`() {
        // given
        val inputOrder = "제로콜라-2,샴페인-4"

        // when, then
        Assertions.assertThatThrownBy { OrderMenuBuilder(inputOrder) }
            .isInstanceOf(IllegalArgumentException::class.java)
            .hasMessage(Exception.ORDER_MENU_DRINK_ONLY.toString())
    }

    @ParameterizedTest
    @ValueSource(strings = ["양송이수프-1,티본스테이크-2,바비큐립-1,레드와인-1", "제로콜라-10,해산물파스타-10"])
    fun `요구사항에 맞게 주문하면 예외가 발생하지 않는다`(inputOrder: String) {
        // given

        // when
        val result = OrderMenuBuilder(inputOrder).create()

        // then
        assertThat(result).isInstanceOf(OrderMenu::class.java)
    }
}