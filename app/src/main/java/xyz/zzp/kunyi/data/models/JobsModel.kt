package xyz.zzp.kunyi.data.models

import android.content.Context
import android.util.Log
import com.google.firebase.database.*
import org.greenrobot.eventbus.EventBus
import xyz.zzp.kunyi.data.vos.InterestedVO
import xyz.zzp.kunyi.data.vos.JobVO
import xyz.zzp.kunyi.events.FireBaseEvents

class JobsModel private constructor(context : Context) : BaseModel(context) {

    companion object {
        private var INSTANCE : JobsModel? = null

        private var mDatabaseReference: DatabaseReference = FirebaseDatabase.getInstance().reference

        private lateinit var mJobsDR: DatabaseReference

        private lateinit var mJobList :List<JobVO>

        fun getINSTANCE() : JobsModel {
            val i = INSTANCE
            return i!!
        }

        fun initJobsModel(context: Context){
            INSTANCE = JobsModel(context)

        }
    }

     fun loadJobs(){
         mJobList = ArrayList()

        mJobsDR = mDatabaseReference.child("")
        mJobsDR.addValueEventListener(object : ValueEventListener{

            override fun onDataChange(p0: DataSnapshot?) {
                    if (p0 != null){
                        for (dataSnapshot in p0.children){
                            var jobVO : JobVO = dataSnapshot.getValue(JobVO::class.java)!!
                            mJobList += jobVO
                        }
                        val event : FireBaseEvents.JobsLoadedEvent = FireBaseEvents.JobsLoadedEvent(mJobList)
                        EventBus.getDefault().post(event)
                    }
             }
            override fun onCancelled(p0: DatabaseError?) {
                Log.i("Kunyi App","Error!${p0.toString()}")
            }

        })
    }

    fun loadJobById(id : Long):JobVO{
        var jobById : JobVO = JobVO()
        for (job in mJobList){
            if (job.jobPostId == id)
                jobById = job
        }
        return jobById
    }

    fun addJob(jobTitle:String, jobDesc: String,
               amount: Int, duration: String,
               location: String){
        var job: JobVO = JobVO().initJob(jobTitle,jobDesc,amount,duration,location)
        mJobsDR.child(job.jobPostId.toString()).setValue(job)
    }

    fun tapInterest(job : JobVO){
        var job: JobVO = JobVO().addInterest(job)
        mJobsDR.child(job.postedDate.toString()).setValue(job)
    }
}