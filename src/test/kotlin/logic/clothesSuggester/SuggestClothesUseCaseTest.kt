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
    fun `getSuggestedClothes should return HEAVY clothes when average temperature is less than 5`() {
        // Given
        val weather = Weather(
            hourlyTemperatures = listOf(
                HourlyTemperature(0f, 0),
                HourlyTemperature(4f, 1)
            ),
            weatherCondition = WeatherCondition.CLEAR_SKY
        )
        val expectedClothes = listOf(
            Cloth(UUID.randomUUID(), "Heavy Coat", ClothType.HEAVY)
        )
        every { clothesRepository.getClothesByType(ClothType.HEAVY) } returns expectedClothes

        // When
        val result = useCase.getSuggestedClothes(weather)

        // Then
        verify { clothesRepository.getClothesByType(ClothType.HEAVY) }
        assertThat(result).isEqualTo(expectedClothes)
    }

    @Test
    fun `getSuggestedClothes should return MEDIUM clothes when average temperature is between 5 and 15`() {
        // Given
        val weather = Weather(
            hourlyTemperatures = listOf(
                HourlyTemperature(10f, 0),
                HourlyTemperature(12f, 1)
            ),
            weatherCondition = WeatherCondition.CLEAR_SKY
        )
        val expectedClothes = listOf(
            Cloth(UUID.randomUUID(), "Sweater", ClothType.MEDIUM)
        )
        every { clothesRepository.getClothesByType(ClothType.MEDIUM) } returns expectedClothes

        // When
        val result = useCase.getSuggestedClothes(weather)

        // Then
        verify { clothesRepository.getClothesByType(ClothType.MEDIUM) }
        assertThat(result).isEqualTo(expectedClothes)
    }

    @Test
    fun `getSuggestedClothes should return LIGHT clothes when average temperature is between 15_1 and 25`() {
        // Given
        val weather = Weather(
            hourlyTemperatures = listOf(
                HourlyTemperature(20f, 0),
                HourlyTemperature(22f, 1)
            ),
            weatherCondition = WeatherCondition.CLEAR_SKY
        )
        val expectedClothes = listOf(
            Cloth(UUID.randomUUID(), "T-shirt", ClothType.LIGHT)
        )
        every { clothesRepository.getClothesByType(ClothType.LIGHT) } returns expectedClothes

        // When
        val result = useCase.getSuggestedClothes(weather)

        // Then
        verify { clothesRepository.getClothesByType(ClothType.LIGHT) }
        assertThat(result).isEqualTo(expectedClothes)
    }

    @Test
    fun `getSuggestedClothes should return VERY_LIGHT clothes when average temperature is above 25`() {
        // Given
        val weather = Weather(
            hourlyTemperatures = listOf(
                HourlyTemperature(27f, 0),
                HourlyTemperature(30f, 1)
            ),
            weatherCondition = WeatherCondition.CLEAR_SKY
        )
        val expectedClothes = listOf(
            Cloth(UUID.randomUUID(), "Tank Top", ClothType.VERY_LIGHT)
        )
        every { clothesRepository.getClothesByType(ClothType.VERY_LIGHT) } returns expectedClothes

        // When
        val result = useCase.getSuggestedClothes(weather)

        // Then
        verify { clothesRepository.getClothesByType(ClothType.VERY_LIGHT) }
        assertThat(result).isEqualTo(expectedClothes)
    }

    @Test
    fun `getSuggestedClothes should return MEDIUM clothes when average temperature is exactly 5 or 15`() {
        val temperatures = listOf(5.0f, 5.0f) // avg = 5.0
        val weather = Weather(temperatures.mapIndexed { i, t -> HourlyTemperature(t, i) }, WeatherCondition.CLEAR_SKY)
        val expected = listOf(Cloth(UUID.randomUUID(), "Jacket", ClothType.MEDIUM))
        every { clothesRepository.getClothesByType(ClothType.MEDIUM) } returns expected

        val result = useCase.getSuggestedClothes(weather)

        verify { clothesRepository.getClothesByType(ClothType.MEDIUM) }
        assertThat(result).isEqualTo(expected)
    }

    @Test
    fun `getSuggestedClothes should return LIGHT clothes when average temperature is exactly 15_1 or 25`() {
        val temperatures = listOf(15.1f, 25.0f) // avg = 20.05
        val weather = Weather(temperatures.mapIndexed { i, t -> HourlyTemperature(t, i) }, WeatherCondition.CLEAR_SKY)
        val expected = listOf(Cloth(UUID.randomUUID(), "Hoodie", ClothType.LIGHT))
        every { clothesRepository.getClothesByType(ClothType.LIGHT) } returns expected

        val result = useCase.getSuggestedClothes(weather)

        verify { clothesRepository.getClothesByType(ClothType.LIGHT) }
        assertThat(result).isEqualTo(expected)
    }
}