package logic.usecases.location

import com.google.common.truth.Truth.assertThat
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import logic.model.LocationCoordinate
import logic.repository.LocationRepository
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

class GetCoordinateByIpUseCaseTest {
    private lateinit var locationRepository: LocationRepository
    private lateinit var getCoordinateByIpUseCase: GetCoordinateByIpUseCase

    @BeforeEach
    fun setup() {
        locationRepository = mockk(relaxed = true)
        getCoordinateByIpUseCase = GetCoordinateByIpUseCase(locationRepository)
    }

    @Test
    fun `getCoordinateByIp should return location when provided valid city name and country`() = runTest {
        // Given
        val expectedLocation = LocationCoordinate(1.0, 2.0)
        coEvery { locationRepository.getLocationByIp() } returns expectedLocation

        // When
        val result = getCoordinateByIpUseCase.getCoordinateByIp()

        // Then
        assertThat(result).isEqualTo(expectedLocation)
    }

    @Test
    fun `getCoordinateByIp should throw Exception when repository throws Exception`() = runTest {
        // Given
        coEvery { locationRepository.getLocationByIp() } throws Exception()

        // When & Then
        assertThrows<Exception> { getCoordinateByIpUseCase.getCoordinateByIp() }
    }
}