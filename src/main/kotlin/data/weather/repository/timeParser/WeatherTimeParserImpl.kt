package data.weather.repository.timeParser

import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class WeatherTimeParserImpl: WeatherTimeParser {
    override fun getHourFromTimeString(time: String): Int {
        val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm")
        val dateTime = LocalDateTime.parse(time, formatter)
        return dateTime.hour
    }
}