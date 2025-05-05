package logic.repository

import logic.model.Cloth
import logic.model.ClothType

interface ClothesRepository {
    suspend fun getClothesByType(clothType: ClothType): List<Cloth>
}