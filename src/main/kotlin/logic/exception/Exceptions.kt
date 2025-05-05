package logic.exception

open class DomainExceptions(message: String) : Exception(message)

class NoHourlyTemperatureFound : DomainExceptions("no hourly temperature found")

class NoWeatherConditionFound : DomainExceptions("no weather condition found")