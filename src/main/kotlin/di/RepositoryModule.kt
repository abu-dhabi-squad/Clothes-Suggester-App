package di

import data.clothes.datasource.ClothesMemoryDataSource
import data.clothes.datasource.ClothesMemoryDataSourceImp
import data.clothes.repository.ClothesRepositoryImpl
import data.location.datasource.LocationRemoteDataSourceImpl
import data.location.datasource.LocationRemoteDataSource
import data.location.mapper.CityLocationMapper
import data.location.mapper.IpLocationMapper
import data.location.repository.LocationRepositoryImpl
import data.weather.datasource.WeatherRemoteDataSourceImpl
import data.weather.datasource.WeatherRemoteDataSource
import data.weather.mapper.WeatherMapper
import data.weather.repository.WeatherRepositoryImpl
import io.ktor.client.HttpClient
import io.ktor.client.engine.cio.*
import kotlinx.serialization.json.Json
import logic.repository.ClothesRepository
import logic.repository.LocationRepository
import logic.repository.WeatherRepository
import org.koin.dsl.module

val repositoryModule = module {
    // repositories
    single<LocationRepository> { LocationRepositoryImpl(get(), get(),get()) }
    single<WeatherRepository> { WeatherRepositoryImpl(get(), get()) }
    single <ClothesRepository>{ClothesRepositoryImpl(get())  }
    // json serializable
    single { Json{ignoreUnknownKeys = true} }
    // datasource
    single<WeatherRemoteDataSource> { WeatherRemoteDataSourceImpl(get(), get()) }
    single<LocationRemoteDataSource> { LocationRemoteDataSourceImpl(get(), get()) }
    single<ClothesMemoryDataSource> {  ClothesMemoryDataSourceImp()}
    single<HttpClient> { HttpClient(CIO) }
    // mapper
    single { CityLocationMapper() }
    single { WeatherMapper() }
    single { IpLocationMapper() }



}