package data.location.datasource

import data.location.model.CoordinateDto
import io.ktor.client.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import kotlinx.serialization.json.Json

class ApiLocationDataSource(
    private val client: HttpClient,
): LocationDataSource {
    override suspend fun getLocationByCityAndCountry(cityName: String, country: String): CoordinateDto {
        val response = client.get{
            url("https://geocoding-api.open-meteo.com/v1/search")
            parameter("name", "$cityName,$country")
        }
        return Json.decodeFromString<CoordinateDto>(response.bodyAsText())
    }
}