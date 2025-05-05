package data.location.repository.mapper

import com.google.common.truth.Truth.assertThat
import helper.createDtoLocation
import logic.model.Coordinate
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class LocationMapperImplTest {
    private lateinit var locationMapper: LocationMapper

    @BeforeEach
    fun setup() {
        locationMapper = LocationMapperImpl()
    }

    @Test
    fun `mapDtoToCoordinate should return Coordinate`() {
        val dtoLocation = createDtoLocation()
        val expectedCoordinate = Coordinate(dtoLocation.locations.first().latitude, dtoLocation.locations.first().longitude)
        val actualCoordinate = locationMapper.mapDtoToCoordinate(dtoLocation)
        assertThat(actualCoordinate).isEqualTo(expectedCoordinate)
    }
}