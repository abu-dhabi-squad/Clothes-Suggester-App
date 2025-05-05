package data.location.repository.mapper

import data.location.model.CoordinateDto
import logic.exception.NoLocationFoundException
import logic.model.Coordinate

class CoordinateMapperImpl : CoordinateMapper {
    override fun mapDtoToCoordinate(coordinateDto: CoordinateDto): Coordinate {
        return coordinateDto.citiesCoordinates.firstOrNull()
            ?.let { Coordinate(it.latitude, it.longitude)}
            ?: throw NoLocationFoundException()
    }
}