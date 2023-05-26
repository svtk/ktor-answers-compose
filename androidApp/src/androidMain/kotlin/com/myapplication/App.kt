package com.myapplication

import android.app.Application
import androidx.lifecycle.viewmodel.viewModelFactory
import di.initKoin
import org.koin.android.ext.android.get
import org.koin.dsl.module
import viewmodel.QuestionsViewModel

class App: Application() {
    override fun onCreate() {
        super.onCreate()
        // TODO use viewModelFactory
        initKoin(
//            viewModelsModule = module {
//                viewModelFactory {
//                    ...
//                }
//            }
        )
    }
}