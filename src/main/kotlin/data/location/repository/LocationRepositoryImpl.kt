package data.location.repository

import data.location.datasource.LocationDataSource
import data.location.mapper.CoordinateMapper
import logic.model.Coordinate
import logic.repository.LocationRepository

class LocationRepositoryImpl(
    private val remoteDataSource: LocationDataSource,
    private val coordinateMapper: CoordinateMapper,
) : LocationRepository {
    override suspend fun getCoordinateByCityAndCountry(cityName: String, country: String) : Coordinate {
        return coordinateMapper.mapDtoToCoordinate(
            remoteDataSource.getLocationByCityAndCountry(cityName, country)
        )
    }
}