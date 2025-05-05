package data.location.repository.mapper

import data.location.model.DtoLocation
import logic.model.Coordinate

interface LocationMapper {
    fun mapDtoToCoordinate(dtoLocation: DtoLocation): Coordinate
}