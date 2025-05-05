package logic.model

data class Weather(
    val hourlyTemperatures: List<HourlyTemperature>,
    val weatherCondition: WeatherCondition?,
)