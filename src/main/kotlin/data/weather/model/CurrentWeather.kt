package data.weather.model


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CurrentWeather(
    @SerialName("interval")
    val interval: Int?,
    @SerialName("temperature_2m")
    val temperature2m: Double?,
    @SerialName("time")
    val time: String?,
    @SerialName("weather_code")
    val weatherCode: Int?
)