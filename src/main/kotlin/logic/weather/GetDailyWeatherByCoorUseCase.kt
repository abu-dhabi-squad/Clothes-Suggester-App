package logic.weather

import logic.model.Coordinate
import logic.model.Weather
import logic.repository.WeatherRepository

class GetDailyWeatherByCoordinateUseCase(
    private val weatherRepository: WeatherRepository
) {
    suspend fun getDailyWeather(coordinate: Coordinate): Weather {
        TODO()
    }
}