package logic.model

import java.util.UUID

data class Cloth(
    val id: UUID = UUID.randomUUID(),
    val name: String,
    val type: ClothType,
    val isFavourite: Boolean = false,
)
