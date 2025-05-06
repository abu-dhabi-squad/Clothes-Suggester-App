package logic.clothesSuggester

import com.google.common.truth.Truth.assertThat
import helper.createWeather
import io.mockk.*
import logic.exception.EmptyClotheListException
import logic.exception.EmptyTemperatureListException
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
    fun `should return VERY_HEAVY clothes when avg temperature is below 0`() {
        // Given
        val weather = Weather(
            hourlyTemperatures = listOf(HourlyTemperature(-5.0, 1)),
            weatherCondition = WeatherCondition.CLEAR_SKY
        )
        val expected = listOf(Cloth(UUID.randomUUID(), "Snow Jacket", ClothType.VERY_HEAVY))
        every { clothesRepository.getClothesByType(ClothType.VERY_HEAVY) } returns expected

        // When
        val result = useCase.getSuggestedClothes(weather)

        // Then
        verify { clothesRepository.getClothesByType(ClothType.VERY_HEAVY) }
        assertThat(result).isEqualTo(expected)
    }

    @Test
    fun `should return HEAVY clothes when avg temperature is between 0 and 5`() {
        val weather = Weather(
            hourlyTemperatures = listOf(HourlyTemperature(2.5, 1)),
            weatherCondition = WeatherCondition.CLEAR_SKY
        )
        val expected = listOf(Cloth(UUID.randomUUID(), "Heavy Coat", ClothType.HEAVY))
        every { clothesRepository.getClothesByType(ClothType.HEAVY) } returns expected

        val result = useCase.getSuggestedClothes(weather)

        verify { clothesRepository.getClothesByType(ClothType.HEAVY) }
        assertThat(result).isEqualTo(expected)
    }

    @Test
    fun `should return MEDIUM clothes when avg temperature is between 5 and 15`() {
        val weather = Weather(
            hourlyTemperatures = listOf(HourlyTemperature(10.0, 1)),
            weatherCondition = WeatherCondition.CLEAR_SKY
        )
        val expected = listOf(Cloth(UUID.randomUUID(), "Medium Jacket", ClothType.MEDIUM))
        every { clothesRepository.getClothesByType(ClothType.MEDIUM) } returns expected

        val result = useCase.getSuggestedClothes(weather)

        verify { clothesRepository.getClothesByType(ClothType.MEDIUM) }
        assertThat(result).isEqualTo(expected)
    }

    @Test
    fun `should return LIGHT clothes when avg temperature is between 15 and 22`() {
        val weather = Weather(
            hourlyTemperatures = listOf(HourlyTemperature(20.0, 1)),
            weatherCondition = WeatherCondition.CLEAR_SKY
        )
        val expected = listOf(Cloth(UUID.randomUUID(), "Light Shirt", ClothType.LIGHT))
        every { clothesRepository.getClothesByType(ClothType.LIGHT) } returns expected

        val result = useCase.getSuggestedClothes(weather)

        verify { clothesRepository.getClothesByType(ClothType.LIGHT) }
        assertThat(result).isEqualTo(expected)
    }

    @Test
    fun `should return VERY_LIGHT clothes when avg temperature is 22 or above`() {
        val weather = Weather(
            hourlyTemperatures = listOf(HourlyTemperature(25.0, 1)),
            weatherCondition = WeatherCondition.CLEAR_SKY
        )
        val expected = listOf(Cloth(UUID.randomUUID(), "T-Shirt", ClothType.VERY_LIGHT))
        every { clothesRepository.getClothesByType(ClothType.VERY_LIGHT) } returns expected

        val result = useCase.getSuggestedClothes(weather)

        verify { clothesRepository.getClothesByType(ClothType.VERY_LIGHT) }
        assertThat(result).isEqualTo(expected)
    }

    @Test
    fun `getSuggestedClothes should throw EmptyTemperatureListException when hourlyTemperatures is empty`() {
        val weather = createWeather()

        val exception = assertThrows(EmptyTemperatureListException::class.java) {
            useCase.getSuggestedClothes(weather)
        }

        assertThat(exception).isInstanceOf(EmptyTemperatureListException::class.java)
    }

    @Test
    fun `getSuggestedClothes should throw EmptyClotheListException when repository returns empty list`() {
        val weather =  createWeather(listOf(HourlyTemperature(10.0,1)))
        every { clothesRepository.getClothesByType(ClothType.MEDIUM) } returns listOf()

        val exception = assertThrows(EmptyClotheListException::class.java) {
            useCase.getSuggestedClothes(weather)
        }

        assertThat(exception).isInstanceOf(EmptyClotheListException::class.java)
        verify { clothesRepository.getClothesByType(ClothType.MEDIUM) }
    }
}