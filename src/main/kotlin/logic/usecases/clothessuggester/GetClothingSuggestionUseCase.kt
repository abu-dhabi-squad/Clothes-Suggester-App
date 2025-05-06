package logic.usecases.clothessuggester

import logic.exception.NoMatchingClothesFoundException
import logic.exception.MissingTemperaturesException
import logic.model.Cloth
import logic.model.ClothType
import logic.model.Weather
import logic.repository.ClothesRepository

class GetClothingSuggestionUseCase(private val clothesRepository: ClothesRepository) {
    fun getSuggestedClothes(weather: Weather): List<Cloth> {
        val averageTemprature = calculateAverageTemperature(weather)
        val clothType = getClothType(averageTemprature)
        val clothes = clothesRepository.getClothByType(clothType)

        return clothes.takeIf { it.isNotEmpty() }
            ?: throw NoMatchingClothesFoundException()
    }

    private fun calculateAverageTemperature(weather: Weather): Double {
        val temperatures = weather.hourlyTemperatures.map { it.temperature }
        if (temperatures.isEmpty()) throw MissingTemperaturesException()
        return temperatures.average()
    }

    private fun getClothType(temp: Double): ClothType = when {
        temp < 0 -> ClothType.VERY_HEAVY
        temp < 5 -> ClothType.HEAVY
        temp < 15 -> ClothType.MEDIUM
        temp < 22 -> ClothType.LIGHT
        else -> ClothType.VERY_LIGHT
    }
}