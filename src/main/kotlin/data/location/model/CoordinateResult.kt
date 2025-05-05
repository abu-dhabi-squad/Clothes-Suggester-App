package data.location.model


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CoordinateResult(
    @SerialName("admin1")
    val admin1: String,
    @SerialName("admin1_id")
    val admin1Id: Int,
    @SerialName("country")
    val country: String,
    @SerialName("country_code")
    val countryCode: String,
    @SerialName("country_id")
    val countryId: Int,
    @SerialName("elevation")
    val elevation: Double,
    @SerialName("feature_code")
    val featureCode: String,
    @SerialName("id")
    val id: Int,
    @SerialName("latitude")
    val latitude: Double,
    @SerialName("longitude")
    val longitude: Double,
    @SerialName("name")
    val name: String,
    @SerialName("population")
    val population: Int,
    @SerialName("timezone")
    val timezone: String
)