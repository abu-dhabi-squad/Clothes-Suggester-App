package di

import ui.ClothesSuggesterApp
import ui.ClothesSuggesterByCityNameUI
import ui.ClothesSuggesterByIpUI
import ui.UIFeature
import org.koin.core.qualifier.named
import org.koin.dsl.module
import ui.io.ConsolePrinter
import ui.io.ConsoleReader
import ui.io.InputReader
import ui.io.Printer

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