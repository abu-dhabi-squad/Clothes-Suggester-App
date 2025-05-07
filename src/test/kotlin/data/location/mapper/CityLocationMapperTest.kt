package data.location.mapper

import com.google.common.truth.Truth.assertThat
import helper.createDtoLocation
import logic.exception.NoLocationFoundException
import logic.model.LocationCoordinate
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

class CityLocationMapperTest {
    private lateinit var locationMapper: CityLocationMapper

    @BeforeEach
    fun setup() {
        locationMapper = CityLocationMapper()
    }

    @Test
    fun `mapDtoToLocationCoordinate should return LocationCoordinate`() {
        // Given
        val dtoLocation = createDtoLocation(latitude = 0.0, longitude = 0.0)
        val expectedLocation = LocationCoordinate(latitude = 0.0, longitude = 0.0)

        // When
        val actualLocation = locationMapper.mapDtoToLocationCoordinate(dtoLocation)

        // Then
        assertThat(actualLocation).isEqualTo(expectedLocation)
    }

    @Test
    fun `mapDtoToLocationCoordinate should throw NoLocationFoundException when citiesCoordinates is null`() {
        //Given
        val dtoLocation = createDtoLocation(citiesCoordinates = null)
        
        //When & Then
        assertThrows<NoLocationFoundException> { locationMapper.mapDtoToLocationCoordinate(dtoLocation) }
    }

    @Test
    fun `mapDtoToLocationCoordinate should throw NoLocationFoundException when citiesCoordinates firstOrNull is null`() {
        //Given
        val dtoLocation = createDtoLocation(citiesCoordinates = listOf())

        //When & Then
        assertThrows<NoLocationFoundException> { locationMapper.mapDtoToLocationCoordinate(dtoLocation) }
    }

    @Test
    fun `mapDtoToLocationCoordinate should throw NoLocationFoundException when citiesCoordinates first latitude is null`() {
        //Given
        val dtoLocation = createDtoLocation(latitude = null)

        //When & Then
        assertThrows<NoLocationFoundException> { locationMapper.mapDtoToLocationCoordinate(dtoLocation) }
    }

    @Test
    fun `mapDtoToLocationCoordinate should throw NoLocationFoundException when citiesCoordinates first longitude is null`() {
        //Given
        val dtoLocation = createDtoLocation(longitude = null)

        //When & Then
        assertThrows<NoLocationFoundException> { locationMapper.mapDtoToLocationCoordinate(dtoLocation) }
    }

    @Test
    fun `mapDtoToLocationCoordinate should throw NoLocationFoundException when coordinates list is empty`() {
        // Given
        val dtoLocation = createDtoLocation(citiesCoordinates = emptyList())

        // When & Then
        assertThrows<NoLocationFoundException> { locationMapper.mapDtoToLocationCoordinate(dtoLocation) }
    }
}