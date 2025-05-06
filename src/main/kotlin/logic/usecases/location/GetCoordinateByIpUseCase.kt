package logic.usecases.location

import logic.model.LocationCoordinate
import logic.repository.LocationRepository

class GetCoordinateByIpUseCase (private val locationRepository: LocationRepository) {
    suspend fun getCoordinateByIp(): LocationCoordinate {
        return locationRepository.getLocationByIp()
    }
}