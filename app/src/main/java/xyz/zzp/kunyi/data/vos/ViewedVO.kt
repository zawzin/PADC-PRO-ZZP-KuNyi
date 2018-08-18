package xyz.zzp.kunyi.data.vos

class ViewedVO {

    var seekerId : Int? = 0

    var seekerName : String? = null

    var seekerProfilePicUrl : String? = null

    var viewedTimeStamp : String? = null

    var seekerSkill : List<SkillVO>? = null
    get() = if(field==null) ArrayList() else field
}