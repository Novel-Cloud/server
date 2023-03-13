package com.novel.cloud.web.config.security.detail
import com.novel.cloud.db.entity.member.Member

data class OAuthAttributes(
    val attributes: Map<String, Any>,
    val name: String,
    val email: String,
    val picture: String
) {
    companion object {
        fun create(
            registrationId: String,
            attributes: Map<String, Any>
        ): OAuthAttributes {
            return OAuthAttributes(
                name = attributes["name"] as String,
                email = attributes["email"] as String,
                picture = attributes["picture"] as String,
                attributes = attributes,            )
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


