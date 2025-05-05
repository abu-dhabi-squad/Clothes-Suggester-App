package data.location.mapper

import data.location.model.CoordinateDto
import logic.model.Coordinate
import logic.model.NoLocationFoundException

class CoordinateMapper {
    fun mapDtoToCoordinate(coordinateDto: CoordinateDto): Coordinate {
        return coordinateDto.citiesCoordinates.firstOrNull()
            ?.let { Coordinate(it.latitude, it.longitude)}
            ?: throw NoLocationFoundException()
    }
}