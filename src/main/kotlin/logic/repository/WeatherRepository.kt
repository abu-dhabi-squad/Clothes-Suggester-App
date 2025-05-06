package logic.repository

import logic.model.LocationCoordinate
import logic.model.Weather

interface WeatherRepository {
    suspend fun getDailyWeatherByCoordinate(locationCoordinate: LocationCoordinate): Weather
}