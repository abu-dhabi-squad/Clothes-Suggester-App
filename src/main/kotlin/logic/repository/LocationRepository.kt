package logic.repository

import logic.model.Coordinate

interface LocationRepository {
    suspend fun getCoordinateByCityAndCountry(cityName: String, country: String) : Coordinate
}