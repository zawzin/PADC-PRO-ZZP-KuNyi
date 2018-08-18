package xyz.zzp.kunyi.mvp.views

import xyz.zzp.kunyi.data.vos.JobVO

interface JobsView : BaseView {
    fun lunchDetail (postId : Long)

    fun lunchApplyPost()

    fun tappedInterest(job: JobVO)

}