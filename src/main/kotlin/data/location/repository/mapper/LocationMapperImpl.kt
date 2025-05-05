package data.location.repository.mapper

import data.location.model.DtoLocation
import logic.model.Coordinate

class LocationMapperImpl: LocationMapper {
    override fun mapDtoToCoordinate(dtoLocation: DtoLocation): Coordinate {
        return Coordinate(dtoLocation.locations.first().latitude, dtoLocation.locations.first().longitude)
    }
}