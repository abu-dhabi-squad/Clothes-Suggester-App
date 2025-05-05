package helper

import data.weather.model.*
import kotlinx.serialization.SerialName
import logic.model.HourlyTemperature
import logic.model.WeatherCondition

fun createDtoWeather(
    hourly: Hourly = Hourly(listOf(), listOf(), listOf()),
    currentWeatherCode: Int = 0,
): DtoWeather {

    return DtoWeather(
        current = Current(0, 0.0, "", currentWeatherCode),
        currentUnits = CurrentUnits("", "", "", ""),
        daily = Daily(listOf(), listOf(), listOf(), listOf()),
        dailyUnits = DailyUnits("", "", "", ""),
        elevation = 0,
        generationtimeMs = 0.0,
        hourly = hourly,
        hourlyUnits = HourlyUnits("", "", ""),
        latitude = 0.0,
        longitude = 0.0,
        timezone = "",
        timezoneAbbreviation = "",
        utcOffsetSeconds = 0,
    )
}