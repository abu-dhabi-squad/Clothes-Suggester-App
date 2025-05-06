package logic.clothesSuggester

import logic.exception.EmptyClotheListException
import logic.exception.EmptyTemperatureListException
import logic.model.Cloth
import logic.model.ClothType
import logic.model.Weather
import logic.repository.ClothesRepository

class SuggestClothesUseCase(private val clothesRepository: ClothesRepository) {
    fun getSuggestedClothes(weather: Weather): List<Cloth> {
        val averageTemperature =
            weather.hourlyTemperatures.takeIf { it.isNotEmpty() }?.map { it.temperature }?.average()
                ?: throw EmptyTemperatureListException()
        val clotheType = getClotheType(averageTemperature)
        return clothesRepository.getClothesByType(clotheType).takeIf { it.isNotEmpty() } ?:throw EmptyClotheListException()
    }

    private fun getClotheType(averageTemperature: Double): ClothType {
        return when {
            averageTemperature < 0 -> ClothType.VERY_HEAVY
            averageTemperature in 0.0..<5.0 -> ClothType.HEAVY
            averageTemperature in 5.0..<15.0 -> ClothType.MEDIUM
            averageTemperature in 15.0..<22.0 -> ClothType.LIGHT
            else -> ClothType.VERY_LIGHT
        }
    }
}