package ro.tasegula.exchange.arch.list

interface ItemViewModel {
    /**
     * Called to check whether the passed object represents the same item as this ViewModel.
     *
     * For example, if your items have unique ids, this method should check their id equality.
     *
     * @see [DiffUtil.ItemCallback.areItemsTheSame]
     * @return `true` if the two items represent the same object or `false` if they are different.
     */
    fun isSameItemAs(other: ItemViewModel): Boolean

    /**
     * Called to check whether whether the passed object has the same data as this ViewModel. It
     * should return whether the items' visual representations are the same.
     *
     * This information is used to detect if the contents of an item have changed. This method is
     * used to check equality instead of [Object.equals] so consumers can change its behavior
     * depending on the UI.
     *
     * This method is called only if [isSameItemAs] returns `true` for these items.
     *
     * @see [DiffUtil.ItemCallback.areContentsTheSame]
     * @return `true` if the contents of the items are the same or `false` if they are different.
     */
    fun isSameContentAs(other: ItemViewModel): Boolean

}
