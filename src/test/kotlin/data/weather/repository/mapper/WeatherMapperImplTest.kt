package data.weather.repository.mapper

import com.google.common.truth.Truth.assertThat
import helper.createDtoWeather
import logic.model.HourlyTemperature
import logic.model.Weather
import logic.model.WeatherCondition
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import kotlin.test.Test

class WeatherMapperImplTest{
    private lateinit var weatherMapper: WeatherMapper

    @BeforeEach
    fun setup() {
        weatherMapper = WeatherMapperImpl()
    }

    @Test
    fun `mapDtoToWeather should return weather`() {
        val dtoWeather = createDtoWeather(listOf(HourlyTemperature(10.0, 12)), WeatherCondition.WINDY)
        val expectedWeather = Weather(listOf(HourlyTemperature(10.0, 12)), WeatherCondition.WINDY)
        val actualWeather = weatherMapper.mapDtoToWeather(dtoWeather)
        assertThat(actualWeather).isEqualTo(expectedWeather)
    }
}