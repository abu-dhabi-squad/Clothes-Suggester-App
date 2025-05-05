package logic.location

import logic.exception.InvalidCityNameException
import logic.exception.InvalidCountryNameException
import logic.model.Coordinate
import logic.repository.LocationRepository

class GetLocationUseCase(
    private val locationRepository: LocationRepository
) {
    suspend fun getLocation(cityName: String, country: String): Coordinate {
        if (!validateCityName(cityName)) throw InvalidCityNameException()
        if (!validateCountry(country)) throw InvalidCountryNameException()
        return locationRepository.getLocationByCityAndCountry(cityName, country)
    }

    private fun validateCityName(cityName: String): Boolean {
        return cityName.isNotEmpty()
    }

    private fun validateCountry(country: String): Boolean {
        return country.isNotEmpty()
    }
}