package logic.exception

open class DomainExceptions(message: String) : Exception(message)

class NoHourlyTemperatureFound : DomainExceptions("no hourly temperature found")

open class DataException(message: String): Exception(message)

class UnKownWeatherConditionException: DataException("unkown weather code found")