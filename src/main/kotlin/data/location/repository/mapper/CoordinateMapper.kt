package data.location.repository.mapper

import data.location.model.CoordinateDto
import logic.model.Coordinate

interface CoordinateMapper {
    fun mapDtoToCoordinate(coordinateDto: CoordinateDto): Coordinate
}