package com.novel.cloud.db.entity.artwork

import com.novel.cloud.db.entity.common.BaseTimeEntity
import lombok.AccessLevel
import lombok.Getter
import lombok.NoArgsConstructor
import javax.persistence.Entity

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
class Artwork : BaseTimeEntity() {

}