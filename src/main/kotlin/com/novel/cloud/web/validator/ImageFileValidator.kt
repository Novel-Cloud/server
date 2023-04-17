package com.novel.cloud.web.validator

import org.springframework.web.multipart.MultipartFile
import javax.validation.ConstraintValidator
import javax.validation.ConstraintValidatorContext

class ImageFileValidator : ConstraintValidator<ValidImage, MultipartFile> {

    override fun initialize(constraintAnnotation: ValidImage) {}

    override fun isValid(
        multipartFile: MultipartFile?,
        context: ConstraintValidatorContext
    ): Boolean {
        var result = true
        val contentType = multipartFile?.contentType
        if (!isSupportedContentType(contentType)) {
            context.disableDefaultConstraintViolation()
            context.buildConstraintViolationWithTemplate(
                "PNG, JPG, GIF만 허용됩니다."
            ).addConstraintViolation()
            result = false
        }

        return result
    }

    private fun isSupportedContentType(contentType: String?): Boolean {
        return contentType == "image/png"
                || contentType == "image/jpg"
                || contentType == "image/jpeg"
                || contentType == "image/gif"
    }
}