package logic.repository

import logic.model.Coordinate
import logic.model.Weather

interface WeatherRepository {
    suspend fun getDailyWeatherByCoordinate(coordinate: Coordinate): Weather
}