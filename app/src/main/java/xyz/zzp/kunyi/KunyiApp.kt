package xyz.zzp.kunyi

import android.app.Application
import xyz.zzp.kunyi.data.models.JobsModel
import xyz.zzp.kunyi.data.models.UserModel

class KunyiApp : Application() {

    companion object {
        val TAG: String = "KuNyiApp"
    }

    override fun onCreate() {
        super.onCreate()
        JobsModel.initJobsModel(this)
        UserModel.initUserModel(this)
    }
}