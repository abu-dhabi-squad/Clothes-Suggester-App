package logic.weather

import com.google.common.truth.Truth.assertThat
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import logic.exception.NoHourlyTemperatureFound
import logic.exception.UnKnownWeatherConditionException
import logic.model.Coordinate
import logic.model.HourlyTemperature
import logic.model.Weather
import logic.model.WeatherCondition
import logic.repository.WeatherRepository
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import kotlin.test.BeforeTest

class GetDailyWeatherWeatherByCoordinateUseCaseTest {

    private lateinit var weatherRepository: WeatherRepository
    private lateinit var getDailyWeatherByCoordinate: GetDailyWeatherByCoordinateUseCase

    @BeforeTest
    fun setup() {
        weatherRepository = mockk(relaxed = true)
        getDailyWeatherByCoordinate = GetDailyWeatherByCoordinateUseCase(weatherRepository)
    }

    @Test
    fun `getDailyWeather should return weather when provided valid coordinate`() = runTest {
        //Given
        val validCoordinate = Coordinate(27.0, 30.0)
        val weather = Weather(listOf(HourlyTemperature(10.0, 12)), WeatherCondition.WINDY)
        coEvery { weatherRepository.getDailyWeatherByCoordinate(any()) } returns weather
        //When
        val result = getDailyWeatherByCoordinate.getDailyWeather(validCoordinate)
        //Then
        assertThat(result).isEqualTo(weather)
    }

    @Test
    fun `getDailyWeather should throw NoHourlyTemperatureFound when returned weather has empty hourlyTemperatures`() =
        runTest {
            //Given
            val validCoordinate = Coordinate(27.0, 30.0)
            val weather = Weather(listOf(), WeatherCondition.WINDY)
            coEvery { weatherRepository.getDailyWeatherByCoordinate(any()) } returns weather
            //When & Then
            assertThrows<NoHourlyTemperatureFound> {
                getDailyWeatherByCoordinate.getDailyWeather(validCoordinate)
            }
        }

    @Test
    fun `getDailyWeather should throw UnKnownWeatherConditionException when returned weather has weather UNKNOWN_WEATHER_FORECAST condition`() =
        runTest {
            //Given
            val validCoordinate = Coordinate(27.0, 30.0)
            val weather = Weather(listOf(HourlyTemperature(10.0, 12)), WeatherCondition.UNKNOWN_WEATHER_FORECAST)
            coEvery { weatherRepository.getDailyWeatherByCoordinate(any()) } returns weather
            //When & Then
            assertThrows<UnKnownWeatherConditionException> {
                getDailyWeatherByCoordinate.getDailyWeather(validCoordinate)
            }
        }

    @Test
    fun `getDailyWeather should throw Exception when weatherRepository getDailyWeather throw Exception`() = runTest {
        //Given
        val validCoordinate = Coordinate(27.0, 30.0)
        coEvery { weatherRepository.getDailyWeatherByCoordinate(any()) } throws Exception()
        //When & Then
        assertThrows<Exception> {
            getDailyWeatherByCoordinate.getDailyWeather(validCoordinate)
        }
    }
}