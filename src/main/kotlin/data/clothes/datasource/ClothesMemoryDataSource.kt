package data.clothes.datasource

import logic.model.Cloth
import logic.model.ClothType

interface ClothesMemoryDataSource {
    fun getClothesByType(clothType: ClothType) : List<Cloth>


}