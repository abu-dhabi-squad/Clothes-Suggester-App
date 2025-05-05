package data.weather.repository.timeParser

interface WeatherTimeParser {
    fun getHourFromTimeString(time: String): Int
}