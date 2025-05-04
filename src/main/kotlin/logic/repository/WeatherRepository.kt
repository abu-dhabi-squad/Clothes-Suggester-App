package logic.repository

import logic.model.Coordinate
import logic.model.Weather

interface WeatherRepository {
    fun getDailyWeather(coordinate: Coordinate): Weather
}