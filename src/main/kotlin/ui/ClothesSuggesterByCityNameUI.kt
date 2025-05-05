package main.kotlin.ui

import kotlinx.coroutines.runBlocking
import logic.clothesSuggester.SuggestClothesUseCase
import logic.location.GetLocationUseCase
import logic.model.Cloth
import logic.weather.GetDailyWeatherByCoordinateUseCase

import presentation.ui_io.InputReader
import presentation.ui_io.Printer

class ClothesSuggesterByCityNameUI(
    private val printer: Printer,
    private val inputReader: InputReader,
    private val getLocationUseCase: GetLocationUseCase,
    private val getDailyWeatherByCoordinateUseCase: GetDailyWeatherByCoordinateUseCase,
    private val getSuggestedClothes: SuggestClothesUseCase
) : UiLauncher {

    private var suggestedClothes: List<Cloth>? = null
    override fun launchUi() {
        val cityName = promptNonEmptyString("Enter city name: ")
        val countryName = promptNonEmptyString("Enter country name: ")
        runBlocking {
            onGetSuggestingClothesExecute(cityName, countryName)
            suggestedClothes?.let {
                displaySuggestedClothes(it)
            }
        }
    }

    private fun promptNonEmptyString(prompt: String): String {
        while (true) {
            printer.display(prompt)
            val input = inputReader.readString()
            if (!input.isNullOrBlank()) return input
            printer.displayLn("Input cannot be empty.")
        }
    }

    private suspend fun onGetSuggestingClothesExecute(cityName: String, countryName: String) {
        try {
            val coordinate =
                getLocationUseCase.getLocation(cityName = cityName, country = countryName)
            val weather = getDailyWeatherByCoordinateUseCase.getDailyWeather(coordinate)
            suggestedClothes = getSuggestedClothes.getSuggestedClothes(weather)
        } catch (ex: Exception) {
            printer.display(ex.message)
        }
    }

    private fun displaySuggestedClothes(clothes: List<Cloth>) {
        printer.displayLn("\n👕 Suggested Clothes:\n")
        clothes.forEachIndexed { index, cloth ->
            printer.displayLn(
                """
                ${index + 1}. ✨ ${cloth.name.uppercase()} ✨
                   • Type: ${cloth.type}
                """.trimIndent()
            )
        }
    }

}

