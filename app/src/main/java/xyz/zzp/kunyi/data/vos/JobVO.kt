package xyz.zzp.kunyi.data.vos

import xyz.zzp.kunyi.data.models.UserModel

class JobVO {

    var applicant : List<ApplicantVO>? = null
    get() = if (field == null) ArrayList() else field

    var availablePostCount : Int? = 0

    var email : String? = null

    var fullDesc : String? = null

    var genderForJob : Long? = 0L

    var images : List<String>? = null
    get() = if(field == null) ArrayList() else field

    var importantNotes : List<String>? = null
    get() = if (field == null) ArrayList() else field

    var interested : List<InterestedVO>? = null
    get() = if(field == null) ArrayList() else field

    var isActive : Boolean = false

    var jobDuration : JobDurationVO? = null
    get() = if(field == null) JobDurationVO() else field

    var jobPostId : Long? = 0

    var jobTags : List<JobsTagVO>? =null
    get() = if(field == null) ArrayList() else field

    var location : String? = null

    var makeMoneyRating : Int? = 0

    var offerAmount : OfferAmountVO? = null
    get() = if(field == null) OfferAmountVO() else field

    var phoneNo : String? = null

    var postClosedDate : String? = null

    var postedDate : String? = null

    var relevant : List<RelevantVO>? = null
    get() = if(field == null) ArrayList() else field

    var requiredSkill : List<SkillVO>? = null
    get() = if(field == null) ArrayList() else field

    var shortDesc : String? = null

    var viewed : List<ViewedVO>? = null
    get() = if(field == null) ArrayList() else field

    open fun initJob(jobTitle:String, jobDesc: String,
                     amount: Int, duration: String,
                     location: String): JobVO{
        var offer: OfferAmountVO = OfferAmountVO()
        offer.amount = amount
        offer.duration = duration

        var job: JobVO = JobVO()
        job.jobPostId = System.currentTimeMillis() / 1000
        job.shortDesc = jobTitle
        job.fullDesc = jobDesc
        job.location = location
        job.offerAmount = offer

        return job
    }

    fun addInterest(job: JobVO): JobVO{
        var interest = InterestedVO()
        interest.seekerId = (System.currentTimeMillis() / 1000).toInt()
        interest.seekerName = UserModel.getInstance().getUser()!!.displayName

        var jobInterest: JobVO = JobVO()

        if (job != null){

            var listInterest : List<InterestedVO> = ArrayList()
            listInterest = job.interested!!
            listInterest += interest

            jobInterest = job
            jobInterest.interested = listInterest
        }
        return jobInterest
    }
}