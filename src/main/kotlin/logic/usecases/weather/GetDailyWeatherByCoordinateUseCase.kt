package logic.usecases.weather

import logic.model.LocationCoordinate
import logic.model.HourlyTemperature
import logic.exception.NoHourlyTemperatureFound
import logic.exception.UnKnownWeatherConditionException
import logic.model.Weather
import logic.model.WeatherCondition
import logic.repository.WeatherRepository

class GetDailyWeatherByCoordinateUseCase(
    private val weatherRepository: WeatherRepository
) {
    suspend fun getDailyWeather(locationCoordinate: LocationCoordinate): Weather {
        val dailyWeather = weatherRepository.getDailyWeatherByCoordinate(locationCoordinate)
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