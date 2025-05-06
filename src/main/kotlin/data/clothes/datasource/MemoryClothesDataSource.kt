package data.clothes.datasource

import logic.model.Cloth
import logic.model.ClothType

interface MemoryClothesDataSource {
    fun getClothesByType(clothType: ClothType) : List<Cloth>
}