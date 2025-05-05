package logic.clothesSuggester

import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import logic.model.ClothType
import logic.model.HourlyTemperature
import logic.model.Weather
import logic.model.WeatherCondition
import logic.repository.ClothesRepository
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class SuggestClothesUseCaseTest{
    private lateinit var clothesRepository: ClothesRepository
    private lateinit var useCase: SuggestClothesUseCase

    @BeforeEach
    fun setup() {
        clothesRepository = mockk()
        useCase = SuggestClothesUseCase(clothesRepository)
    }


    @Test
    fun `getSuggestedClothes should call HEAVY when temperature is very cold`() = runTest {
        // given
        val weather = Weather(
            hourlyTemperatures = List(24) { HourlyTemperature(-8f, it) },
            weatherCondition = WeatherCondition.COLD
        )

        // when
        useCase.getSuggestedClothes(weather)

        // then
        coVerify { clothesRepository.getClothesByType(ClothType.HEAVY) }
    }

    @Test
    fun `getSuggestedClothes should call VERY_LIGHT when temperature is very hot`() = runTest {
        // given
        val weather = Weather(
            hourlyTemperatures = List(24) { HourlyTemperature(35f, it) },
            weatherCondition = WeatherCondition.SUNNY
        )

        // when
        useCase.getSuggestedClothes(weather)

        // then
        coVerify { clothesRepository.getClothesByType(ClothType.VERY_LIGHT) }
    }

    @Test
    fun `getSuggestedClothes should call MEDIUM and HEAVY when temperature is moderate but stormy`() = runTest {
        // given
        val weather = Weather(
            hourlyTemperatures = List(24) { HourlyTemperature(10f, it) },
            weatherCondition = WeatherCondition.THUNDERSTORM
        )

        // when
        useCase.getSuggestedClothes(weather)

        // then
        coVerify { clothesRepository.getClothesByType(ClothType.MEDIUM) }
        coVerify { clothesRepository.getClothesByType(ClothType.HEAVY) }
    }

    @Test
    fun `getSuggestedClothes should not call repository when no temperatures are available`() = runTest {
        // given
        val weather = Weather(
            hourlyTemperatures = emptyList(),
            weatherCondition = WeatherCondition.SUNNY
        )

        // when
        useCase.getSuggestedClothes(weather)

        // then
        coVerify(exactly = 0) { clothesRepository.getClothesByType(any()) }
    }
}