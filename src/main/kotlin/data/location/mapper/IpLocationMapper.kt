package data.location.mapper

import data.location.model.IpLocationDto
import logic.exception.NoLocationFoundException
import logic.model.LocationCoordinate

class IpLocationMapper {
    fun mapDtoToCoordinate(ipLocationDto: IpLocationDto): LocationCoordinate {
        val latitude = ipLocationDto.lat ?: throw NoLocationFoundException()
        val longitude = ipLocationDto.lon ?: throw NoLocationFoundException()
        return LocationCoordinate(
            longitude = longitude,
            latitude = latitude,
        )
    }
}