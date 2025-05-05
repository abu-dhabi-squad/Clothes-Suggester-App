package data.weather.repository

import com.google.common.truth.Truth.assertThat
import data.weather.datasource.WeatherDataSource
import data.weather.mapper.WeatherMapper
import helper.createDtoWeather
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import logic.model.Coordinate
import logic.model.HourlyTemperature
import logic.model.Weather
import logic.model.WeatherCondition
import org.junit.jupiter.api.assertThrows
import kotlin.test.BeforeTest
import kotlin.test.Test

class WeatherRepositoryImplTest {
    private lateinit var weatherMapper: WeatherMapper
    private lateinit var weatherDataSource: WeatherDataSource
    private lateinit var weatherRepository: WeatherRepositoryImpl

    @BeforeTest
    fun setup() {
        weatherMapper = mockk(relaxed = true)
        weatherDataSource = mockk(relaxed = true)
        weatherRepository = WeatherRepositoryImpl(weatherDataSource, weatherMapper)
    }

    @Test
    fun `getDailyWeatherByCoordinate should return weather when provided valid coordinate`() = runTest {
        // Given
        val coordinate = Coordinate(27.0, 30.0)
        val weather = Weather(listOf(HourlyTemperature(10.0, 12)), WeatherCondition.WINDY)
        coEvery { weatherDataSource.getWeatherByCoordinate(any()) } returns createDtoWeather()
        coEvery { weatherMapper.mapDtoToWeather(any()) } returns weather
        // When
        val result = weatherRepository.getDailyWeatherByCoordinate(coordinate)
        // Then
        assertThat(result).isEqualTo(weather)
    }

    @Test
    fun `getDailyWeatherByCoordinate should throw Exception when datasource throws Exception`() = runTest {
        // Given
        val coordinate = Coordinate(27.0, 30.0)
        val weather = Weather(listOf(HourlyTemperature(10.0, 12)), WeatherCondition.WINDY)
        coEvery { weatherDataSource.getWeatherByCoordinate(any()) } throws Exception()
        // When & Then
        assertThrows<Exception> { weatherRepository.getDailyWeatherByCoordinate(coordinate) }
    }

    @Test
    fun `getDailyWeatherByCoordinate should throw Exception when mapper throws Exception`() = runTest {
        // Given
        val coordinate = Coordinate(27.0, 30.0)
        val weather = Weather(listOf(HourlyTemperature(10.0, 12)), WeatherCondition.WINDY)
        coEvery { weatherDataSource.getWeatherByCoordinate(any()) } returns createDtoWeather()
        coEvery { weatherMapper.mapDtoToWeather(any()) } throws Exception()
        // When & Then
        assertThrows<Exception> { weatherRepository.getDailyWeatherByCoordinate(coordinate) }
    }
}