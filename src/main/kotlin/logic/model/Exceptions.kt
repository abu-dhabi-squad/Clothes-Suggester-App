package logic.model

open class DomainExceptions(message: String) : Exception(message)

class NoLocationFoundException : DomainExceptions("No location found")

class InvalidCityNameException : DomainExceptions("Invalid city name")

class InvalidCountryNameException : DomainExceptions("Invalid country name")

class NoHourlyTemperatureFound : DomainExceptions("no hourly temperature found")

class UnKnownWeatherConditionException : DomainExceptions("unknown weather code found")