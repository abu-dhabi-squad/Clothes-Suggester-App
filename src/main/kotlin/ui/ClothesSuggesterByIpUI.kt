package ui

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import logic.model.Cloth
import logic.usecases.clothessuggester.SuggestClothesUseCase
import logic.usecases.location.GetCoordinateByIpUseCase
import logic.usecases.weather.GetDailyWeatherByCoordinateUseCase

import ui.io.Printer

class ClothesSuggesterByIpUI(
    private val printer: Printer,
    private val getCoordinateByIpUseCase: GetCoordinateByIpUseCase,
    private val getDailyWeatherByCoordinateUseCase: GetDailyWeatherByCoordinateUseCase,
    private val getSuggestedClothes: SuggestClothesUseCase
) : UiLauncher {

    private var suggestedClothes: List<Cloth>? = null
    private lateinit var customCoroutineScope: CoroutineScope
    override fun launchUi() {
        customCoroutineScope = CoroutineScope(Dispatchers.Default)
        loading()
        runBlocking(Dispatchers.Default) {
            onGetSuggestingClothesExecute()
            suggestedClothes?.let {
                displaySuggestedClothes(it)
            }
            customCoroutineScope.cancel()
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

    private suspend fun onGetSuggestingClothesExecute() {
        try {
            val coordinate = getCoordinateByIpUseCase.getCoordinateByIp()
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

