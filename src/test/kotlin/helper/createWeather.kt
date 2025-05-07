package helper

import logic.model.HourlyTemperature
import logic.model.Weather
import logic.model.WeatherCondition

fun createWeather(hourlyTemperatures: List<HourlyTemperature> = emptyList(), weatherCondition:WeatherCondition=WeatherCondition.CLEAR_SKY):Weather{
    return Weather(
        hourlyTemperatures = hourlyTemperatures,
        weatherCondition = weatherCondition)
}