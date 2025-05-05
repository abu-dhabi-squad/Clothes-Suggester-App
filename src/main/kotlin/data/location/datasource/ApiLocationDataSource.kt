package data.location.datasource

import data.location.model.CityLocationDto
import data.location.model.IpLocationDto
import io.ktor.client.HttpClient
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import io.ktor.client.request.url
import io.ktor.client.statement.bodyAsText
import kotlinx.serialization.json.Json

class ApiLocationDataSource(
    private val client: HttpClient,
) : LocationDataSource {
    override suspend fun getLocationByCityAndCountry(
        cityName: String,
        country: String
    ): CityLocationDto {
        val response = client.get {
            url(LOCATION_URL_BY_CITY_NAME)
            parameter("name", "$cityName,$country")
        }
        return Json.decodeFromString<CityLocationDto>(response.bodyAsText())
    }
    override suspend fun getCoordinateByIp(): IpLocationDto {
        val response = client.get {
            url(LOCATION_URL_BY_IP)
        }
        return Json.decodeFromString<IpLocationDto>(response.bodyAsText())
    }

    companion object {
        private const val LOCATION_URL_BY_CITY_NAME =
            "https://geocoding-api.open-meteo.com/v1/search"
        private const val LOCATION_URL_BY_IP = "http://ip-api.com/json/"
    }
}