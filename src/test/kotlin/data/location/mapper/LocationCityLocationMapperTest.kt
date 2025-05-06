package data.location.mapper

import com.google.common.truth.Truth.assertThat
import helper.createDtoLocation
import logic.exception.NoLocationFoundException
import logic.model.LocationCoordinate

import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

class CoordinateMapperTest {
    private lateinit var coordinateMapper: CityLocationMapper

    @BeforeEach
    fun setup() {
        coordinateMapper = CityLocationMapper()
    }

    @Test
    fun `mapDtoToCoordinate should return Coordinate`() {
        // Given
        val dtoLocation = createDtoLocation()
        val expectedCoordinate = LocationCoordinate(dtoLocation.citiesCoordinates.first().latitude, dtoLocation.citiesCoordinates.first().longitude)

        // When
        val actualCoordinate = coordinateMapper.mapDtoToCoordinate(dtoLocation)

        // Then
        assertThat(actualCoordinate).isEqualTo(expectedCoordinate)
    }

    @Test
    fun `mapDtoToCoordinate should throw NoLocationFoundException when coordinates list is empty`() {
        // Given
        val dtoLocation = createDtoLocation(citiesCoordinates = emptyList())

        // When & Then
        assertThrows<NoLocationFoundException> { coordinateMapper.mapDtoToCoordinate(dtoLocation) }
    }
}