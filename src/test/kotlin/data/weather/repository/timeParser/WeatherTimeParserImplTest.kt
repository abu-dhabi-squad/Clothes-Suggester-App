package data.weather.repository.timeParser

import com.google.common.truth.Truth.assertThat
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.CsvSource
import java.time.format.DateTimeParseException
import kotlin.test.BeforeTest

class WeatherTimeParserImplTest {
    private lateinit var weatherTimeParser: WeatherTimeParserImpl

    @BeforeTest
    fun setup() {
        weatherTimeParser = WeatherTimeParserImpl()
    }

    @ParameterizedTest
    @CsvSource(
        "2025-05-05T15:00,15",
        "2025-05-09T08:00,8",
        "2025-05-11T23:00,23"
    )
    fun `getHourFromTimeString should return hour when the time is in righ format`(time: String, hour: Int) {
        //Given & When
        val res = weatherTimeParser.getHourFromTimeString(time)
        //Then
        assertThat(res).isEqualTo(hour)
    }

    @ParameterizedTest
    @CsvSource(
        "2025-05-0515:00",
        "2025-05-09T8:00",
        "2025-5-11T23:00",
        "11-5-2025T23:00"
    )
    fun `getHourFromTimeString should throw DateTimeParseException when the time is in wrong format`(time: String) {
        //Given
        val time = time
        //When & Then
        org.junit.jupiter.api.assertThrows<DateTimeParseException> {
            weatherTimeParser.getHourFromTimeString(time)
        }
    }
}