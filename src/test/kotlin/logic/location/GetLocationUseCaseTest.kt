package logic.location

import com.google.common.truth.Truth.assertThat
import io.mockk.every
import io.mockk.mockk
import logic.exception.InvalidCityNameException
import logic.exception.InvalidCountryNameException
import logic.exception.NoLocationFoundException
import logic.model.Coordinate
import logic.repository.LocationRepository
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

class GetLocationUseCaseTest {

    private lateinit var locationRepository: LocationRepository
    private lateinit var getLocationUseCase: GetLocationUseCase

    @BeforeEach
    fun setup() {
        locationRepository = mockk(relaxed = true)
        getLocationUseCase = GetLocationUseCase(locationRepository)
    }

    @Test
    fun `getLocation should return location when provided valid city name and country`() {
        // Given
        val city = "city"
        val country = "country"
        val expectedLocation = Coordinate(1.0, 2.0)
        every { locationRepository.getLocationByCityAndCountry(city, country) } returns expectedLocation

        // When
        val result = getLocationUseCase.getLocation(city, country)

        // Then
        assertThat(result).isEqualTo(expectedLocation)
    }

    @Test
    fun `getLocation should throw InvalidCityNameException when provided invalid city name`() {
        // Given
        val city = ""
        val country = "country"

        // When & Then
        assertThrows<InvalidCityNameException> { getLocationUseCase.getLocation(city, country) }
    }

    @Test
    fun `getLocation should throw InvalidCountryNameException when provided invalid country`() {
        // Given
        val city = "city"
        val country = ""

        // When & Then
        assertThrows<InvalidCountryNameException> { getLocationUseCase.getLocation(city, country) }
    }

    @Test
    fun `getLocation should throw NoLocationFoundException when repository returns null`() {
        // Given
        val city = "city"
        val country = "country"
        every { locationRepository.getLocationByCityAndCountry(city, country) } returns null

        // When & Then
        assertThrows<NoLocationFoundException> { getLocationUseCase.getLocation(city, country) }
    }
}