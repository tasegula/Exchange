package ro.tasegula.exchange.ui

import android.os.Bundle
import dagger.android.AndroidInjection
import ro.tasegula.exchange.BR
import ro.tasegula.exchange.R
import ro.tasegula.exchange.arch.ObservableActivity
import ro.tasegula.exchange.databinding.ExchangeScreenBinding
import javax.inject.Inject
import javax.inject.Provider

class ExchangeActivity : ObservableActivity<ExchangeViewModel, ExchangeScreenBinding>() {

    @Inject
    lateinit var viewModelProvider: Provider<ExchangeViewModel>

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        setup(R.layout.exchange_screen, BR.viewModel, viewModelProvider::get)
        super.onCreate(savedInstanceState)
    }
}
