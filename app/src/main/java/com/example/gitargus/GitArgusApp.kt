package com.example.gitargus

import android.app.Application
import com.example.gitargus.di.DaggerViewModelFactoryComponent
import com.example.gitargus.di.ViewModelFactoryComponent

class GitArgusApp : Application() {

    companion object {
        var viewModelFactoryComponent: ViewModelFactoryComponent =
            DaggerViewModelFactoryComponent.builder().build()
    }
}