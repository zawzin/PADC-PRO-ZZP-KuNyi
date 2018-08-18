package xyz.zzp.kunyi.events

import xyz.zzp.kunyi.data.vos.JobVO

class FireBaseEvents {

    public class JobsLoadedEvent(private var jobsList:List<JobVO>){

        public fun getJobs():List<JobVO>{
            return jobsList
        }
    }
}