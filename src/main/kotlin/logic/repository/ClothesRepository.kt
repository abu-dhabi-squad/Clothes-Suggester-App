package logic.repository

import logic.model.Cloth
import logic.model.ClothType

interface ClothesRepository {
    fun getClothesByType(clothType: ClothType): List<Cloth>
}