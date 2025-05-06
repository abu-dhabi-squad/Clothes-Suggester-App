package data.weather.repository

import com.google.common.truth.Truth.assertThat
import data.weather.datasource.WeatherRemoteDataSource
import data.weather.mapper.WeatherMapper
import helper.createDtoWeather
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import logic.model.LocationCoordinate
import logic.model.HourlyTemperature
import logic.model.Weather
import logic.model.WeatherCondition
import org.junit.jupiter.api.assertThrows
import kotlin.test.BeforeTest
import kotlin.test.Test

class WeatherRepositoryImplTest {
    private lateinit var weatherMapper: WeatherMapper
    private lateinit var weatherRemoteDataSource: WeatherRemoteDataSource
    private lateinit var weatherRepository: WeatherRepositoryImpl

    @BeforeTest
    fun setup() {
        weatherMapper = mockk(relaxed = true)
        weatherRemoteDataSource = mockk(relaxed = true)
        weatherRepository = WeatherRepositoryImpl(weatherRemoteDataSource, weatherMapper)
    }

    @Test
    fun `getDailyWeatherByCoordinate should return weather when provided valid coordinate`() = runTest {
        // Given
        val locationCoordinate = LocationCoordinate(27.0, 30.0)
        val weather = Weather(listOf(HourlyTemperature(10.0, 12)), WeatherCondition.WINDY)
        coEvery { weatherRemoteDataSource.getDailyWeatherByCoordinate(any()) } returns createDtoWeather()
        coEvery { weatherMapper.mapDtoToWeather(any()) } returns weather

        // When
        val result = weatherRepository.getDailyWeatherByCoordinate(locationCoordinate)

        // Then
        assertThat(result).isEqualTo(weather)
    }

    @Test
    fun `getDailyWeatherByCoordinate should throw Exception when datasource throws Exception`() = runTest {
        // Given
        val locationCoordinate = LocationCoordinate(27.0, 30.0)
        coEvery { weatherRemoteDataSource.getDailyWeatherByCoordinate(any()) } throws Exception()

        // When & Then
        assertThrows<Exception> { weatherRepository.getDailyWeatherByCoordinate(locationCoordinate) }
    }

    @Test
    fun `getDailyWeatherByCoordinate should throw Exception when mapper throws Exception`() = runTest {
        // Given
        val locationCoordinate = LocationCoordinate(27.0, 30.0)
        coEvery { weatherRemoteDataSource.getDailyWeatherByCoordinate(any()) } returns createDtoWeather()
        coEvery { weatherMapper.mapDtoToWeather(any()) } throws Exception()

        // When & Then
        assertThrows<Exception> { weatherRepository.getDailyWeatherByCoordinate(locationCoordinate) }
    }
}