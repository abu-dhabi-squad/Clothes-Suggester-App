package data.location.mapper

import data.location.model.CityLocationDto
import logic.exception.DataIsNullException
import logic.model.LocationCoordinate
import logic.exception.NoLocationFoundException

class CityLocationMapper {
    fun mapDtoToLocationCoordinate(cityLocationDto: CityLocationDto): LocationCoordinate {
        val citiesCoordinates = cityLocationDto.citiesCoordinates ?: throw DataIsNullException()
        val firstCity = citiesCoordinates.firstOrNull() ?: throw NoLocationFoundException()
        val latitude = firstCity.latitude ?: throw DataIsNullException()
        val longitude = firstCity.longitude ?: throw DataIsNullException()
        return LocationCoordinate(latitude, longitude)
    }
}