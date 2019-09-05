package ro.tasegula.exchange

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import dagger.android.AndroidInjection
import ro.tasegula.exchange.databinding.ExchangeScreenBinding
import javax.inject.Inject
import javax.inject.Provider

class ExchangeActivity : AppCompatActivity() {

    @Inject
    protected lateinit var viewModelProvider: Provider<ExchangeViewModel>

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.exchange_screen)

        // Inflate view and obtain an instance of the binding class.
        val binding: ExchangeScreenBinding = DataBindingUtil.setContentView(this, R.layout.exchange_screen)

        // Assign the component to a property in the binding class.
        binding.viewModel = viewModelProvider.get()
    }
}

