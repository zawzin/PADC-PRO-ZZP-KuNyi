package xyz.zzp.kunyi.viewholders

import android.annotation.SuppressLint
import android.view.View
import com.padcmyanmar.mmnews.kotlin.views.holders.BaseViewHolder
import kotlinx.android.synthetic.main.item_job.*
import xyz.zzp.kunyi.data.vos.JobVO
import kotlinx.android.synthetic.main.item_job.view.*
import xyz.zzp.kunyi.R
import xyz.zzp.kunyi.R.id.btnInterest
import xyz.zzp.kunyi.data.models.UserModel
import xyz.zzp.kunyi.delegates.JobItemDelegate

class JobsViewHolder(itemView : View,
                     private val mDelegate: JobItemDelegate) : BaseViewHolder<JobVO>(itemView) {

    override fun setData(data: JobVO) {
        mData = data

        itemView.tvJobShortDesc.text = data.shortDesc
        itemView.tvJobSalary.text = "Salary : " + data.offerAmount!!.amount
        itemView.tvJobDuration.text = data.offerAmount!!.duration
        itemView.tvJobLocation.text = "Location : ${data.location}"
        itemView.tvJobClosedDate.text = "ClosedDate : ${data.postClosedDate}"
        itemView.tvJopAppliers.text = "${data.applicant!!.size} person applied"

        itemView.btnJobApply.setOnClickListener(object: View.OnClickListener{
            override fun onClick(p0: View?) {
                mDelegate.onTapApply()
            }

        })

        itemView.btnInterest.setOnClickListener(object : View.OnClickListener{
            @SuppressLint("ResourceAsColor")
            override fun onClick(p0: View?) {
                mDelegate.onTapInterest(mData)
                if(UserModel.getInstance().isUserSignIn(itemView.context)){
                    itemView.btnInterest.setBackgroundColor(R.color.divider)
                    itemView.btnInterest.isClickable = false
                }
            }

        })

    }

    override fun onClick(p0: View?) {
        mDelegate.onTapJob(mData)
    }

}