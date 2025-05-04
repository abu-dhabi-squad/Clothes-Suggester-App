package logic.repository

import logic.model.Coordinate

interface LocationRepository {
    fun getLocationByCityAndCountry(cityName: String, country: String) : Coordinate
}