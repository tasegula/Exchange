package ro.tasegula.exchange.ui

import android.content.Context
import android.os.Bundle
import android.view.MotionEvent
import android.view.View
import android.view.inputmethod.InputMethodManager
import dagger.android.AndroidInjection
import ro.tasegula.exchange.BR
import ro.tasegula.exchange.R
import ro.tasegula.exchange.core.arch.ObservableActivity
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

    override fun dispatchTouchEvent(ev: MotionEvent?): Boolean {
        currentFocus?.let {
            it.hideKeyboard()
        }
        return super.dispatchTouchEvent(ev)
    }

    private fun View.hideKeyboard() {
        clearFocus()
        val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(windowToken, 0)
    }
}

