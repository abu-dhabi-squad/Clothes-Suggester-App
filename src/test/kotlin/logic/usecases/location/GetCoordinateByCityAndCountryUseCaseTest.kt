package logic.usecases.location

import com.google.common.truth.Truth.assertThat
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import logic.model.LocationCoordinate
import logic.exception.InvalidCityNameException
import logic.exception.InvalidCountryNameException
import logic.repository.LocationRepository
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

class GetCoordinateByCityAndCountryUseCaseTest {
    private lateinit var locationRepository: LocationRepository
    private lateinit var getCoordinateByCityAndCountryUseCase: GetCoordinateByCityAndCountryUseCase

    @BeforeEach
    fun setup() {
        locationRepository = mockk(relaxed = true)
        getCoordinateByCityAndCountryUseCase = GetCoordinateByCityAndCountryUseCase(locationRepository)
    }

    @Test
    fun `getCoordinateByCityAndCountry should return location when provided valid city name and country`() = runTest {
        // Given
        val city = "city"
        val country = "country"
        val expectedLocation = LocationCoordinate(1.0, 2.0)
        coEvery { locationRepository.getCoordinateByCityAndCountry(city, country) } returns expectedLocation

        // When
        val result = getCoordinateByCityAndCountryUseCase.getCoordinateByCityAndCountry(city, country)

        // Then
        assertThat(result).isEqualTo(expectedLocation)
    }

    @Test
    fun `getCoordinateByCityAndCountry should throw InvalidCityNameException when provided invalid city name`() = runTest {
        // Given
        val city = ""
        val country = "country"

        // When & Then
        assertThrows<InvalidCityNameException> { getCoordinateByCityAndCountryUseCase.getCoordinateByCityAndCountry(city, country) }
    }

    @Test
    fun `getCoordinateByCityAndCountry should throw InvalidCountryNameException when provided invalid country`() = runTest {
        // Given
        val city = "city"
        val country = ""

        // When & Then
        assertThrows<InvalidCountryNameException> { getCoordinateByCityAndCountryUseCase.getCoordinateByCityAndCountry(city, country) }
    }

    @Test
    fun `getCoordinateByCityAndCountry should throw Exception when repository throws Exception`() = runTest {
        // Given
        val city = "city"
        val country = "country"
        coEvery { locationRepository.getCoordinateByCityAndCountry(city, country) } throws Exception()

        // When & Then
        assertThrows<Exception> { getCoordinateByCityAndCountryUseCase.getCoordinateByCityAndCountry(city, country) }
    }
}