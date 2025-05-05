package helper

import data.location.model.CoordinateDto
import data.location.model.CoordinateResult

fun createDtoLocation(
    generationTimeMs: Double = 0.0,
    latitude: Double = 0.0,
    longitude: Double = 0.0,
    citiesCoordinates: List<CoordinateResult>? = null,
): CoordinateDto {
    return CoordinateDto(
        generationTimeMs = generationTimeMs,
        citiesCoordinates = citiesCoordinates ?: listOf(CoordinateResult(
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
    )
}