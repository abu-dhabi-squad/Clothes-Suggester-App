package data.clothes.repository

import com.google.common.truth.Truth.assertThat
import data.clothes.datasource.ClothesDataSource
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import logic.model.Cloth
import logic.model.ClothType
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import java.util.*

class ClothesRepositoryImplTest{
    private lateinit var dataSource: ClothesDataSource
    private lateinit var repository: ClothesRepositoryImpl

    @BeforeEach
    fun setUp() {
        dataSource = mockk()
        repository = ClothesRepositoryImpl(dataSource)
    }

    @Test
    fun `getClothesByType should return clothes from dataSource`() {
        // Given
        val type = ClothType.LIGHT
        val expectedClothes = listOf(
            Cloth(UUID.randomUUID(), "Light Shirt", type),
            Cloth(UUID.randomUUID(), "Light Pants", type)
        )
        every { dataSource.getClothesByType(type) } returns expectedClothes

        // When
        val result = repository.getClothesByType(type)

        // Then
        verify { dataSource.getClothesByType(type) }
        assertThat(result).isEqualTo(expectedClothes)
    }
}