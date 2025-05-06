package ui

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import logic.usecases.clothesSuggester.GetClothingSuggestionUseCase
import logic.model.Cloth
import logic.usecases.location.GetCoordinateByCityAndCountryUseCase
import logic.usecases.weather.GetDailyWeatherByCoordinateUseCase
import ui.io.InputReader
import ui.io.Printer

class ClothesSuggesterByCityNameUI(
    private val printer: Printer,
    private val inputReader: InputReader,
    private val getCoordinateByCityAndCountryUseCase: GetCoordinateByCityAndCountryUseCase,
    private val getDailyWeatherByCoordinateUseCase: GetDailyWeatherByCoordinateUseCase,
    private val getSuggestedClothes: GetClothingSuggestionUseCase
) : UiLauncher {

    private var suggestedClothes: List<Cloth>? = null
    private lateinit var customCoroutineScope: CoroutineScope
    override fun launchUi() {
        val cityName = promptNonEmptyString("Enter city name: ")
        val countryName = promptNonEmptyString("Enter country name: ")
        customCoroutineScope = CoroutineScope(Dispatchers.Default)
        loading()
        runBlocking(Dispatchers.Default) {
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
                    delay(200)
                }
                printer.display("\r")
            }
        }
    }

    private suspend fun onGetSuggestingClothesExecute(cityName: String, countryName: String) {
        try {
            val coordinate =
                getCoordinateByCityAndCountryUseCase.getCoordinateByCityAndCountry(
                    cityName = cityName,
                    country = countryName
                )
            val weather = getDailyWeatherByCoordinateUseCase.getDailyWeather(coordinate)
            suggestedClothes = getSuggestedClothes.getSuggestedClothes(weather)
        } catch (exception: Exception) {
            printer.display("\r")
            printer.displayLn(exception.message)
        }
    }

    private fun displaySuggestedClothes(clothes: List<Cloth>) {
        printer.displayLn("\r\n👕 Suggested Clothes:\n")
        clothes.forEachIndexed { index, cloth ->
            printer.displayLn(
                """
                ${index + 1}. ✨ ${cloth.name.uppercase()} ✨
                """.trimIndent()
            )
        }
    }

}

