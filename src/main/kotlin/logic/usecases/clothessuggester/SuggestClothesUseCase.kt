package logic.usecases.clothessuggester

import logic.model.Cloth
import logic.model.ClothType
import logic.model.Weather
import logic.repository.ClothesRepository

class SuggestClothesUseCase(private val clothesRepository: ClothesRepository) {
     fun getSuggestedClothes(weather: Weather): List<Cloth> {
        val avgTemp = weather.hourlyTemperatures.map { it.temperature }.average()
        val tempBasedType = when {
            avgTemp < 5 -> ClothType.HEAVY
            avgTemp in 5.0 ..< 15.0 -> ClothType.MEDIUM
            avgTemp in 15.0 ..< 25.0 -> ClothType.LIGHT
            else ->ClothType.VERY_LIGHT
        }
        return clothesRepository.getClothByType(tempBasedType)
    }
}