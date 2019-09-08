package ro.tasegula.exchange.core.arch.list

import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView

class SimpleViewModelItemViewHolder<in VM : ItemViewModel, out DB : ViewDataBinding>
constructor(private val binding: DB,
            private val viewModelBindingVarId: Int) : RecyclerView.ViewHolder(binding.root) {

    fun bind(item: VM) {
        this.binding.setVariable(viewModelBindingVarId, item)
        this.binding.executePendingBindings()
    }
}
