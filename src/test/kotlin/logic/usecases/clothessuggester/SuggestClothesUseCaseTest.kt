package logic.usecases.clothessuggester

import com.google.common.truth.Truth.assertThat
import io.mockk.*
import logic.model.*
import logic.repository.ClothesRepository
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import java.util.*

class SuggestClothesUseCaseTest{
    private lateinit var clothesRepository: ClothesRepository
    private lateinit var useCase: SuggestClothesUseCase

    @BeforeEach
    fun setup() {
        clothesRepository = mockk()
        useCase = SuggestClothesUseCase(clothesRepository)
    }

    @Test
    fun `should return HEAVY clothes when avg temperature is below 5`() {
        // Given
        val hourlyTemps = listOf(
            HourlyTemperature(0.0, 1),
            HourlyTemperature(2.0, 2)
        )
        val weather = Weather(hourlyTemps, WeatherCondition.CLEAR_SKY)
        val expectedClothes = listOf(Cloth(UUID.randomUUID(), "Heavy Coat", ClothType.HEAVY))

        every { clothesRepository.getClothByType(ClothType.HEAVY) } returns expectedClothes

        // When
        val result = useCase.getSuggestedClothes(weather)

        // Then
        verify { clothesRepository.getClothByType(ClothType.HEAVY) }
        assertThat(result).isEqualTo(expectedClothes)
    }

    @Test
    fun `should return MEDIUM clothes when avg temperature is between 5 and 15 inclusive`() {
        // Given
        val hourlyTemps = listOf(
            HourlyTemperature(10.0, 1),
            HourlyTemperature(12.0, 2)
        )
        val weather = Weather(hourlyTemps, WeatherCondition.CLEAR_SKY)
        val expectedClothes = listOf(Cloth(UUID.randomUUID(), "Medium Jacket", ClothType.MEDIUM))

        every { clothesRepository.getClothByType(ClothType.MEDIUM) } returns expectedClothes

        // When
        val result = useCase.getSuggestedClothes(weather)

        // Then
        verify { clothesRepository.getClothByType(ClothType.MEDIUM) }
        assertThat(result).isEqualTo(expectedClothes)
    }

    @Test
    fun `should return LIGHT clothes when avg temperature is between 15_1 and 25 inclusive`() {
        // Given
        val hourlyTemps = listOf(
            HourlyTemperature(16.0, 1),
        )
        val weather = Weather(hourlyTemps, WeatherCondition.CLEAR_SKY)
        val expectedClothes = listOf(Cloth(UUID.randomUUID(), "Light Shirt", ClothType.LIGHT))

        every { clothesRepository.getClothByType(ClothType.LIGHT) } returns expectedClothes

        // When
        val result = useCase.getSuggestedClothes(weather)

        // Then
        verify { clothesRepository.getClothByType(ClothType.LIGHT) }
        assertThat(result).isEqualTo(expectedClothes)
    }

    @Test
    fun `should return VERY_LIGHT clothes when avg temperature is above 25`() {
        // Given
        val hourlyTemps = listOf(
            HourlyTemperature(30.0, 1),
            HourlyTemperature(32.0, 2)
        )
        val weather = Weather(hourlyTemps, WeatherCondition.CLEAR_SKY)
        val expectedClothes = listOf(Cloth(UUID.randomUUID(), "T-Shirt", ClothType.VERY_LIGHT))

        every { clothesRepository.getClothByType(ClothType.VERY_LIGHT) } returns expectedClothes

        // When
        val result = useCase.getSuggestedClothes(weather)

        // Then
        verify { clothesRepository.getClothByType(ClothType.VERY_LIGHT) }
        assertThat(result).isEqualTo(expectedClothes)
    }
}