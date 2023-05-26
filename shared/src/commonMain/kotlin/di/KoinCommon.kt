package di

import org.koin.core.KoinApplication
import org.koin.core.context.startKoin
import org.koin.core.module.Module
import org.koin.dsl.module
import remote.QuestionsRepository
import viewmodel.NewQuestionViewModel
import viewmodel.QuestionsViewModel
import viewmodel.UsersViewModel

object Modules {
    val repositories = module {
        factory { QuestionsRepository() }
    }
    val viewModels = module {
        factory { QuestionsViewModel(get()) }
        factory { UsersViewModel(get()) }
        // TODO shouldn't be single. Where to store 'new question' state then?
        single { NewQuestionViewModel(get()) }
    }
}

fun initKoin(
    appModule: Module = module { },
    repositoriesModule: Module = Modules.repositories,
    viewModelsModule: Module = Modules.viewModels,
): KoinApplication = startKoin {
    modules(
        appModule,
        repositoriesModule,
        viewModelsModule
    )
}