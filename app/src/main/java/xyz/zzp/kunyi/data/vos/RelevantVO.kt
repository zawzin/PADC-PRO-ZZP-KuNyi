package xyz.zzp.kunyi.data.vos

class RelevantVO {

    var seekerId : Int? = 0

    var seekerName : String? = null

    var seekerProfilePicUrl : String? = null

    var relevancyPercentage : Double? = 0.0

    var whyRelevant : String? = null

    var seekerSkill : List<SkillVO>? = null
    get() = if(field == null) ArrayList() else field
}