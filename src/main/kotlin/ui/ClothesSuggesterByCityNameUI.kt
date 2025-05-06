package main.kotlin.ui

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import logic.clothesSuggester.GetClothingSuggestionUseCase
import logic.location.GetCoordinateByCityAndCountryUseCase
import logic.model.Cloth
import logic.weather.GetDailyWeatherByCoordinateUseCase

import presentation.ui_io.InputReader
import presentation.ui_io.Printer

class ClothesSuggesterByCityNameUI(
    private val printer: Printer,
    private val inputReader: InputReader,
    private val getCoordinateByCityAndCountryUseCase: GetCoordinateByCityAndCountryUseCase,
    private val getDailyWeatherByCoordinateUseCase: GetDailyWeatherByCoordinateUseCase,
    private val getSuggestedClothes: GetClothingSuggestionUseCase
) : UiLauncher {

    private var suggestedClothes: List<Cloth>? = null
    val customCoroutineScope = CoroutineScope(Dispatchers.Default)
    override fun launchUi() {
        val cityName = promptNonEmptyString("Enter city name: ")
        val countryName = promptNonEmptyString("Enter country name: ")
        loading()
        runBlocking {
            onGetSuggestingClothesExecute(cityName, countryName)
            suggestedClothes?.let {
                displaySuggestedClothes(it)
            }
            customCoroutineScope.cancel()
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

    private fun loading() {
        customCoroutineScope.launch {
            while (true) {
                for (char in "Loading ...") {
                    printer.display(char)
                    delay(250)
                }
                printer.display("\r ")
            }
        }
    }

    private suspend fun onGetSuggestingClothesExecute(cityName: String, countryName: String) {
        try {
            val coordinate =
                getCoordinateByCityAndCountryUseCase.getCoordinateByCityAndCountry(cityName = cityName, country = countryName)
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

