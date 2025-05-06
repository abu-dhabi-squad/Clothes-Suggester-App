package data.weather.repository.mapper

import com.google.common.truth.Truth.assertThat
import data.weather.mapper.WeatherMapper
import data.weather.model.HourlyWeather
import helper.createDtoWeather
import logic.exception.DataIsNullException
import logic.model.HourlyTemperature
import logic.model.Weather
import logic.model.WeatherCondition
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource
import java.util.stream.Stream
import kotlin.test.Test

class WeatherMapperTest {
    private lateinit var weatherMapper: WeatherMapper

    @BeforeEach
    fun setup() {
        weatherMapper = WeatherMapper()
    }

    @Test
    fun `mapDtoToWeather should throw DataIsNullException when hourlyWeather is null`(){
        //Given
        val dtoWeather = createDtoWeather(null, 0)
        //When & Then
        assertThrows<DataIsNullException> {
            weatherMapper.mapDtoToWeather(dtoWeather)
        }
    }

    @Test
    fun `mapDtoToWeather should throw DataIsNullException when hourlyWeather temperature2m is null`(){
        //Given
        val dtoWeather = createDtoWeather(HourlyWeather(null, listOf("2025-05-05T15:00"), listOf(0)), 0)
        //When & Then
        assertThrows<DataIsNullException> {
            weatherMapper.mapDtoToWeather(dtoWeather)
        }
    }

    @Test
    fun `mapDtoToWeather should throw DataIsNullException when hourlyWeather time is null`(){
        //Given
        val dtoWeather = createDtoWeather(HourlyWeather(listOf(10.0), null, listOf(0)), 0)
        //When & Then
        assertThrows<DataIsNullException> {
            weatherMapper.mapDtoToWeather(dtoWeather)
        }
    }

    @Test
    fun `mapDtoToWeather should throw DataIsNullException when currentWeather is null`(){
        //Given
        val dtoWeather = createDtoWeather(HourlyWeather(listOf(10.0), listOf("2025-05-05T15:00"), listOf(0)),0, null)
        //When & Then
        assertThrows<DataIsNullException> {
            weatherMapper.mapDtoToWeather(dtoWeather)
        }
    }

    @Test
    fun `mapDtoToWeather should throw DataIsNullException when currentWeather code is null`(){
        //Given
        val dtoWeather = createDtoWeather(HourlyWeather(listOf(10.0), listOf("2025-05-05T15:00"), listOf(0)),null)
        //When & Then
        assertThrows<DataIsNullException> {
            weatherMapper.mapDtoToWeather(dtoWeather)
        }
    }

    @ParameterizedTest
    @MethodSource("provideWeatherParams")
    fun `mapDtoToWeather should return weather when data is valid`(
        weatherCode: Int,
        WeatherCondition: WeatherCondition
    ) {
        //Given
        val dtoWeather = createDtoWeather(hourlyWeather = HourlyWeather(listOf(10.0), listOf("2025-05-05T15:00"), listOf(0)), weatherCode)
        val expectedWeather = Weather(listOf(HourlyTemperature(10.0, 15)), WeatherCondition)
        //When
        val actualWeather = weatherMapper.mapDtoToWeather(dtoWeather)
        //Then
        assertThat(actualWeather).isEqualTo(expectedWeather)
    }

    companion object {
        @JvmStatic
        fun provideWeatherParams(): Stream<Arguments> {
            return Stream.of(
                Arguments.of(0, WeatherCondition.CLEAR_SKY),
                Arguments.of(1, WeatherCondition.MAINLY_CLEAR),
                Arguments.of(2, WeatherCondition.PARTLY_CLOUDY),
                Arguments.of(3, WeatherCondition.OVERCAST),
                Arguments.of(45, WeatherCondition.FOG),
                Arguments.of(48, WeatherCondition.DEPOSITING_RIME_FOG),
                Arguments.of(51, WeatherCondition.DRIZZLE_LIGHT),
                Arguments.of(53, WeatherCondition.DRIZZLE_MODERATE),
                Arguments.of(55, WeatherCondition.DRIZZLE_HIGH),
                Arguments.of(56, WeatherCondition.FREEZING_DRIZZLE_LIGHT),
                Arguments.of(57, WeatherCondition.FREEZING_DRIZZLE_HIGHT),
                Arguments.of(61, WeatherCondition.RAIN_LIGHT),
                Arguments.of(63, WeatherCondition.RAIN_MODERATE),
                Arguments.of(65, WeatherCondition.RAIN_HEAVY),
                Arguments.of(66, WeatherCondition.FREEZING_RAIN_LIGHT),
                Arguments.of(67, WeatherCondition.FREEZING_RAIN_HIGH),
                Arguments.of(73, WeatherCondition.SNOW_MODERATE),
                Arguments.of(71, WeatherCondition.SNOW_LIGHT),
                Arguments.of(75, WeatherCondition.SNOW_HEAVY),
                Arguments.of(77, WeatherCondition.SNOW_GRAINS),
                Arguments.of(80, WeatherCondition.RAIN_SHOWER_LIGHT),
                Arguments.of(81, WeatherCondition.RAIN_SHOWER_MODRATE),
                Arguments.of(82, WeatherCondition.RAIN_SHOWER_HEAVY),
                Arguments.of(85, WeatherCondition.SNOW_SHOWER_LIGHT),
                Arguments.of(86, WeatherCondition.SNOW_SHOWER_HEAVY),
                Arguments.of(95, WeatherCondition.THUNDER_STORM),
                Arguments.of(96, WeatherCondition.THUNDER_STORM_HAIL_LIGHT),
                Arguments.of(99, WeatherCondition.THUNDER_STORM_HAIL_HEAVY),
                Arguments.of(100, WeatherCondition.UNKNOWN_WEATHER_FORECAST)
            )
        }
    }
}