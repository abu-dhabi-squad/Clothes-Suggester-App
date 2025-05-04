package logic.location

import logic.model.Coordinate
import logic.repository.LocationRepository

class GetLocationUseCase(
    private val locationRepository: LocationRepository
) {
    fun getLocation(cityName: String, country: String): Coordinate {
        TODO()
    }
}