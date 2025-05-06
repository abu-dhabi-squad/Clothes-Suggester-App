package data.weather.datasource

import data.weather.model.WeatherDto
import io.ktor.client.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import kotlinx.serialization.json.Json
import logic.model.LocationCoordinate

class WeatherRemoteDataSourceImpl (private val client: HttpClient) : WeatherRemoteDataSource {
    override suspend fun getDailyWeatherByCoordinate(locationCoordinate: LocationCoordinate): WeatherDto {
        val response = client.get(BASE_WEATHER_URL){
            url {
                parameters.append("latitude", locationCoordinate.latitude.toString())
                parameters.append("longitude", locationCoordinate.longitude.toString())
                parameters.append("daily", "weather_code,temperature_2m_max,temperature_2m_min")
                parameters.append("hourly", "temperature_2m,weather_code")
                parameters.append("current", "weather_code,temperature_2m")
                parameters.append("forecast_days", "1")
            }
        }
       return Json.decodeFromString<WeatherDto>(response.bodyAsText())
    }
    companion object{
        private const val BASE_WEATHER_URL = "https://api.open-meteo.com/v1/forecast"
    }
}