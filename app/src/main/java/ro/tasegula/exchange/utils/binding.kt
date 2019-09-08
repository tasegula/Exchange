package ro.tasegula.exchange.utils

import android.graphics.drawable.Drawable
import android.view.View
import android.widget.ImageView
import androidx.annotation.DrawableRes
import androidx.databinding.BindingAdapter

@BindingAdapter("onFocusChangeListener")
fun onFocusChangeListener(view: View, listener: View.OnFocusChangeListener) {
    view.onFocusChangeListener = listener
}

// region ImageView
@BindingAdapter("srcCompat")
fun bindVectorResId(imageView: ImageView, @DrawableRes resId: Int) {
    if (resId == 0) return
    imageView.setImageResource(resId)
}

@BindingAdapter("srcCompat")
fun bindVectorDrawable(imageView: ImageView, drawableRes: Drawable?) {
    imageView.setImageDrawable(drawableRes)
}
// endregion