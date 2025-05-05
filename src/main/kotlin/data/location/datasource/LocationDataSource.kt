package data.location.datasource

import data.location.model.CityLocationDto
import data.location.model.IpLocationDto

interface LocationDataSource {
    suspend fun getLocationByCityAndCountry(cityName: String, country: String) : CityLocationDto
    suspend fun getCoordinateByIp(): IpLocationDto

}