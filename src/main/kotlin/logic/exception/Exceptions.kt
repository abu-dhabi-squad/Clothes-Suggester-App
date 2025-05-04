package logic.exception

open class DomainExceptions(message: String): Exception(message)

class NoLocationFoundException: DomainExceptions("No location found")

class InvalidCityNameException: DomainExceptions("Invalid city name")

class InvalidCountryNameException: DomainExceptions("Invalid country name")