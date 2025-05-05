package data.weather.repository.mapper

import data.weather.model.DtoWeather
import logic.model.Weather

class WeatherMapperImpl : WeatherMapper {
    override fun mapDtoToWeather(dtoWeather: DtoWeather): Weather {
        //val hourlyTemperature = dtoWeather.hourly.temperature2m.zip(dtoWeather.hourly.time){ temp, time -> }

        TODO("Not yet implemented")
    }
}