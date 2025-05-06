package logic.usecases.location


import logic.model.LocationCoordinate
import logic.exception.InvalidCityNameException
import logic.exception.InvalidCountryNameException
import logic.repository.LocationRepository

class GetCoordinateByCityAndCountryUseCase(
    private val locationRepository: LocationRepository
) {
    suspend fun getCoordinateByCityAndCountry(cityName: String, country: String): LocationCoordinate {
        if (!validateCityName(cityName)) throw InvalidCityNameException()
        if (!validateCountry(country)) throw InvalidCountryNameException()
        return locationRepository.getCoordinateByCityAndCountry(cityName, country)
    }

    private fun validateCityName(cityName: String): Boolean {
        return cityName.isNotEmpty()
    }

    private fun validateCountry(country: String): Boolean {
        return country.isNotEmpty()
    }
}