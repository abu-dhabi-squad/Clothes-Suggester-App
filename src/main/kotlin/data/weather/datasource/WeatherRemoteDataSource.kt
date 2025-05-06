package data.weather.datasource

import data.weather.model.WeatherDto
import logic.model.LocationCoordinate

interface WeatherRemoteDataSource {
    suspend fun getDailyWeatherByCoordinate(locationCoordinate: LocationCoordinate): WeatherDto
}