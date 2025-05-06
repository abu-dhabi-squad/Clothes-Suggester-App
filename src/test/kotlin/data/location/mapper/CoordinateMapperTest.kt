package data.location.mapper

import com.google.common.truth.Truth.assertThat
import helper.createDtoLocation
import helper.createDtoWeather
import logic.exception.DataIsNullException
import logic.exception.NoLocationFoundException
import logic.model.Coordinate
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

class CoordinateMapperTest {
    private lateinit var coordinateMapper: CoordinateMapper

    @BeforeEach
    fun setup() {
        coordinateMapper = CoordinateMapper()
    }

    @Test
    fun `mapDtoToCoordinate should return Coordinate`() {
        // Given
        val dtoLocation = createDtoLocation(latitude = 0.0, longitude = 0.0)
        val expectedCoordinate = Coordinate(latitude = 0.0, longitude = 0.0)

        // When
        val actualCoordinate = coordinateMapper.mapDtoToCoordinate(dtoLocation)

        // Then
        assertThat(actualCoordinate).isEqualTo(expectedCoordinate)
    }

    @Test
    fun `mapDtoToCoordinate should throw DataIsNullException when citiesCoordinates is null`(){
        //Given
        val dtoLocation = createDtoLocation(citiesCoordinates = null)
        //When & Then
        assertThrows<DataIsNullException> { coordinateMapper.mapDtoToCoordinate(dtoLocation) }
    }

    @Test
    fun `mapDtoToCoordinate should throw NoLocationFoundException when citiesCoordinates firstOrNull is null`(){
        //Given
        val dtoLocation = createDtoLocation(citiesCoordinates = listOf())
        //When & Then
        assertThrows<NoLocationFoundException> { coordinateMapper.mapDtoToCoordinate(dtoLocation) }
    }

    @Test
    fun `mapDtoToCoordinate should throw DataIsNullException when citiesCoordinates first latitude is null`(){
        //Given
        val dtoLocation = createDtoLocation(latitude = null)
        //When & Then
        assertThrows<DataIsNullException> { coordinateMapper.mapDtoToCoordinate(dtoLocation) }
    }

    @Test
    fun `mapDtoToCoordinate should throw DataIsNullException when citiesCoordinates first longitude is null`(){
        //Given
        val dtoLocation = createDtoLocation(longitude = null)
        //When & Then
        assertThrows<DataIsNullException> { coordinateMapper.mapDtoToCoordinate(dtoLocation) }
    }

    @Test
    fun `mapDtoToCoordinate should throw NoLocationFoundException when coordinates list is empty`() {
        // Given
        val dtoLocation = createDtoLocation(citiesCoordinates = emptyList())

        // When & Then
        assertThrows<NoLocationFoundException> { coordinateMapper.mapDtoToCoordinate(dtoLocation) }
    }
}