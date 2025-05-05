package data.weather.repository

import data.weather.datasource.WeatherDataSource
import data.weather.mapper.WeatherMapper
import logic.model.LocationCoordinate
import logic.model.Weather
import logic.repository.WeatherRepository

class WeatherRepositoryImpl(
    private val remoteDataSource: WeatherDataSource,
    private val weatherMapper: WeatherMapper,
) : WeatherRepository {
    override suspend fun getDailyWeatherByCoordinate(locationCoordinate: LocationCoordinate): Weather {
        val res = remoteDataSource.getDailyWeatherByCoordinate(locationCoordinate)
        return weatherMapper.mapDtoToWeather(res)
    }
}