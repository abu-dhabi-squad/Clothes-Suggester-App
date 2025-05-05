package data.weather.datasource

import data.weather.model.DtoWeather
import io.ktor.client.*
import io.ktor.client.engine.cio.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import kotlinx.serialization.json.Json
import logic.model.Coordinate

class ApiWeatherDataSource : WeatherDataSource {
    override suspend fun getWeatherByCoordinate(coordinate: Coordinate): DtoWeather {
        val client = HttpClient(CIO)
        val response = client.get("https://api.open-meteo.com/v1/forecast?latitude=${coordinate.latitude}&longitude=${coordinate.longitude}&daily=weather_code,temperature_2m_max,temperature_2m_min&hourly=temperature_2m,weather_code&current=weather_code,temperature_2m&forecast_days=1")
        val data = Json.decodeFromString<DtoWeather>(response.bodyAsText())
        return data
    }
}