package di

import main.kotlin.ui.ClothesSuggesterApp
import main.kotlin.ui.ClothesSuggesterByCityNameUI
import main.kotlin.ui.ClothesSuggesterByIpUI
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
    single { ClothesSuggesterByIpUI(get(),get(),get(),get()) }
    single<List<UIFeature>>(named("features")) {
        listOf(
            UIFeature(
                label = "Get Outfit by City",
                id = 1,
                uiLauncher = get<ClothesSuggesterByCityNameUI>()
            ),
            UIFeature(
                label = "Get Outfit for Your Location",
                id = 2,
                uiLauncher = get<ClothesSuggesterByIpUI>()
            )
        )
    }
    single<ClothesSuggesterApp> { ClothesSuggesterApp(get(named("features")),get(),get()) }
}