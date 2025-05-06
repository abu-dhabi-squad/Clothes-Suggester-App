package data.location.repository

import data.location.datasource.LocationRemoteDataSource
import data.location.mapper.CityLocationMapper
import data.location.mapper.IpLocationMapper
import logic.model.LocationCoordinate
import logic.repository.LocationRepository

class LocationRepositoryImpl(
    private val remoteDataSource: LocationRemoteDataSource,
    private val cityLocationMapper: CityLocationMapper,
    private val ipLocationMapper: IpLocationMapper
) : LocationRepository {
    override suspend fun getCoordinateByCityAndCountry(
        cityName: String,
        country: String
    ): LocationCoordinate {
        return cityLocationMapper.mapDtoToCoordinate(
            remoteDataSource.getLocationByCityAndCountry(cityName, country)
        )
    }

    override suspend fun getLocationByIp(): LocationCoordinate {
        return ipLocationMapper.mapDtoToCoordinate(remoteDataSource.getLocationByIp())
    }
}