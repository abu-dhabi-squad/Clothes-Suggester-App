package logic.clothesSuggester

import com.google.common.truth.Truth.assertThat
import helper.createWeather
import io.mockk.*
import logic.exception.NoMatchingClothesFoundException
import logic.exception.MissingTemperaturesException
import logic.model.*
import logic.repository.ClothesRepository
import logic.usecases.clothesSuggester.GetClothingSuggestionUseCase
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource
import java.util.*
import java.util.stream.Stream

class GetClothingSuggestionUseCaseTest{
    private lateinit var clothesRepository: ClothesRepository
    private lateinit var useCase: GetClothingSuggestionUseCase

    @BeforeEach
    fun setup() {
        clothesRepository = mockk()
        useCase = GetClothingSuggestionUseCase(clothesRepository)
    }

    companion object {
        @JvmStatic
        fun temperatureToClothTypeProvider(): Stream<Arguments> = Stream.of(
            Arguments.of(-5.0, ClothType.VERY_HEAVY),
            Arguments.of(2.5, ClothType.HEAVY),
            Arguments.of(10.0, ClothType.MEDIUM),
            Arguments.of(20.0, ClothType.LIGHT),
            Arguments.of(25.0, ClothType.VERY_LIGHT)
        )
    }

    @ParameterizedTest
    @MethodSource("temperatureToClothTypeProvider")
    fun `should return correct clothes based on temperature`(temperature: Double, expectedType: ClothType) {
        // Given
        val weather = Weather(
            hourlyTemperatures = listOf(HourlyTemperature(temperature, 1)),
            weatherCondition = WeatherCondition.CLEAR_SKY
        )
        val expected = listOf(Cloth(UUID.randomUUID(), "Test Cloth", expectedType))
        every { clothesRepository.getClothByType(expectedType) } returns expected

        // When
        val result = useCase.getSuggestedClothes(weather)

        // Then
        verify { clothesRepository.getClothByType(expectedType) }
        assertThat(result).isEqualTo(expected)
    }

    @Test
    fun `getSuggestedClothes should throw MissingTemperaturesException when hourlyTemperatures is empty`() {
        val weather = createWeather()

        val exception = assertThrows(MissingTemperaturesException::class.java) {
            useCase.getSuggestedClothes(weather)
        }

        assertThat(exception).isInstanceOf(MissingTemperaturesException::class.java)
    }

    @Test
    fun `getSuggestedClothes should throw NoMatchingClothesFoundException when repository returns empty list`() {
        val weather = createWeather(listOf(HourlyTemperature(10.0, 1)))
        every { clothesRepository.getClothByType(ClothType.MEDIUM) } returns listOf()

        val exception = assertThrows(NoMatchingClothesFoundException::class.java) {
            useCase.getSuggestedClothes(weather)
        }

        assertThat(exception).isInstanceOf(NoMatchingClothesFoundException::class.java)
        verify { clothesRepository.getClothByType(ClothType.MEDIUM) }
    }
}