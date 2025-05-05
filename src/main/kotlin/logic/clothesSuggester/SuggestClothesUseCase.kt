package logic.clothesSuggester

import logic.model.Cloth
import logic.model.Weather
import logic.repository.ClothesRepository

class SuggestClothesUseCase(private val clothesRepository: ClothesRepository) {
    suspend fun getSuggestedClothes(weather: Weather): List<Cloth> {
        TODO()
    }
}