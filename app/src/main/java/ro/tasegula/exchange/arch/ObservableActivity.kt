package ro.tasegula.exchange.arch

import android.os.Bundle
import androidx.annotation.IdRes
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding

abstract class ObservableActivity<VM : ObservableViewModel, B : ViewDataBinding> : AppCompatActivity() {

    @LayoutRes
    private var bindingLayoutId: Int = 0
    @IdRes
    private var viewModelBindingVariableId: Int = 0

    protected lateinit var viewModel: VM
    protected lateinit var binding: B


    protected fun setup(@LayoutRes bindingLayoutId: Int,
                        @IdRes viewModelBindingVariableId: Int,
                        viewModelCreator: () -> VM) {
        this.bindingLayoutId = bindingLayoutId
        this.viewModelBindingVariableId = viewModelBindingVariableId

        viewModel = viewModelCreator.invoke()

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val dataBinding: B = DataBindingUtil.setContentView(this, bindingLayoutId)

        dataBinding.setVariable(viewModelBindingVariableId, viewModel)
    }
}