package helper

import data.weather.model.*
import kotlinx.serialization.SerialName

fun createDtoWeather(): DtoWeather {

    return DtoWeather(
        current =Current(0,0.0,"",0),
        currentUnits = CurrentUnits("","","",""),
        daily = Daily(listOf(), listOf(), listOf(), listOf()),
        dailyUnits = DailyUnits("","","",""),
        elevation =0,
        generationtimeMs = 0.0,
        hourly =Hourly(listOf(), listOf(), listOf()),
        hourlyUnits = HourlyUnits("","", ""),
        latitude = 0.0,
        longitude = 0.0,
        timezone ="",
        timezoneAbbreviation ="",
        utcOffsetSeconds = 0,
    )
}