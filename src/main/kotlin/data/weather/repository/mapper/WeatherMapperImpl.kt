package data.weather.repository.mapper

import data.weather.model.DtoWeather
import data.weather.repository.timeParser.WeatherTimeParser
import logic.exception.UnKownWeatherConditionException
import logic.model.HourlyTemperature
import logic.model.Weather
import logic.model.WeatherCondition

class WeatherMapperImpl(
    private val weatherTimeParser: WeatherTimeParser
) : WeatherMapper {
    override fun mapDtoToWeather(dtoWeather: DtoWeather): Weather {
        val firstDayDate = dtoWeather.hourly.time.first().substringBefore("T")
        val timeOfTheDay = dtoWeather.hourly.time.filter { it.startsWith(firstDayDate) }
        val hourlyTemperature = dtoWeather.hourly.temperature2m.zip(timeOfTheDay) { temp, time ->
            HourlyTemperature(temp, weatherTimeParser.getHourFromTimeString(time))
        }
        return Weather(hourlyTemperature, getWeatherForeCast(dtoWeather.current.weatherCode))
    }

    private fun getWeatherForeCast(weatherCode: Int): WeatherCondition {
        return when (weatherCode) {
            0 -> WeatherCondition.CLEAR_SKY
            1 -> WeatherCondition.MAINLY_CLEAR
            2 -> WeatherCondition.PARTLY_CLOUDY
            3 -> WeatherCondition.OVERCAST
            45 -> WeatherCondition.FOG
            48 -> WeatherCondition.DEPOSITING_RIME_FOG
            51 -> WeatherCondition.DRIZZLE_LIGHT
            53 -> WeatherCondition.DRIZZLE_MODERATE
            55 -> WeatherCondition.DRIZZLE_HIGH
            56 -> WeatherCondition.FREEZING_DRIZZLE_LIGHT
            57 -> WeatherCondition.FREEZING_DRIZZLE_HIGHT
            61 -> WeatherCondition.RAIN_LIGHT
            63 -> WeatherCondition.RAIN_MODERATE
            65 -> WeatherCondition.RAIN_HEAVY
            66 -> WeatherCondition.FREEZING_RAIN_LIGHT
            67 -> WeatherCondition.FREEZING_RAIN_HIGH
            73 -> WeatherCondition.SNOW_MODERATE
            71 -> WeatherCondition.SNOW_LIGHT
            75 -> WeatherCondition.SNOW_HEAVY
            77 -> WeatherCondition.SNOW_GRAINS
            80 -> WeatherCondition.RAIN_SHOWER_LIGHT
            81 -> WeatherCondition.RAIN_SHOWER_MODRATE
            82 -> WeatherCondition.RAIN_SHOWER_HEAVY
            85 -> WeatherCondition.SNOW_SHOWER_LIGHT
            86 -> WeatherCondition.SNOW_SHOWER_HEAVY
            95 -> WeatherCondition.THUNDER_STORM
            96 -> WeatherCondition.THUNDER_STORM_HAIL_LIGHT
            99 -> WeatherCondition.THUNDER_STORM_HAIL_HEAVY
            else -> {throw UnKownWeatherConditionException()
            }
        }
    }
}