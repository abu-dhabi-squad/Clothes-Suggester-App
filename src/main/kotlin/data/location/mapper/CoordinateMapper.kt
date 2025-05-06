package data.location.mapper

import data.location.model.CoordinateDto
import logic.exception.DataIsNullException
import logic.exception.NoLocationFoundException
import logic.model.Coordinate

class CoordinateMapper {
    fun mapDtoToCoordinate(coordinateDto: CoordinateDto): Coordinate {
        coordinateDto.isValidData()
        return coordinateDto.citiesCoordinates!!.first()
            .let { Coordinate(it.latitude!!, it.longitude!!)}
    }

    private fun CoordinateDto.isValidData(){
        this.citiesCoordinates ?: throw DataIsNullException()
        this.citiesCoordinates.firstOrNull()?: throw NoLocationFoundException()
        this.citiesCoordinates.first().latitude ?: throw DataIsNullException()
        this.citiesCoordinates.first().longitude ?: throw DataIsNullException()
    }
}