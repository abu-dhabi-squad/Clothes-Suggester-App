package di

import logic.usecases.clothessuggester.GetClothingSuggestionUseCase
import org.koin.dsl.module
import logic.usecases.clothessuggester.SuggestClothesUseCase
import logic.usecases.location.GetCoordinateByCityAndCountryUseCase
import logic.usecases.location.GetCoordinateByIpUseCase
import logic.usecases.weather.GetDailyWeatherByCoordinateUseCase

val useCaseModule = module {
    single { SuggestClothesUseCase(get()) }
    single { GetCoordinateByCityAndCountryUseCase(get()) }
    single { GetDailyWeatherByCoordinateUseCase(get()) }
    single { GetCoordinateByIpUseCase(get()) }
    single { GetClothingSuggestionUseCase(get()) }
}

