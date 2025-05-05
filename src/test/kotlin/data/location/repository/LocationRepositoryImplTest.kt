package data.location.repository

import helper.createDtoLocation
import com.google.common.truth.Truth.assertThat
import data.location.datasource.LocationDataSource
import data.location.repository.mapper.LocationMapper
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import logic.model.Coordinate
import logic.repository.LocationRepository
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

class LocationRepositoryImplTest {
    private lateinit var remoteDataSource: LocationDataSource
    private lateinit var locationMapper: LocationMapper
    private lateinit var locationRepository: LocationRepository

    @BeforeEach
    fun setup() {
        remoteDataSource = mockk(relaxed = true)
        locationMapper = mockk(relaxed = true)
        locationRepository = LocationRepositoryImpl(remoteDataSource, locationMapper)
    }

    @Test
    fun `getLocationByCityAndCountry should return location when provided valid city name and country`() = runTest {
        // Given
        val city = "city"
        val country = "country"
        val expectedLocationDto = createDtoLocation()
        val expectedLocation = Coordinate(expectedLocationDto.locations.first().latitude, expectedLocationDto.locations.first().longitude)
        coEvery { remoteDataSource.getLocationByCityAndCountry(any(), any()) } returns expectedLocationDto
        coEvery { locationMapper.mapDtoToCoordinate(expectedLocationDto) } returns expectedLocation

        // When
        val result = locationRepository.getLocationByCityAndCountry(city, country)

        // Then
        assertThat(result).isEqualTo(expectedLocation)
    }

    @Test
    fun `getLocationByCityAndCountry should throw Exception when datasource throws Exception`() = runTest {
        // Given
        val city = "city"
        val country = "country"
        coEvery { remoteDataSource.getLocationByCityAndCountry(any(), any()) } throws Exception()

        // When & Then
        assertThrows<Exception> { locationRepository.getLocationByCityAndCountry(city, country) }
    }

    @Test
    fun `getLocationByCityAndCountry should throw Exception when mapper throws Exception`() = runTest {
        // Given
        val city = "city"
        val country = "country"
        val expectedLocationDto = createDtoLocation()
        coEvery { remoteDataSource.getLocationByCityAndCountry(any(), any()) } returns expectedLocationDto
        coEvery { locationMapper.mapDtoToCoordinate(expectedLocationDto) } throws Exception()

        // When & Then
        assertThrows<Exception> { locationRepository.getLocationByCityAndCountry(city, country) }
    }
}