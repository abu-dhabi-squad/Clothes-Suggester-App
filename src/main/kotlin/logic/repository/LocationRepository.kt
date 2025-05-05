package logic.repository

import logic.model.Coordinate

interface LocationRepository {
    suspend fun getLocationByCityAndCountry(cityName: String, country: String) : Coordinate
}