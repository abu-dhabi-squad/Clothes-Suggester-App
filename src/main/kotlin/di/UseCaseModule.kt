package di
import logic.clothesSuggester.SuggestClothesUseCase
import org.koin.dsl.module
import logic.usecases.location.GetCoordinateByCityAndCountryUseCase
import logic.usecases.weather.GetDailyWeatherByCoordinateUseCase


val useCaseModule = module {
    single { SuggestClothesUseCase() }
    single { GetCoordinateByCityAndCountryUseCase(get()) }
    single { GetDailyWeatherByCoordinateUseCase(get()) }
}

