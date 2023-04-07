package com.novel.cloud.web.config.converter

import javax.persistence.AttributeConverter
import javax.persistence.Converter

@Converter
class BooleanToYNConverter: AttributeConverter<Boolean, String> {

    override fun convertToDatabaseColumn(attribute: Boolean): String {
        if (attribute) return "Y"
        return "N"
    }

    override fun convertToEntityAttribute(yn: String?): Boolean {
        return "Y".equals(yn, ignoreCase = true)
    }

}
