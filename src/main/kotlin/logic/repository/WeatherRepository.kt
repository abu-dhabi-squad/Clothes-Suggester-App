package logic.repository

import logic.model.Coordinate
import logic.model.Weather

interface WeatherRepository {
    suspend fun getDailyWeather(coordinate: Coordinate): Weather
}