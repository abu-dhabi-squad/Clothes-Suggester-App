package logic.weather

import logic.exception.NoHourlyTemperatureFound
import logic.model.Coordinate
import logic.model.HourlyTemperature
import logic.model.Weather
import logic.repository.WeatherRepository

class GetDailyWeatherByCoordinateUseCase(
    private val weatherRepository: WeatherRepository
) {
    suspend fun getDailyWeather(coordinate: Coordinate): Weather {
        val dailyWeather = weatherRepository.getDailyWeatherByCoordinate(coordinate)
        if (!isValidHourlyWeather(dailyWeather.hourlyTemperatures)) throw NoHourlyTemperatureFound()
        return dailyWeather
    }

    private fun isValidHourlyWeather(hourlyTemperatures: List<HourlyTemperature>): Boolean {
        return hourlyTemperatures.isNotEmpty()
    }
}