package data.weather.repository.mapper

import data.weather.model.DtoWeather
import logic.model.Weather

interface WeatherMapper {
    fun mapDtoToWeather(dtoWeather: DtoWeather): Weather
}