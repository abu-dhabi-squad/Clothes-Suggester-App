import di.repositoryModule
import di.uiModule
import di.useCaseModule
import ui.ClothesSuggesterApp
import org.koin.core.context.startKoin
import org.koin.mp.KoinPlatform.getKoin

fun main() {
    startKoin {
        modules(repositoryModule, useCaseModule, uiModule)
    }
    getKoin().get<ClothesSuggesterApp>().launchUi()
}