package data.location.model


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class DtoLocation(
    @SerialName("generationtime_ms")
    val generationTimeMs: Double,
    @SerialName("results")
    val locations: List<LocationResult>
)