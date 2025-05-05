package helper

import data.location.model.DtoLocation
import data.location.model.LocationResult

fun createDtoLocation(
    generationTimeMs: Double = 0.0,
    latitude: Double = 0.0,
    longitude: Double = 0.0,
): DtoLocation {
    return DtoLocation(
        generationTimeMs = generationTimeMs,
        locations = listOf(LocationResult(
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