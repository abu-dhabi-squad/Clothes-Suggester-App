package ui.clothessuggester

import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.ExperimentalCoroutinesApi
import logic.clothesSuggester.SuggestClothesUseCase
import logic.location.GetLocationUseCase
import logic.model.Cloth
import logic.model.ClothType
import logic.weather.GetDailyWeatherByCoordinateUseCase
import main.kotlin.ui.ClothesSuggesterByCityNameUI
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.CsvSource
import presentation.ui_io.InputReader
import presentation.ui_io.Printer

class ClothesSuggesterByCityNameUITest {

    private lateinit var clothesSuggesterUi: ClothesSuggesterByCityNameUI
    private lateinit var inputReader: InputReader
    private lateinit var printer: Printer
    private lateinit var getLocationUseCase: GetLocationUseCase
    private lateinit var getDailyWeatherByCoordinateUseCase: GetDailyWeatherByCoordinateUseCase
    private lateinit var getSuggestedClothes: SuggestClothesUseCase

    @BeforeEach
    fun setup() {
        inputReader = mockk(relaxed = true)
        printer = mockk(relaxed = true)
        getLocationUseCase = mockk(relaxed = true)
        getDailyWeatherByCoordinateUseCase = mockk(relaxed = true)
        getSuggestedClothes = mockk(relaxed = true)
        clothesSuggesterUi = ClothesSuggesterByCityNameUI(
            printer,
            inputReader,
            getLocationUseCase,
            getDailyWeatherByCoordinateUseCase,
            getSuggestedClothes
        )
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `should display suggested clothes when input is valid and no error occurs`() {
        // Given
        every { inputReader.readString() } returns "London" andThen "UK"
        coEvery { getSuggestedClothes.getSuggestedClothes(any()) } returns listOf(
            Cloth(
                name = "T-shirt",
                type = ClothType.MEDIUM
            )
        )
        // When
        clothesSuggesterUi.launchUi()
        // Then
        verify { printer.displayLn(match { it.toString().contains("Suggested Clothes") }) }
    }

    @ParameterizedTest
    @CsvSource("'',London","null,London", nullValues = ["null"])
    fun `should display error message when input is invalid`(cityNameFirstAttempt : String?,cityNameSecondAttempt:String) {
        // Given
        every { inputReader.readString() } returns cityNameFirstAttempt andThen cityNameSecondAttempt andThen "UK"
        //
        clothesSuggesterUi.launchUi()
        // Then
        verify { printer.displayLn("Input cannot be empty.") }
    }

    //    getLocationUseCase = mockk(relaxed = true)
//    getDailyWeatherByCoordinateUseCase = mockk(relaxed = true)
//    getSuggestedClothes = mockk(relaxed = true)
    @Test
    fun `should display error message when getLocationUseCase throw exception`() {
        // Given
        val cityName = "London"
        val countryName = "UK"
        val error = "there are error will getting the location"
        every { inputReader.readString() } returns "London" andThen "UK"
        coEvery { getLocationUseCase.getLocation(cityName, countryName) } throws Exception(error)
        //
        clothesSuggesterUi.launchUi()
        // Then
        verify { printer.display(error) }
    }

    @Test
    fun `should display error message when getDailyWeatherByCoordinateUseCase throw exception`() {
        // Given
        val cityName = "London"
        val countryName = "UK"
        val error = "there are error will while getting daily weather"
        every { inputReader.readString() } returns "London" andThen "UK"
        coEvery { getLocationUseCase.getLocation(cityName, countryName) } throws Exception(error)
        //
        clothesSuggesterUi.launchUi()
        // Then
        verify { printer.display(error) }
    }

    @Test
    fun `should display error message when getSuggestedClothes throw exception`() {
        // Given
        val cityName = "London"
        val countryName = "UK"
        val error = "there are error will while getting suggesting clothes"
        every { inputReader.readString() } returns "London" andThen "UK"
        coEvery { getLocationUseCase.getLocation(cityName, countryName) } throws Exception(error)
        //
        clothesSuggesterUi.launchUi()
        // Then
        verify { printer.display(error) }
    }



}