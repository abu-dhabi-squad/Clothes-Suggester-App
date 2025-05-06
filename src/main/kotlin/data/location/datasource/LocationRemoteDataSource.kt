package data.location.datasource

import data.location.model.CityLocationDto
import data.location.model.IpLocationDto

interface LocationRemoteDataSource {
    suspend fun getLocationByCityAndCountry(cityName: String, country: String) : CityLocationDto
    suspend fun getLocationByIp(): IpLocationDto
}