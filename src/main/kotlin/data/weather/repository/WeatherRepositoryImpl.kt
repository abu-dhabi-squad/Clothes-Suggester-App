package data.weather.repository

import data.weather.datasource.WeatherDataSource
import data.weather.repository.mapper.WeatherMapper
import logic.model.Coordinate
import logic.model.Weather
import logic.repository.WeatherRepository

class WeatherRepositoryImpl(
    private val remoteDataSource: WeatherDataSource,
    private val weatherMapper: WeatherMapper,
): WeatherRepository{
    override suspend fun getDailyWeatherByCoordinate(coordinate: Coordinate): Weather {
        TODO("Not yet implemented")
    }
}