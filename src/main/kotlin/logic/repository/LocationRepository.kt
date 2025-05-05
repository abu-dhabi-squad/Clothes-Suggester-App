package logic.repository

import logic.model.LocationCoordinate

interface LocationRepository {
    suspend fun getCoordinateByCityAndCountry(cityName: String, country: String) : LocationCoordinate
    suspend fun getCoordinateByIp(): LocationCoordinate
}