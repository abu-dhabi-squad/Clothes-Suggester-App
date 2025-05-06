package logic.repository

import logic.model.Cloth
import logic.model.ClothType

interface ClothesRepository {
    fun getClothByType(clothType: ClothType): List<Cloth>
}