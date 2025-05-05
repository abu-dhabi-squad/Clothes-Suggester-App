package data.location.datasource

import data.location.model.CoordinateDto
import io.ktor.client.*
import io.ktor.client.engine.cio.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import kotlinx.serialization.json.Json

class ApiLocationDataSource: LocationDataSource {
    override suspend fun getLocationByCityAndCountry(cityName: String, country: String): CoordinateDto {
        val client = HttpClient(CIO)
        val response = client.get("https://geocoding-api.open-meteo.com/v1/search?name=$cityName,$country")
        val data = Json.decodeFromString<CoordinateDto>(response.bodyAsText())
        return data
    }
}