package xyz.zzp.kunyi.mvp.presenters

import android.arch.lifecycle.MutableLiveData
import xyz.zzp.kunyi.data.models.JobsModel
import xyz.zzp.kunyi.data.vos.JobVO
import xyz.zzp.kunyi.delegates.JobItemDelegate
import xyz.zzp.kunyi.mvp.views.JobsView

class JobPresenter : BasePresenter<JobsView>(), JobItemDelegate {
    override fun onTapInterest(job: JobVO?) {
        if (job != null) {
            mView!!.tappedInterest(job)
        }
    }

    override fun onTapApply() {
        mView!!.lunchApplyPost()
    }

    override fun onTapJob(job: JobVO?) {
        if (job != null) {
            mView!!.lunchDetail(job.jobPostId!!)
        }
    }

    fun onRefreshJobList(){
        JobsModel.getINSTANCE().loadJobs()
    }

    private lateinit var mJobsListLD : MutableLiveData<List<JobVO>>

    override fun initPresenter(mView: JobsView) {
        super.initPresenter(mView)
        mJobsListLD = MutableLiveData()
        JobsModel.getINSTANCE().loadJobs()
    }

}