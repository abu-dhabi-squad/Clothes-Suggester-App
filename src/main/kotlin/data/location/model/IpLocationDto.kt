package data.location.model


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class IpLocationDto(
    @SerialName("as")
    var asX: String?,
    @SerialName("city")
    var city: String?,
    @SerialName("country")
    var country: String?,
    @SerialName("countryCode")
    var countryCode: String?,
    @SerialName("isp")
    var isp: String?,
    @SerialName("lat")
    var lat: Double?,
    @SerialName("lon")
    var lon: Double?,
    @SerialName("org")
    var org: String?,
    @SerialName("query")
    var query: String?,
    @SerialName("region")
    var region: String?,
    @SerialName("regionName")
    var regionName: String?,
    @SerialName("status")
    var status: String?,
    @SerialName("timezone")
    var timezone: String?,
    @SerialName("zip")
    var zip: String?,
)