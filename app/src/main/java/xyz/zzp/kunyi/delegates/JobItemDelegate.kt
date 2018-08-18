package xyz.zzp.kunyi.delegates

import xyz.zzp.kunyi.data.vos.JobVO

interface JobItemDelegate {
    fun onTapJob(job : JobVO?)

    fun onTapApply()

    fun onTapInterest(job: JobVO?)
}