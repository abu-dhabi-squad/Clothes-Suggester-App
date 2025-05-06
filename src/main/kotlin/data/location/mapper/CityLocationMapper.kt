package data.location.mapper

import data.location.model.CityLocationDto
import logic.model.LocationCoordinate
import logic.exception.NoLocationFoundException

class CityLocationMapper {
    fun mapDtoToCoordinate(cityLocationDto: CityLocationDto): LocationCoordinate {
        return cityLocationDto.citiesCoordinates.firstOrNull()
            ?.let { LocationCoordinate(it.latitude, it.longitude)}
            ?: throw NoLocationFoundException()
    }
}