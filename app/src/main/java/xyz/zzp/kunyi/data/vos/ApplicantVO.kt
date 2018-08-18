package xyz.zzp.kunyi.data.vos

class ApplicantVO {

    var seekerId : Int? = 0

    var seekerName : String? = null

    var seekerProfilePicUrl : String? = null

    var appliedDate : String? = null

    var canLowerOfferAmount : Boolean? = false

    var seekerSkill : List<SkillVO>? = null
    get() = if(field==null) ArrayList() else field

    var whyShouldHire : List<WhyShouldHireVO>? = null
    get() = if(field==null) ArrayList() else field
}