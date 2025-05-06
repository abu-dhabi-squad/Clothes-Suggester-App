package data.location.mapper

import data.location.model.CoordinateDto
import logic.exception.DataIsNullException
import logic.exception.NoLocationFoundException
import logic.model.Coordinate

class CoordinateMapper {
    fun mapDtoToCoordinate(coordinateDto: CoordinateDto): Coordinate {

        val citiesCoordinates = coordinateDto.citiesCoordinates ?: throw DataIsNullException()
        val firstCity = citiesCoordinates.firstOrNull() ?: throw NoLocationFoundException()
        val latitude = firstCity.latitude ?: throw DataIsNullException()
        val longitude = firstCity.longitude ?: throw DataIsNullException()

        return Coordinate(latitude, longitude)
    }
}