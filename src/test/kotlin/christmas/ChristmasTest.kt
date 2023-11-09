package christmas

import christmas.model.VisitDate
import org.assertj.core.api.Assertions
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.ValueSource
import christmas.constants.Exception

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
    fun `1~31 범위에 포함되는 VisitDate 객체를 생성하면 예외가 발생한다`(inputDate: Int) {
        Assertions.assertThatCode { VisitDate(inputDate) }
            .doesNotThrowAnyException()
    }
}