package com.novel.cloud.web.config.security.detail
import com.novel.cloud.db.entity.member.Member

data class OAuthAttributes(
    val attributes: Map<*, *>,
    val name: String,
    val email: String,
    val picture: String
) {
    companion object {

        fun create(
            registrationId: String,
            attributes: Map<*, *>
        ): OAuthAttributes {
            return OAuthAttributes(
                name = attributes["name"] as String,
                email = attributes["email"] as String,
                picture = changePictureSize(attributes["picture"] as String),
                attributes = attributes
            )
        }

        private fun changePictureSize(pictureUrl: String): String {
            return pictureUrl.replace("s96", "s300")
        }

    }

    fun toEntity(): Member {
        return Member(
            email = email,
            nickname = name,
            picture = picture
        )
    }

}


