package di

import main.kotlin.ui.ClothesSuggesterApp
import main.kotlin.ui.ClothesSuggesterByCityNameUI
import main.kotlin.ui.UIFeature
import org.koin.core.qualifier.named
import org.koin.dsl.module
import presentation.ui_io.ConsolePrinter
import presentation.ui_io.ConsoleReader
import presentation.ui_io.InputReader
import presentation.ui_io.Printer

val uiModule = module {

    // I/O dependencies
    single<Printer> { ConsolePrinter() }
    single<InputReader> { ConsoleReader() }

    single { ClothesSuggesterByCityNameUI(get(), get(), get(), get(), get()) }
    single { ClothesSuggesterApp(get(), get(), get()) }

    single<List<UIFeature>>(named("features")) {

        listOf(
            UIFeature(
                label = "Suggest Clothes", id = 1,
                uiLauncher = get<ClothesSuggesterByCityNameUI>()
            )
        )
    }
    single<ClothesSuggesterApp> { ClothesSuggesterApp(get(named("features")),get(),get()) }
}