package christmas

import christmas.model.VisitDate
import org.assertj.core.api.Assertions
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.ValueSource
import christmas.constants.Exception
import christmas.model.OrderMenu
import christmas.service.DiscountApplier
import christmas.service.OrderMenuGenerator
import christmas.service.PresentApplier
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource

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

    @ParameterizedTest
    @MethodSource("크리스마스 디데이 할인 여부에 대한 테스트 데이터")
    fun `1~25일 사이에 방문하면 크리스마스 디데이 할인을 받을 수 있다`(date: Int, expected: Boolean) {
        // given
        val case = VisitDate(date)

        // when
        val result = DiscountApplier.availableChristmasDiscount(case)

        // then
        assertThat(result).isEqualTo(expected)
    }

    @ParameterizedTest
    @MethodSource("크리스마스 디데이 할인 금액에 대한 테스트 데이터")
    fun `크리스마스가 다가올 수록 할인 금액이 증가한다`(date: Int, expected: Int) {
        // given
        val case = VisitDate(date)

        // when
        val result = DiscountApplier.discountChristmas(case)

        // then
        assertThat(result).isEqualTo(expected)
    }

    @ParameterizedTest
    @MethodSource("주말 여부에 대한 테스트 데이터")
    fun `방문 날짜가 주말인지 판별한다`(date: Int, expected: Boolean) {
        // given
        val case = VisitDate(date)

        // when
        val result = DiscountApplier.isWeekend(case)

        // then
        assertThat(result).isEqualTo(expected)
    }

    @ParameterizedTest
    @MethodSource("주중 할인에 대한 테스트 데이터")
    fun `메뉴의 개수만큼 할인을 적용한다`(menuCount: Int, expected: Int) {
        // given

        // when
        val result = DiscountApplier.discountWeek(menuCount)

        // then
        assertThat(result).isEqualTo(expected)
    }

    @ParameterizedTest
    @MethodSource("특별 할인 여부에 대한 테스트 데이터")
    fun `이벤트 달력에 별이 있으면 특별 할인을 받을 수 있다`(date: Int, expected: Boolean) {
        // given
        val case = VisitDate(date)

        // when
        val result = DiscountApplier.availableSpecialDiscount(case)

        // then
        assertThat(result).isEqualTo(expected)
    }

    @Test
    fun `특별 할인을 적용한다`() {
        // given

        // when
        val result = DiscountApplier.discountSpecial()

        // then
        assertThat(result).isEqualTo(1000)
    }

    @ParameterizedTest
    @MethodSource("증정 여부에 대한 테스트 데이터")
    fun `할인 전 총주문 금액이 12만 원 이상이면 샴페인 1개를 증정한다`(totalPrice: Int, expected: Boolean) {
        // given

        // when
        val result = PresentApplier.availablePresent(totalPrice)

        // then
        assertThat(result).isEqualTo(expected)
    }

    @Test
    fun `증정 이벤트를 적용한다`() {
        // given

        // when
        val result = PresentApplier.givePresent()

        // then
        assertThat(result).isEqualTo(120000)
    }

    companion object {
        @JvmStatic
        fun `크리스마스 디데이 할인 여부에 대한 테스트 데이터`() = listOf(
            Arguments.of(1, true),
            Arguments.of(10, true),
            Arguments.of(25, true),
            Arguments.of(30, false),
            Arguments.of(28, false)
        )

        @JvmStatic
        fun `크리스마스 디데이 할인 금액에 대한 테스트 데이터`() = listOf(
            Arguments.of(1, 1000),
            Arguments.of(25, 3400),
            Arguments.of(10, 1900)
        )

        @JvmStatic
        fun `주말 여부에 대한 테스트 데이터`() = listOf(
            Arguments.of(1, true),
            Arguments.of(15, true),
            Arguments.of(20, false),
            Arguments.of(6, false)
        )

        @JvmStatic
        fun `주중 할인에 대한 테스트 데이터`() = listOf(
            Arguments.of(1, 2023),
            Arguments.of(5, 2023 * 5),
            Arguments.of(3, 2023 * 3)
        )

        @JvmStatic
        fun `특별 할인 여부에 대한 테스트 데이터`() = listOf(
            Arguments.of(17, true),
            Arguments.of(25, true),
            Arguments.of(1, false),
            Arguments.of(20, false),
        )

        @JvmStatic
        fun `증정 여부에 대한 테스트 데이터`() = listOf(
            Arguments.of(120000, true),
            Arguments.of(300000, true),
            Arguments.of(50000, false),
            Arguments.of(15000, false)
        )
    }
}