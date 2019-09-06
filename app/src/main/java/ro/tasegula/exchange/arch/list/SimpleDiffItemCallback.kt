package ro.tasegula.exchange.arch.list

import androidx.recyclerview.widget.DiffUtil

/**
 * A [DiffUtil.ItemCallback] implementation that takes care of the comparison of [ItemViewModel]
 * items, forwarding the calls to [ItemViewModel.isSameItemAs] and [ItemViewModel.isSameContentAs].
 */
class SimpleDiffItemCallback<IVM : ItemViewModel> : DiffUtil.ItemCallback<IVM>() {
    override fun areItemsTheSame(oldItem: IVM, newItem: IVM) = oldItem.isSameItemAs(newItem)
    override fun areContentsTheSame(oldItem: IVM, newItem: IVM) = oldItem.isSameContentAs(newItem)
}
