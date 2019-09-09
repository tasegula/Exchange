package ro.tasegula.exchange.core.arch

import android.os.Bundle
import androidx.annotation.IdRes
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable

abstract class ObservableActivity<VM : ObservableViewModel, B : ViewDataBinding> : AppCompatActivity() {

    private var compositeDisposable: CompositeDisposable? = null
    fun Disposable.disposeOnStop() {
        if (compositeDisposable == null) {
            synchronized(ObservableActivity::class) {
                if (compositeDisposable == null)
                    compositeDisposable = CompositeDisposable()
            }
        }

        compositeDisposable?.add(this)
    }

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
        binding = DataBindingUtil.setContentView(this, bindingLayoutId)
        binding.setVariable(viewModelBindingVariableId, viewModel)
    }
}