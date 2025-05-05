package logic.weather

import logic.exception.NoHourlyTemperatureFound
import logic.exception.UnKnownWeatherConditionException
import logic.model.Coordinate
import logic.model.HourlyTemperature
import logic.model.Weather
import logic.model.WeatherCondition
import logic.repository.WeatherRepository

class GetDailyWeatherByCoordinateUseCase(
    private val weatherRepository: WeatherRepository
) {
    suspend fun getDailyWeather(coordinate: Coordinate): Weather {
        val dailyWeather = weatherRepository.getDailyWeatherByCoordinate(coordinate)
        if (!isValidHourlyWeather(dailyWeather.hourlyTemperatures)) throw NoHourlyTemperatureFound()
        if (!isValidWeatherCondition(dailyWeather.weatherCondition)) throw UnKnownWeatherConditionException()
        return dailyWeather
    }

    private fun isValidHourlyWeather(hourlyTemperatures: List<HourlyTemperature>): Boolean {
        return hourlyTemperatures.isNotEmpty()
    }

    private fun isValidWeatherCondition(weatherCondition: WeatherCondition): Boolean {
        return weatherCondition != WeatherCondition.UNKNOWN_WEATHER_FORECAST
    }
}