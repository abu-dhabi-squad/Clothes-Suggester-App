package data.location.datasource

import data.location.model.CityLocationDto
import data.location.model.IpLocationDto
import io.ktor.client.HttpClient
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import io.ktor.client.request.url
import io.ktor.client.statement.bodyAsText
import kotlinx.serialization.json.Json

class LocationRemoteDataSourceImpl(
    private val client: HttpClient,
    private val json: Json,
) : LocationRemoteDataSource {
    override suspend fun getLocationByCityAndCountry(
        cityName: String,
        country: String
    ): CityLocationDto {
        val response = client.get {
            url(BASE_CITY_LOCATION_URL)
            parameter("name", "$cityName,$country")
        }
        return json.decodeFromString<CityLocationDto>(response.bodyAsText())
    }

    override suspend fun getLocationByIp(): IpLocationDto {
        val response = client.get {
            url(BASE_IP_LOCATION_URL)
        }
        return json.decodeFromString<IpLocationDto>(response.bodyAsText())
    }

    companion object {
        private const val BASE_CITY_LOCATION_URL = "https://geocoding-api.open-meteo.com/v1/search"
        private const val BASE_IP_LOCATION_URL = "http://ip-api.com/json/"
    }
}