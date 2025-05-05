package data.weather.datasource

import data.weather.model.DtoWeather
import logic.model.Coordinate

class ApiWeatherDataSource : WeatherDataSource {
    override suspend fun getWeatherByCoordinate(coordinate: Coordinate): DtoWeather {
        TODO("Not yet implemented")
    }
}