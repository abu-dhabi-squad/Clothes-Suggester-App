package ui

import io.mockk.coEvery
import io.mockk.mockk
import io.mockk.verify
import logic.model.Cloth
import logic.model.ClothType
import logic.usecases.clothesSuggester.SuggestClothesUseCase
import logic.usecases.location.GetCoordinateByIpUseCase
import logic.usecases.weather.GetDailyWeatherByCoordinateUseCase
import main.kotlin.ui.ClothesSuggesterByIpUI
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import presentation.ui_io.Printer

class ClothesSuggesterByIpUITest {
    private lateinit var clothesSuggesterByIpUI: ClothesSuggesterByIpUI
    private lateinit var printer: Printer
    private lateinit var getCoordinateByIpUseCase: GetCoordinateByIpUseCase
    private lateinit var getDailyWeatherByCoordinateUseCase: GetDailyWeatherByCoordinateUseCase
    private lateinit var getSuggestedClothes: SuggestClothesUseCase

    @BeforeEach
    fun setup() {
        printer = mockk(relaxed = true)
        getCoordinateByIpUseCase = mockk(relaxed = true)
        getDailyWeatherByCoordinateUseCase = mockk(relaxed = true)
        getSuggestedClothes = mockk(relaxed = true)
        clothesSuggesterByIpUI = ClothesSuggesterByIpUI(
            printer,
            getCoordinateByIpUseCase ,
            getDailyWeatherByCoordinateUseCase,
            getSuggestedClothes
        )
    }

    @Test
    fun `should display suggested clothes when input is valid and no error occurs`() {
        // Given
        coEvery { getSuggestedClothes.getSuggestedClothes(any()) } returns listOf(
            Cloth(
                name = "T-shirt",
                type = ClothType.MEDIUM
            )
        )
        // When
        clothesSuggesterByIpUI.launchUi()
        // Then
        verify { printer.displayLn(match { it.toString().contains("Suggested Clothes") }) }
    }


    @Test
    fun `should display error message when getCoordinateByIpUseCase throw exception`() {
        // Given

        val error = "there are error will getting the location"
        coEvery { getCoordinateByIpUseCase.getCoordinateByIp() } throws Exception(error)
        //
        clothesSuggesterByIpUI.launchUi()
        // Then
        verify { printer.displayLn(error) }
    }

    @Test
    fun `should display error message when getDailyWeatherByCoordinateUseCase throw exception`() {
        // Given
        val error = "there are error will while getting daily weather"
        coEvery { getDailyWeatherByCoordinateUseCase.getDailyWeather(any()) } throws Exception(error)
        // When
        clothesSuggesterByIpUI.launchUi()
        // Then
        verify { printer.displayLn(error) }
    }

    @Test
    fun `should display error message when getSuggestedClothes throw exception`() {
        // Given
        val error = "there are error will while getting suggesting clothes"
        coEvery { getSuggestedClothes.getSuggestedClothes(any()) } throws Exception(error)
        // When
        clothesSuggesterByIpUI.launchUi()
        // Then
        verify { printer.displayLn(error) }
    }


}