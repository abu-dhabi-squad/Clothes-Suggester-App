package data.weather.datasource

import data.weather.model.WeatherDto
import logic.model.Coordinate

interface WeatherDataSource {
    suspend fun getWeatherByCoordinate(coordinate: Coordinate): WeatherDto
}