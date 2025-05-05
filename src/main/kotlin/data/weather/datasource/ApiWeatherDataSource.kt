package data.weather.datasource

import data.weather.model.WeatherDto
import io.ktor.client.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import kotlinx.serialization.json.Json
import logic.model.Coordinate

class ApiWeatherDataSource (val client: HttpClient) : WeatherDataSource {
    override suspend fun getDailyWeatherByCoordinate(coordinate: Coordinate): WeatherDto {
        val response = client.get("https://api.open-meteo.com/v1/forecast"){
            url {
                parameters.append("latitude", coordinate.latitude.toString())
                parameters.append("longitude", coordinate.longitude.toString())
                parameters.append("daily", "weather_code,temperature_2m_max,temperature_2m_min")
                parameters.append("hourly", "temperature_2m,weather_code")
                parameters.append("current", "weather_code,temperature_2m")
                parameters.append("forecast_days", "1")
            }
        }
        val data = Json.decodeFromString<WeatherDto>(response.bodyAsText())
        return data
    }
}