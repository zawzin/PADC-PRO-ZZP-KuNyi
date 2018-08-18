package xyz.zzp.kunyi.data.vos

class InterestedVO {

    var seekerId : Int? = 0

    var seekerName : String? = null

    var seekerProfilePicUrl : String? = null

    var interestedTimeStamp : String? = null

    var seekerSkill : List<SkillVO>? = null
    get() = if(field==null) ArrayList() else field
}