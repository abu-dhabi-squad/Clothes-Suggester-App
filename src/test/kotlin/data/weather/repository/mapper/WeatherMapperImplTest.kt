package data.weather.repository.mapper

import com.google.common.truth.Truth.assertThat
import data.weather.model.Hourly
import data.weather.repository.timeParser.WeatherTimeParser
import helper.createDtoWeather
import io.mockk.every
import io.mockk.mockk
import logic.exception.UnKownWeatherCondition
import logic.model.HourlyTemperature
import logic.model.Weather
import logic.model.WeatherCondition
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.assertThrows
import kotlin.test.Test

class WeatherMapperImplTest {
    private lateinit var weatherMapper: WeatherMapper
    private lateinit var weatherTimeParser: WeatherTimeParser

    @BeforeEach
    fun setup() {
        weatherTimeParser = mockk(relaxed = true)
        weatherMapper = WeatherMapperImpl(weatherTimeParser)
    }

    @Test
    fun `mapDtoToWeather should return weather when data is valid`() {
        //Given
        every { weatherTimeParser.getHourFromTimeString(any()) } returns 15
        val dtoWeather = createDtoWeather(hourly = Hourly(listOf(10.0), listOf("2025-05-05T15:00"), listOf(0)),3)
        val expectedWeather = Weather(listOf(HourlyTemperature(10.0, 15)), WeatherCondition.OVERCAST)
        //When
        val actualWeather = weatherMapper.mapDtoToWeather(dtoWeather)
        //Then
        assertThat(actualWeather).isEqualTo(expectedWeather)
    }

    @Test
    fun `mapDtoToWeather should throw exception when weatherTimeParser throw exception`() {
        //Given
        every { weatherTimeParser.getHourFromTimeString(any()) } throws Exception()
        val dtoWeather = createDtoWeather(hourly = Hourly(listOf(10.0), listOf("2025-05-05T15:00"), listOf(0)),1)
        //When & Then
        assertThrows<Exception> {
            weatherMapper.mapDtoToWeather(dtoWeather)
        }
    }

    @Test
    fun `mapDtoToWeather should throw UnKownWeatherCondition when current weather code is unkown`() {
        //Given
        every { weatherTimeParser.getHourFromTimeString(any()) } returns 15
        val dtoWeather = createDtoWeather(hourly = Hourly(listOf(10.0), listOf("2025-05-05T15:00"), listOf(0)),100)
        //When & Then
        assertThrows<UnKownWeatherCondition> {
            weatherMapper.mapDtoToWeather(dtoWeather)
        }
    }
}