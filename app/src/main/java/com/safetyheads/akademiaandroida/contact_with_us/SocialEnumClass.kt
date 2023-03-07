package com.safetyheads.akademiaandroida.contact_with_us

enum class SocialEnumClass(val applicationPackage: String, val socialName: String? = null, val downloadGooglePlay: String? = null) {
    Facebook(ContactWithUsObject.facebookPackage, ContactWithUsObject.facebookSH, ContactWithUsObject.googlePlayDownloadLink + ContactWithUsObject.facebookPackage),
    Instagram(ContactWithUsObject.instagramPackage, ContactWithUsObject.instagramSH, ContactWithUsObject.googlePlayDownloadLink + ContactWithUsObject.instagramPackage),
    Linkedin(ContactWithUsObject.linkedinPackage, ContactWithUsObject.linkedinSH, ContactWithUsObject.googlePlayDownloadLink + ContactWithUsObject.linkedinPackage),
    YouTube(ContactWithUsObject.youTubePackage, ContactWithUsObject.youTubeSH, ContactWithUsObject.googlePlayDownloadLink + ContactWithUsObject.youTubePackage),
    GooglePlay(ContactWithUsObject.googlePlayPackage)
}