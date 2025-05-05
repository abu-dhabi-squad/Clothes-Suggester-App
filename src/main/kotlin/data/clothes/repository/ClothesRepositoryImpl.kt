package data.clothes.repository

import data.clothes.datasource.ClothesDataSource
import logic.model.Cloth
import logic.model.ClothType
import logic.repository.ClothesRepository

class ClothesRepositoryImpl(
    private val dataSource: ClothesDataSource,
) : ClothesRepository {
    override fun getClothesByType(clothType: ClothType): List<Cloth> {
        return dataSource.getClothesByType(clothType)
    }
}