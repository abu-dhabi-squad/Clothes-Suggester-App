package data.weather.datasource

import data.weather.model.WeatherDto
import logic.model.LocationCoordinate

interface WeatherDataSource {
    suspend fun getDailyWeatherByCoordinate(locationCoordinate: LocationCoordinate): WeatherDto
}