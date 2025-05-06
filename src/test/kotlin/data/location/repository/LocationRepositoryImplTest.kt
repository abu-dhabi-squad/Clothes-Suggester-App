package data.location.repository

import helper.createDtoLocation
import com.google.common.truth.Truth.assertThat
import data.location.datasource.LocationRemoteDataSource
import data.location.mapper.CityLocationMapper
import data.location.mapper.IpLocationMapper
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import logic.model.LocationCoordinate
import logic.exception.NoLocationFoundException
import logic.repository.LocationRepository
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

class LocationRepositoryImplTest {
    private lateinit var remoteDataSource: LocationRemoteDataSource
    private lateinit var cityLocationMapper: CityLocationMapper
    private lateinit var locationRepository: LocationRepository
    private lateinit var ipLocationMapper : IpLocationMapper

    @BeforeEach
    fun setup() {
        remoteDataSource = mockk(relaxed = true)
        cityLocationMapper = mockk(relaxed = true)
        ipLocationMapper = mockk(relaxed = true)
        locationRepository = LocationRepositoryImpl(remoteDataSource, cityLocationMapper,ipLocationMapper)
    }

    @Test
    fun `getCoordinateByCityAndCountry should return location when provided valid city name and country`() = runTest {
        // Given
        val city = "city"
        val country = "country"
        val expectedLocationDto = createDtoLocation()
        val expectedLocation = LocationCoordinate(expectedLocationDto.citiesCoordinates.first().latitude, expectedLocationDto.citiesCoordinates.first().longitude)
        coEvery { remoteDataSource.getLocationByCityAndCountry(any(), any()) } returns expectedLocationDto
        coEvery { cityLocationMapper.mapDtoToCoordinate(expectedLocationDto) } returns expectedLocation

        // When
        val result = locationRepository.getCoordinateByCityAndCountry(city, country)

        // Then
        assertThat(result).isEqualTo(expectedLocation)
    }

    @Test
    fun `getCoordinateByCityAndCountry should throw Exception when datasource throws Exception`() = runTest {
        // Given
        val city = "city"
        val country = "country"
        coEvery { remoteDataSource.getLocationByCityAndCountry(any(), any()) } throws Exception()

        // When & Then
        assertThrows<Exception> { locationRepository.getCoordinateByCityAndCountry(city, country) }
    }

    @Test
    fun `getCoordinateByCityAndCountry should throw NoLocationFoundException when mapper throws NoLocationFoundException`() = runTest {
        // Given
        val city = "city"
        val country = "country"
        val expectedLocationDto = createDtoLocation()
        coEvery { remoteDataSource.getLocationByCityAndCountry(any(), any()) } returns expectedLocationDto
        coEvery { cityLocationMapper.mapDtoToCoordinate(expectedLocationDto) } throws NoLocationFoundException()

        // When & Then
        assertThrows<NoLocationFoundException> { locationRepository.getCoordinateByCityAndCountry(city, country) }
    }

    @Test
    fun `getCoordinateByCityAndCountry should throw Exception when mapper throws Exception`() = runTest {
        // Given
        val city = "city"
        val country = "country"
        val expectedLocationDto = createDtoLocation()
        coEvery { remoteDataSource.getLocationByCityAndCountry(any(), any()) } returns expectedLocationDto
        coEvery { cityLocationMapper.mapDtoToCoordinate(expectedLocationDto) } throws Exception()

        // When & Then
        assertThrows<Exception> { locationRepository.getCoordinateByCityAndCountry(city, country) }
    }
}