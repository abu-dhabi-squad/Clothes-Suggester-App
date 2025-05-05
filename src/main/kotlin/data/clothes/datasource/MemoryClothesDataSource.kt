package data.clothes.datasource

import logic.model.Cloth
import logic.model.ClothType

class MemoryClothesDataSource : ClothesDataSource {
    private val clothes = mutableListOf<Cloth>()

    override fun getClothesByType(clothType: ClothType): List<Cloth> {
        return clothes.filter { it.type == clothType }
    }
}