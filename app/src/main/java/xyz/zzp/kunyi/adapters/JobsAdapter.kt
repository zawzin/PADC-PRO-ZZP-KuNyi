package xyz.zzp.kunyi.adapters

import android.content.Context
import android.view.ViewGroup
import xyz.zzp.hckotlin.adapters.BaseRecyclerAdapter
import xyz.zzp.kunyi.R
import xyz.zzp.kunyi.data.vos.JobVO
import xyz.zzp.kunyi.delegates.JobItemDelegate
import xyz.zzp.kunyi.viewholders.JobsViewHolder

class JobsAdapter (context : Context,
                   private val mDelegate: JobItemDelegate) : BaseRecyclerAdapter<JobsViewHolder,JobVO>(context){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): JobsViewHolder {
        val jobItem = mLayoutInflator.inflate(R.layout.item_job,parent,false)
        return JobsViewHolder(jobItem,mDelegate)
    }
}