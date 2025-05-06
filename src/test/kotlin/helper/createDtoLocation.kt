package helper

import data.location.model.CityLocationDto
import data.location.model.CityLocationDetailsDto

fun createDtoLocation(
    generationTimeMs: Double? = 0.0,
    latitude: Double? = 0.0,
    longitude: Double? = 0.0,
    citiesCoordinates: List<CityLocationDetailsDto>? = listOf(CityLocationDetailsDto(
        latitude = latitude,
        longitude = longitude,
        admin1 = "",
        country = "",
        countryCode = "",
        admin1Id = 0,
        countryId = 0,
        elevation = 0.0,
        featureCode = "",
        id = 0,
        name = "",
        population = 0,
        timezone = "",
    )),
): CityLocationDto {
    return CityLocationDto(
        generationTimeMs = generationTimeMs,
        citiesCoordinates = citiesCoordinates
    )
}