package data.location.datasource

import data.location.model.DtoLocation

interface LocationDataSource {
    suspend fun getLocationByCityAndCountry(cityName: String, country: String) : DtoLocation
}