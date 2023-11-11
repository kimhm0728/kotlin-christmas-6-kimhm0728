package christmas

import christmas.constants.menu.MenuType
import christmas.model.VisitDate
import org.junit.jupiter.params.ParameterizedTest
import christmas.model.Price
import christmas.model.benefit.BenefitStoreBuilder
import christmas.model.discount.DiscountCalculator
import christmas.model.discount.DiscountManager
import christmas.model.ordermenu.OrderMenuBuilder
import christmas.service.DateClassifier
import christmas.service.MenuClassifier
import christmas.service.PriceCalculator
import christmas.utils.convertWithDigitComma
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource

class ChristmasTest {

    @ParameterizedTest
    @MethodSource("크리스마스 디데이 할인 여부에 대한 테스트 데이터")
    fun `1~25일 사이에 방문하면 크리스마스 디데이 할인을 받을 수 있다`(date: Int, expected: Boolean) {
        // given
        val case = VisitDate(date)

        // when
        val result = DateClassifier.isBeforeChristmas(case)

        // then
        assertThat(result).isEqualTo(expected)
    }

    @ParameterizedTest
    @MethodSource("주말 여부에 대한 테스트 데이터")
    fun `방문 날짜가 주말인지 판별한다`(date: Int, expected: Boolean) {
        // given
        val case = VisitDate(date)

        // when
        val result = DateClassifier.isWeekend(case)

        // then
        assertThat(result).isEqualTo(expected)
    }

    @ParameterizedTest
    @MethodSource("특별 할인 여부에 대한 테스트 데이터")
    fun `이벤트 달력에 별이 있으면 특별 할인을 받을 수 있다`(date: Int, expected: Boolean) {
        // given
        val case = VisitDate(date)

        // when
        val result = DateClassifier.isSpecialDate(case)

        // then
        assertThat(result).isEqualTo(expected)
    }

    @ParameterizedTest
    @MethodSource("크리스마스 디데이 할인 금액에 대한 테스트 데이터")
    fun `크리스마스가 다가올 수록 할인 금액이 증가한다`(date: Int, expected: Int) {
        // given
        val case = VisitDate(date)

        // when
        val result = DiscountCalculator.getChristmasPrice(case)

        // then
        assertThat(result).isEqualTo(expected)
    }

    @ParameterizedTest
    @MethodSource("주중 할인에 대한 테스트 데이터")
    fun `메뉴의 개수만큼 할인을 적용한다`(menuCount: Int, expected: Int) {
        // given

        // when
        val result = DiscountCalculator.getWeekPrice(menuCount)

        // then
        assertThat(result).isEqualTo(expected)
    }

    @Test
    fun `특별 할인을 적용한다`() {
        // given

        // when
        val result = DiscountCalculator.getSpecialPrice()

        // then
        assertThat(result).isEqualTo(1000)
    }

    @ParameterizedTest
    @MethodSource("이벤트 적용 조건에 대한 테스트 데이터")
    fun `총 주문 금액 10,000원 이상이면 이벤트를 적용한다`(totalOrderPrice: Int, expected: Boolean) {
        // given
        val price = Price(totalOrderPrice)

        // when
        val result = DiscountManager.isDiscount(price)

        // then
        assertThat(result).isEqualTo(expected)
    }

    @ParameterizedTest
    @MethodSource("메뉴 종류에 대한 테스트 데이터")
    fun `메뉴의 종류를 구한다`(menu: String, expected: MenuType) {
        // given

        // when
        val result = MenuClassifier.getMenuType(menu)

        // then
        assertThat(result).isEqualTo(expected)
    }

    @ParameterizedTest
    @MethodSource("메뉴 가격에 대한 테스트 데이터")
    fun `메뉴의 가격을 구한다`(menu: String, expected: Int) {
        // given

        // when
        val result = MenuClassifier.getMenuPrice(menu)

        // then
        assertThat(result).isEqualTo(expected)
    }

    @Test
    fun `총 혜택 금액을 구한다`() {
        // given
        val visitDate = VisitDate(16)
        val orderMenu = OrderMenuBuilder("시저샐러드-1,바비큐립-1,해산물파스타-2,초코케이크-1").create()
        val benefitStore = BenefitStoreBuilder.create(visitDate, orderMenu)

        // when
        val result = PriceCalculator.getTotalBenefitPrice(benefitStore.discountStore, benefitStore.presentStore)

        // then
        assertThat(result.toString()).isEqualTo(33569.convertWithDigitComma())
    }

    @Test
    fun `결제 금액을 구한다`() {
        // given
        val visitDate = VisitDate(16)
        val orderMenu = OrderMenuBuilder("시저샐러드-1,바비큐립-1,해산물파스타-2,초코케이크-1").create()
        val benefitStore = BenefitStoreBuilder.create(visitDate, orderMenu)

        // when
        val result = PriceCalculator.getPaymentPrice(benefitStore.discountStore, orderMenu)

        // then
        assertThat(result.toString()).isEqualTo(138431.convertWithDigitComma())
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
        fun `이벤트 적용 조건에 대한 테스트 데이터`() = listOf(
            Arguments.of(10000, true),
            Arguments.of(15000, true),
            Arguments.of(8500, false)
        )

        @JvmStatic
        fun `메뉴 종류에 대한 테스트 데이터`() = listOf(
            Arguments.of("양송이수프", MenuType.APPETIZER),
            Arguments.of("해산물파스타", MenuType.MAIN),
            Arguments.of("아이스크림", MenuType.DESSERT),
            Arguments.of("레드와인", MenuType.DRINK),
            Arguments.of("알리오올리오", MenuType.NOTHING)
        )

        @JvmStatic
        fun `메뉴 가격에 대한 테스트 데이터`() = listOf(
            Arguments.of("바비큐립", 54000),
            Arguments.of("초코케이크", 15000),
            Arguments.of("시저샐러드", 8000),
        )
    }
}