package di

import data.location.datasource.ApiLocationDataSource
import data.location.datasource.LocationDataSource
import data.location.mapper.CoordinateMapper
import data.location.repository.LocationRepositoryImpl
import data.weather.datasource.ApiWeatherDataSource
import data.weather.datasource.WeatherDataSource
import data.weather.mapper.WeatherMapper
import data.weather.repository.WeatherRepositoryImpl
import io.ktor.client.HttpClient
import logic.repository.LocationRepository
import logic.repository.WeatherRepository
import org.koin.dsl.module

val repositoryModule = module {
    // repositories
    single<LocationRepository> { LocationRepositoryImpl(get(), get()) }
    single<WeatherRepository> { WeatherRepositoryImpl(get(), get()) }
    // datasource
    single<WeatherDataSource> { ApiWeatherDataSource(get()) }
    single<LocationDataSource> { ApiLocationDataSource(get()) }
    single<HttpClient> { HttpClient() }
    // mapper
    single { CoordinateMapper() }
    single { WeatherMapper() }



}