package ro.tasegula.exchange.arch.list

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView

abstract class SimpleAdapter<IVM : ItemViewModel, B : ViewDataBinding> :
    ListAdapter<IVM, RecyclerView.ViewHolder>(SimpleDiffItemCallback()) {

    /**
     * The data binding variable id on which each item will be bound on the [ViewDataBinding]
     * created in [inflateView].
     */
    abstract val itemViewModelBindingVarId: Int

    /**
     * Called when a new view needs to be inflated to be attached to the View Holder for the
     * provided position.
     *
     * If different items should have different layouts, the [getItemViewType] method should be
     * overriden and the `viewType` parameter should be used to create the proper layout.
     */
    abstract fun inflateView(inflater: LayoutInflater, parent: ViewGroup, viewType: Int): B

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return SimpleViewModelItemViewHolder<IVM, B>(
                inflateView(LayoutInflater.from(parent.context), parent, viewType),
                itemViewModelBindingVarId
        )
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as SimpleViewModelItemViewHolder<IVM, B>).bind(getItem(position))
    }

}
