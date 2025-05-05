package logic.location

import com.google.common.truth.Truth.assertThat
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import logic.exception.InvalidCityNameException
import logic.exception.InvalidCountryNameException
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
    fun `getLocation should return location when provided valid city name and country`() = runTest {
        // Given
        val city = "city"
        val country = "country"
        val expectedLocation = Coordinate(1.0, 2.0)
        coEvery { locationRepository.getLocationByCityAndCountry(city, country) } returns expectedLocation

        // When
        val result = getLocationUseCase.getLocation(city, country)

        // Then
        assertThat(result).isEqualTo(expectedLocation)
    }

    @Test
    fun `getLocation should throw InvalidCityNameException when provided invalid city name`() = runTest {
        // Given
        val city = ""
        val country = "country"

        // When & Then
        assertThrows<InvalidCityNameException> { getLocationUseCase.getLocation(city, country) }
    }

    @Test
    fun `getLocation should throw InvalidCountryNameException when provided invalid country`() = runTest {
        // Given
        val city = "city"
        val country = ""

        // When & Then
        assertThrows<InvalidCountryNameException> { getLocationUseCase.getLocation(city, country) }
    }

    @Test
    fun `getLocation should throw Exception when repository throws Exception`() = runTest {
        // Given
        val city = "city"
        val country = "country"
        coEvery { locationRepository.getLocationByCityAndCountry(city, country) } throws Exception()

        // When & Then
        assertThrows<Exception> { getLocationUseCase.getLocation(city, country) }
    }
}