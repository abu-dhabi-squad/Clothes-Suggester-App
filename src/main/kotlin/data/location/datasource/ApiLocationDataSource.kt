package data.location.datasource

import data.location.model.DtoLocation
import io.ktor.client.*
import io.ktor.client.engine.cio.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import kotlinx.serialization.json.Json

class ApiLocationDataSource: LocationDataSource {
    override suspend fun getLocationByCityAndCountry(cityName: String, country: String): DtoLocation {
        val client = HttpClient(CIO)
        val response = client.get("https://geocoding-api.open-meteo.com/v1/search?name=$cityName,$country")
        val data = Json.decodeFromString<DtoLocation>(response.bodyAsText())
        return data
    }
}