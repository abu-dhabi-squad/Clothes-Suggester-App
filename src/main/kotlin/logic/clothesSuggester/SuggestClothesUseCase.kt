package logic.clothesSuggester

import logic.model.Cloth
import logic.model.ClothType
import logic.model.Weather
import logic.model.WeatherCondition
import logic.repository.ClothesRepository

class SuggestClothesUseCase(private val clothesRepository: ClothesRepository) {
    suspend fun getSuggestedClothes(weather: Weather): List<Cloth> {
        TODO()
    }
}