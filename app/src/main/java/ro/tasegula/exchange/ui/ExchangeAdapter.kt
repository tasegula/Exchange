package ro.tasegula.exchange.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import ro.tasegula.exchange.BR
import ro.tasegula.exchange.arch.list.SimpleAdapter
import ro.tasegula.exchange.databinding.ExchangeItemBinding

class ExchangeAdapter : SimpleAdapter<RateItemViewModel, ExchangeItemBinding>() {
    override val itemViewModelBindingVarId: Int
        get() = BR.viewModel

    override fun inflateView(inflater: LayoutInflater, parent: ViewGroup, viewType: Int): ExchangeItemBinding =
            ExchangeItemBinding.inflate(inflater, parent, false)

}