package data.location.datasource

import data.location.model.CoordinateDto

interface LocationDataSource {
    suspend fun getLocationByCityAndCountry(cityName: String, country: String) : CoordinateDto
}