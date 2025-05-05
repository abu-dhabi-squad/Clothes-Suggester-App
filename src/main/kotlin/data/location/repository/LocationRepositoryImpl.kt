package data.location.repository

import data.location.datasource.LocationDataSource
import data.location.repository.mapper.LocationMapper
import logic.model.Coordinate
import logic.repository.LocationRepository

class LocationRepositoryImpl(
    private val remoteDataSource: LocationDataSource,
    private val locationMapper: LocationMapper,
) : LocationRepository {
    override suspend fun getLocationByCityAndCountry(cityName: String, country: String) : Coordinate {
        return locationMapper.mapDtoToCoordinate(
            remoteDataSource.getLocationByCityAndCountry(cityName, country)
        )
    }
}