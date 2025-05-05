package logic.clothesSuggester

import com.google.common.truth.Truth.assertThat
import io.mockk.*
import logic.model.*
import logic.repository.ClothesRepository
import org.junit.jupiter.api.Assertions.*
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
    fun `getSuggestedClothes should return empty list when hourly temperatures is empty`() {
        // Given
        val weather = Weather(hourlyTemperatures = emptyList(), weatherCondition = WeatherCondition.CLEAR_SKY)

        // When
        val result = useCase.getSuggestedClothes(weather)

        // Then
        assertThat(result).isEmpty()
    }

    @Test
    fun `getSuggestedClothes should return HEAVY clothes when average temp is below 5`() {
        // Given
        val weather = Weather(
            hourlyTemperatures = listOf(
                HourlyTemperature(2f, 0),
                HourlyTemperature(4f, 1)
            ),
            weatherCondition = WeatherCondition.CLEAR_SKY
        )
        val clothes = listOf(
            Cloth(UUID.randomUUID(), "Heavy Jacket", ClothType.HEAVY)
        )
        every { clothesRepository.getClothesByType(ClothType.HEAVY) } returns clothes

        // When
        val result = useCase.getSuggestedClothes(weather)

        // Then
        verify(exactly = 1) { clothesRepository.getClothesByType(ClothType.HEAVY) }
        assertThat(result).isEqualTo(clothes)
    }

    @Test
    fun `getSuggestedClothes should return MEDIUM clothes when average temp is between 5 and 15`() {
        // Given
        val weather = Weather(
            hourlyTemperatures = listOf(
                HourlyTemperature(10f, 0),
                HourlyTemperature(12f, 1)
            ),
            weatherCondition = WeatherCondition.CLEAR_SKY
        )
        val clothes = listOf(
            Cloth(UUID.randomUUID(), "Sweater", ClothType.MEDIUM)
        )
        every { clothesRepository.getClothesByType(ClothType.MEDIUM) } returns clothes

        // When
        val result = useCase.getSuggestedClothes(weather)

        // Then
        verify(exactly = 1) { clothesRepository.getClothesByType(ClothType.MEDIUM) }
        assertThat(result).isEqualTo(clothes)
    }

    @Test
    fun `getSuggestedClothes should return LIGHT clothes when average temp is between 15_1 and 25`() {
        // Given
        val weather = Weather(
            hourlyTemperatures = listOf(
                HourlyTemperature(20f, 0),
                HourlyTemperature(22f, 1)
            ),
            weatherCondition = WeatherCondition.CLEAR_SKY
        )
        val clothes = listOf(
            Cloth(UUID.randomUUID(), "T-Shirt", ClothType.LIGHT)
        )
        every { clothesRepository.getClothesByType(ClothType.LIGHT) } returns clothes

        // When
        val result = useCase.getSuggestedClothes(weather)

        // Then
        verify(exactly = 1) { clothesRepository.getClothesByType(ClothType.LIGHT) }
        assertThat(result).isEqualTo(clothes)
    }

    @Test
    fun `getSuggestedClothes should return VERY_LIGHT clothes when average temp is above 25`() {
        // Given
        val weather = Weather(
            hourlyTemperatures = listOf(
                HourlyTemperature(30f, 0),
                HourlyTemperature(28f, 1)
            ),
            weatherCondition = WeatherCondition.CLEAR_SKY
        )
        val clothes = listOf(
            Cloth(UUID.randomUUID(), "Tank Top", ClothType.VERY_LIGHT)
        )
        every { clothesRepository.getClothesByType(ClothType.VERY_LIGHT) } returns clothes

        // When
        val result = useCase.getSuggestedClothes(weather)

        // Then
        verify(exactly = 1) { clothesRepository.getClothesByType(ClothType.VERY_LIGHT) }
        assertThat(result).isEqualTo(clothes)
    }
}