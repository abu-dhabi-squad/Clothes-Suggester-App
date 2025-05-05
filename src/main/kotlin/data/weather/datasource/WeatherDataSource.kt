package data.weather.datasource

import data.weather.model.DtoWeather
import logic.model.Coordinate

interface WeatherDataSource {
    suspend fun getWeatherByCoordinate(coordinate: Coordinate): DtoWeather
}