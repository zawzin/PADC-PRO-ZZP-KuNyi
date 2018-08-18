package xyz.zzp.kunyi.mvp.presenters


import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import xyz.zzp.kunyi.mvp.views.BaseView


open class BasePresenter<T : BaseView> : ViewModel() {

    protected var mView: T? = null

    protected lateinit var mErrorLD: MutableLiveData<String>

    open fun initPresenter(mView: T) {
        this.mView = mView
        mErrorLD = MutableLiveData()
    }

    open fun getmErrorLD(): MutableLiveData<String> {
        return mErrorLD
    }

}
