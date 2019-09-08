package ro.tasegula.exchange.utils

import android.view.View
import androidx.databinding.BindingAdapter

@BindingAdapter("onFocusChangeListener")
fun onFocusChangeListener(view: View, listener: View.OnFocusChangeListener) {
    view.onFocusChangeListener = listener
}
