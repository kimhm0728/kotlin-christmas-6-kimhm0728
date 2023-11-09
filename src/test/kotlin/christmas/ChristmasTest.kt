package christmas

import christmas.model.VisitDate
import org.assertj.core.api.Assertions
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.ValueSource
import christmas.constants.Exception
import christmas.model.OrderMenu
import christmas.service.OrderMenuGenerator
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class ChristmasTest {

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

    @ParameterizedTest
    @ValueSource(strings = ["토마토파스타-10", "해산물파스타-1,제로사이다-2"])
    fun `메뉴판에 없는 메뉴를 입력하면 예외가 발생한다`(inputOrder: String) {
        Assertions.assertThatThrownBy { OrderMenuGenerator(inputOrder) }
            .isInstanceOf(IllegalArgumentException::class.java)
            .hasMessage(Exception.ORDER_MENU.toString())
    }

    @ParameterizedTest
    @ValueSource(strings = ["티본스테이크-0", "타파스-50000000000"])
    fun `메뉴의 개수가 1보다 작거나 Int의 범위를 벗어나면 예외가 발생한다`(inputOrder: String) {
        Assertions.assertThatThrownBy { OrderMenuGenerator(inputOrder) }
            .isInstanceOf(IllegalArgumentException::class.java)
            .hasMessage(Exception.ORDER_MENU.toString())
    }

    @Test
    fun `총 메뉴의 개수가 20보다 많으면 예외가 발생한다`() {
        // given
        val case = "양송이수프-2,티본스테이크-5,바비큐립-5,제로콜라-10"

        // when, then
        Assertions.assertThatThrownBy { OrderMenuGenerator(case) }
            .isInstanceOf(IllegalArgumentException::class.java)
            .hasMessage(Exception.ORDER_MENU.toString())
    }

    @Test
    fun `중복된 메뉴를 주문하면 예외가 발생한다`() {
        // given
        val case = "양송이수프-2,티본스테이크-1,양송이수프-1"

        // when, then
        Assertions.assertThatThrownBy { OrderMenuGenerator(case) }
            .isInstanceOf(IllegalArgumentException::class.java)
            .hasMessage(Exception.ORDER_MENU.toString())
    }

    @Test
    fun `음료만 주문하면 예외가 발생한다`() {
        // given
        val case = "제로콜라-2,샴페인-4"

        // when, then
        Assertions.assertThatThrownBy { OrderMenuGenerator(case) }
            .isInstanceOf(IllegalArgumentException::class.java)
            .hasMessage(Exception.ORDER_MENU.toString())
    }

    @ParameterizedTest
    @ValueSource(strings = ["양송이수프-1,티본스테이크-2,바비큐립-1,레드와인-1", "제로콜라-10,해산물파스타-10"])
    fun `요구사항에 맞게 주문하면 예외가 발생하지 않는다`(inputOrder: String) {
        // given

        // when
        val result = OrderMenuGenerator(inputOrder).createOrderMenu()

        // then
        assertThat(result).isInstanceOf(OrderMenu::class.java)
    }
}