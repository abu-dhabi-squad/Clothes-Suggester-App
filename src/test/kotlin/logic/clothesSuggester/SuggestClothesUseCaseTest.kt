package logic.clothesSuggester

import com.google.common.truth.Truth.assertThat
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import logic.model.*
import logic.repository.ClothesRepository
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class SuggestClothesUseCaseTest{
    private val clothesRepository = mockk<ClothesRepository>()
    private lateinit var useCase: SuggestClothesUseCase

    @BeforeEach
    fun setUp() {
        useCase = SuggestClothesUseCase(clothesRepository)
    }

    @Test
    fun `getSuggestedClothes should return HEAVY clothes for cold weather`() = runTest {
        // given
        val weather = Weather(
            hourlyTemperatures = List(24) { HourlyTemperature(2f, it) },
            weatherCondition = WeatherCondition.SNOW_HEAVY
        )
        val expectedClothes = listOf(Cloth(name = "Winter Coat", type = ClothType.HEAVY))
        coEvery { clothesRepository.getClothesByType(ClothType.HEAVY) } returns expectedClothes

        // when
        val result = useCase.getSuggestedClothes(weather)

        // then
        coVerify { clothesRepository.getClothesByType(ClothType.HEAVY) }
        assertThat(result).isEqualTo(expectedClothes)
    }

    @Test
    fun `getSuggestedClothes should return MEDIUM clothes for cool weather`() = runTest {
        // given
        val weather = Weather(
            hourlyTemperatures = List(24) { HourlyTemperature(10f, it) },
            weatherCondition = WeatherCondition.CLEAR_SKY
        )
        coEvery { clothesRepository.getClothesByType(ClothType.MEDIUM) } returns listOf()

        // when
        useCase.getSuggestedClothes(weather)

        // then
        coVerify { clothesRepository.getClothesByType(ClothType.MEDIUM) }
    }

    @Test
    fun `getSuggestedClothes should return LIGHT clothes for mild weather`() = runTest {
        // given
        val weather = Weather(
            hourlyTemperatures = List(24) { HourlyTemperature(20f, it) },
            weatherCondition = WeatherCondition.CLEAR_SKY
        )
        coEvery { clothesRepository.getClothesByType(ClothType.LIGHT) } returns listOf()

        // when
        useCase.getSuggestedClothes(weather)

        // then
        coVerify { clothesRepository.getClothesByType(ClothType.LIGHT) }
    }

    @Test
    fun `getSuggestedClothes should return VERY_LIGHT clothes for hot weather`() = runTest {
        // given
        val weather = Weather(
            hourlyTemperatures = List(24) { HourlyTemperature(30f, it) },
            weatherCondition = WeatherCondition.CLEAR_SKY
        )
        coEvery { clothesRepository.getClothesByType(ClothType.VERY_LIGHT) } returns listOf()

        // when
        useCase.getSuggestedClothes(weather)

        // then
        coVerify { clothesRepository.getClothesByType(ClothType.VERY_LIGHT) }
    }

    @Test
    fun `getSuggestedClothes should return empty list if hourly temperatures are empty`() = runTest {
        // given
        val weather = Weather(
            hourlyTemperatures = emptyList(),
            weatherCondition = WeatherCondition.CLEAR_SKY
        )

        // when
        val result = useCase.getSuggestedClothes(weather)

        // then
        assertThat(result).isEmpty()
        coVerify(exactly = 0) { clothesRepository.getClothesByType(any()) }
    }
}