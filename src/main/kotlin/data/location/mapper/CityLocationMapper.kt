package data.location.mapper

import data.location.model.CityLocationDto
import logic.model.LocationCoordinate
import logic.exception.NoLocationFoundException

class CityLocationMapper {
    fun mapDtoToLocationCoordinate(cityLocationDto: CityLocationDto): LocationCoordinate {
        val citiesCoordinates = cityLocationDto.citiesCoordinates ?: throw NoLocationFoundException()
        val firstCity = citiesCoordinates.firstOrNull() ?: throw NoLocationFoundException()
        val latitude = firstCity.latitude ?: throw NoLocationFoundException()
        val longitude = firstCity.longitude ?: throw NoLocationFoundException()
        return LocationCoordinate(latitude, longitude)
    }
}