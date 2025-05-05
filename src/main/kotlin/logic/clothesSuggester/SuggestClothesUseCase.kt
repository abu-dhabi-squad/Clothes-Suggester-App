package logic.clothesSuggester

import logic.model.Cloth
import logic.model.ClothType
import logic.model.Weather
import logic.model.WeatherCondition
import logic.repository.ClothesRepository

class SuggestClothesUseCase(private val clothesRepository: ClothesRepository) {
    suspend fun getSuggestedClothes(weather: Weather): List<Cloth> {
        val avgTemp = weather.hourlyTemperatures.map { it.temperature }.averageOrNull()

        val tempBasedType = when {
            avgTemp == null -> emptyList()
            avgTemp < 5 -> listOf(ClothType.HEAVY)
            avgTemp in 5.0..15.0 -> listOf(ClothType.MEDIUM)
            avgTemp in 15.1..25.0 -> listOf(ClothType.LIGHT)
            else -> listOf(ClothType.VERY_LIGHT)
        }

        val types = tempBasedType.distinct()
        return types.flatMap { clothesRepository.getClothesByType(it) }
    }

    private fun List<Float>.averageOrNull(): Double? =
        if (isEmpty()) null else this.average()
}