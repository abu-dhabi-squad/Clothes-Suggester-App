package helper

import data.weather.model.*

fun createDtoWeather(
    hourlyWeather: HourlyWeather? = HourlyWeather(listOf(), listOf(), listOf()),
    currentWeatherCode: Int? = 0,
    currentWeather: CurrentWeather? = CurrentWeather(0, 0.0, "", currentWeatherCode) ,
): WeatherDto {

    return WeatherDto(
        currentWeather = currentWeather,
        currentWeatherUnits = CurrentWeatherUnits("", "", "", ""),
        dailyWeather = DailyWeather(listOf(), listOf(), listOf(), listOf()),
        dailyWeatherUnits = DailyWeatherUnits("", "", "", ""),
        elevation = 0.0,
        generationtimeMs = 0.0,
        hourlyWeather = hourlyWeather,
        hourlyWeatherUnits = HourlyWeatherUnits("", "", ""),
        latitude = 0.0,
        longitude = 0.0,
        timezone = "",
        timezoneAbbreviation = "",
        utcOffsetSeconds = 0,
    )
}