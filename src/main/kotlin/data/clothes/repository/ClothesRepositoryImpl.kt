package data.clothes.repository

import data.clothes.datasource.ClothesMemoryDataSource
import logic.model.Cloth
import logic.model.ClothType
import logic.repository.ClothesRepository

class ClothesRepositoryImpl(
    private val dataSource: ClothesMemoryDataSource,
) : ClothesRepository {
    override fun getClothByType(clothType: ClothType): List<Cloth> {
        return dataSource.getClothesByType(clothType)
    }
}