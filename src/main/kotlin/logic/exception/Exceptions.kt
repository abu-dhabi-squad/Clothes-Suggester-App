package logic.exception

open class AppException(message: String) : Exception(message)

open class DomainExceptions(message: String) : AppException(message)

class NoLocationFoundException : DomainExceptions("No location found")

class InvalidCityNameException : DomainExceptions("Invalid city name")

class InvalidCountryNameException : DomainExceptions("Invalid country name")

class NoHourlyTemperatureFound : DomainExceptions("no hourly temperature found")

class UnKnownWeatherConditionException : DomainExceptions("unknown weather code found")

class MissingTemperaturesException : DomainExceptions("No temperatures found in this list")

class NoMatchingClothesFoundException : DomainExceptions("No Clothes found in this list")

open class DataException(message: String) : AppException(message)

class DataIsNullException : DataException("data not found")