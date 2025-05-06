package data.location.mapper

import data.location.model.IpLocationDto
import logic.model.LocationCoordinate

class IpLocationMapper {
    fun mapDtoToCoordinate(ipLocationDto: IpLocationDto): LocationCoordinate {
        return LocationCoordinate(
            longitude = ipLocationDto.lon,
            latitude = ipLocationDto.lat
        )
    }

}