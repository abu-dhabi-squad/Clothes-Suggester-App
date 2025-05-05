package data.location.mapper

import data.location.model.CoordinateDto
import logic.exception.NoLocationFoundException
import logic.model.Coordinate

class CoordinateMapper {
    fun mapDtoToCoordinate(coordinateDto: CoordinateDto): Coordinate {
        return coordinateDto.citiesCoordinates.firstOrNull()
            ?.let { Coordinate(it.latitude, it.longitude)}
            ?: throw NoLocationFoundException()
    }
}